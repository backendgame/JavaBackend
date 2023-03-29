package database;

import java.io.IOException;
import java.io.RandomAccessFile;

import database.table.DBString;

public abstract class BaseTableData {
	public short tableId;
	///////////////////////////////////////////////////////////////
	public static final long Offset_TimeAvaiable		 	= 0;
	public static final long Offset_AdminId					= 8;
	public static final long Offset_Permission	 			= 16;//1 byte
	public static final long Offset_LogoutId	 			= 17;//1 byte
	public static final long Offset_DataLength	 			= 18;//2 byte
	public static final long Offset_AccessKey			 	= 20;
	public static final long Offset_ReadKey				 	= 28;
	public static final long Offset_WriteKey			 	= 36;
	
	public static final long Offset_TimeCreate		 		= 44;
	public static final long Offset_Version					= 52;
	///////////////////////////////////////////////////////////////
	public RandomAccessFile rf;
	public long timeAvaiable;				//0
	public long adminId;					//8
	public byte permission;					//16
	public byte logoutId;					//17
	public short dataLength;				//18
	public long accessKey;					//20
//	public long readKey;					//28
//	public long writeKey;					//36
//	public long timeCreate;					//44
	
	public BaseTableData(short _tableId, String path) throws IOException {
		tableId=_tableId;
		try {
			rf = new RandomAccessFile(path, "rw");
			timeAvaiable=rf.readLong();
			adminId=rf.readLong();
			permission=rf.readByte();
			logoutId=rf.readByte();
			dataLength=rf.readShort();
			accessKey=rf.readLong();
		}catch (Exception e) {
			e.printStackTrace();
			if(rf!=null)
				try {rf.close();} catch (IOException ee) {ee.printStackTrace();}
			throw e;
		}
	}
	
	public abstract long countRow() throws IOException;
	///////////////////////////////////////////////////////////////
	private short generateColumnId(short cloumnId,DescribeTable[] listDescribeTable) {
		if(cloumnId==0)
			cloumnId++;
		for(DescribeTable des:listDescribeTable)
			if(des.ColumnId==cloumnId)
				return generateColumnId((short) (cloumnId+1), listDescribeTable);
		return cloumnId;
	}
	
	//2 byte đầu quản lý ColumnId + 2 byte sau là numberDescribe
	protected void updateDescribeTable(long offsetDescribeTables, DescribeTable[] list, long offsetDataDefault, DBString dbString) throws IOException {
		rf.seek(Offset_Version);
		rf.writeLong(System.currentTimeMillis());
		
		if(list==null) {
			rf.seek(offsetDescribeTables);
			rf.writeShort(0);//ColumnId = 0
			rf.seek(Offset_DataLength);
			rf.writeShort(0);
		}else {
			dataLength=0;
			DescribeTable describeTable;
			/////////////////////////////////////////////
			short numberDescribe=(short) list.length;
			if(numberDescribe>365)
				throw new IOException("Out of range : Max(DescribeTable) = "+365);
			
			rf.seek(offsetDescribeTables);
			short _columnId = rf.readShort();
			rf.writeShort(numberDescribe);
			for(short i=0;i<numberDescribe;i++) {
				describeTable=list[i];
				
				if(describeTable.ColumnId==0) {
					describeTable.ColumnId = generateColumnId(_columnId, list);
					_columnId = (short) (describeTable.ColumnId + 1);
				}
				
				describeTable.Offset = dataLength;//Vị trí đúng của offset trước khi cộng Size
				describeTable.writeDescribeTable(rf, dbString);
				
				dataLength+=describeTable.Size;//Sau khi writeData thì mới cộng vào Length (Size chỉ setup ở chỗ nhận Message và đọc từ DatabaseFile
			}
			/////////////////////////////////////////////
			rf.seek(offsetDescribeTables);
			rf.writeShort(_columnId);
			
			rf.seek(Offset_DataLength);
			rf.writeShort(dataLength);

			rf.seek(offsetDataDefault);
			for(short i=0;i<numberDescribe;i++)
				list[i].writeDefaultValue(rf, dbString);
		}
	}
	public DescribeTable get1DescribeTable(short indexDescribeTable,long offsetDescribeTables, long offsetDataDefault, DBString dbString) throws IOException {
		if(indexDescribeTable>365)
			throw new IOException("Of out range indexDescribeTable : "+indexDescribeTable);
		rf.seek(offsetDescribeTables+4+indexDescribeTable*21);
		DescribeTable result = new DescribeTable(rf, dbString);
		
		rf.seek(offsetDataDefault + result.Offset);
		result.readDefault(rf, dbString);
		return result;
	}
	public DescribeTable[] getDescribeTables(long offsetDescribeTables, long offsetDataDefault, DBString dbString) throws IOException {
		/*Không đọc ColumnId*/
		rf.seek(offsetDescribeTables+2);
		short numberDescribe=rf.readShort();
		if(numberDescribe<1)
			return null;
		
		DescribeTable[] list=new DescribeTable[numberDescribe];
		for(short i=0;i<numberDescribe;i++)
			list[i]=new DescribeTable(rf, dbString);
			
		rf.seek(offsetDataDefault);
		for(short i=0;i<numberDescribe;i++)
			list[i].readDefault(rf, dbString);
		
		return list;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte getType() throws IOException {rf.seek(SubTable.HEADER_LENGTH);return rf.readByte();}
	public byte[] read(long offet,int length) throws IOException {byte[] _data = new byte[length];rf.seek(offet);rf.read(_data);return _data;}
	public void setLong(long offset, long value) throws IOException {rf.seek(offset);rf.writeLong(value);}
	public void writeBytes(long offset, byte[] data) throws IOException {rf.seek(offset);rf.write(data);}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void close() {if(rf!=null)try {rf.close();rf=null;} catch (IOException e) {e.printStackTrace();}}
}
