package database_game.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.MessageSending;
import backendgame.com.database.DBDescribe;
import backendgame.com.database.DBProcess_Describe;
import backendgame.com.database.entity.DB_WriteDatabase;
import begame.config.PATH;

public class DBGame_UserData extends BaseDatabaseGame{//lengthData = des.getDataLength()+8
	public static final long OFF=123;
	
	public RandomAccessFile rfData;
	public DBProcess_Describe des;
	public DBGame_UserData(short _tableId) throws FileNotFoundException {
		tableId=_tableId;
		path=PATH.DATABASE_FOLDER+"/"+_tableId+"/UserData";
		rfData = new RandomAccessFile(path, "rw");
		des=new DBProcess_Describe(rfData, OFF);
	}
	public DBGame_UserData(String _path) throws FileNotFoundException {
		path=_path;
		rfData = new RandomAccessFile(path, "rw");
		des=new DBProcess_Describe(rfData, OFF);
	}
	
	public void insertRow(long userId,long offsetCredential) throws IOException {
		int dataLength = des.getDataLength();
		if(dataLength>0) {
			byte[] defaultData=des.getDefaultData();
			rfData.seek(des.getOffset_BeginData() + userId*(dataLength+8));
			rfData.writeLong(offsetCredential);
			rfData.write(defaultData);
		}else {
			rfData.seek(des.getOffset_BeginData() + userId*(dataLength+8));
			rfData.writeLong(offsetCredential);
		}
	}
	public long getOffsetOfCredential(long userId) throws IOException {
		rfData.seek(des.getOffset_BeginData() + userId*(8+des.getDataLength()));
		return rfData.readLong();
	}
	public void reNewUser(long userId,long offsetCredential, byte[] defaultData) throws IOException {
		rfData.seek(des.getOffset_BeginData() + userId*(des.getDataLength()+8));
		rfData.writeLong(offsetCredential);
		rfData.write(defaultData);
	}
	public void setCredentialOffset(long userId,long offsetCredential) throws IOException {
		rfData.seek(des.getOffset_BeginData() + userId*(des.getDataLength()+8));
		rfData.writeLong(offsetCredential);
	}
	public DBDescribe[] getDescribes() throws IOException {
		return des.readDescribes();
	}
	
	public void setDescribe(DBDescribe[] listDes) throws IOException{
		des.writeDescribes(listDes, true);
	}


	
	public void writeData(long userId, DB_WriteDatabase writer) throws IOException {
		DBDescribe describe = des.readDescribeByIndex(writer.indexDescribe);
		if(describe==null || writer.Type!=describe.Type || writer.columnName.equals(describe.ColumnName)==false) {
			describe=des.findDescribe_ByCoulmnId(writer.columnName);
			if(describe==null || writer.Type!=describe.Type)
				throw new IOException("DBGame_UserData error : can't not found describe");
			writer.Type=describe.Type;
		}
		
		des.writeData(des.getOffset_BeginData() + userId*(8+des.getDataLength()) + 8 + describe.OffsetRow, writer.Type, writer.value);
	}
	public void writeData(long userId, int indexDescribe, Object value) throws IOException {
		des.writeData(get_OffsetData(userId, indexDescribe), des.get_DataType_ByIndex(indexDescribe), value);
	}
	
    public long get_OffsetData(long userId, int indexDescribe) throws IOException {
        return des.getOffset_BeginData() + userId*(8+des.getDataLength()) + 8 + des.get_OffsetRow_of_Describe_ByIndex(indexDescribe);
    }

    public void seekTo_OffsetData(long userId, int indexDescribe) throws IOException {
        rfData.seek(get_OffsetData(userId, indexDescribe));
    }
    
    
    
    
    
    ///Boolean////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean processBoolean(long userId, int indexDescribe, byte operator,boolean value) throws IOException {
		if(des.get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.BOOLEAN)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long userId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe)));
		return des.processBoolean(get_OffsetData(userId, indexDescribe), operator, value);
	}
	public boolean processBoolean(long userId, String columnName, byte operator,boolean value) throws IOException {
		int indexDescribe = des.findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processBoolean(userId, indexDescribe, operator, value);
	}
	
	///Byte///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte processByte(long userId, int indexDescribe, byte operator,byte value) throws IOException {
		if(des.get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.BYTE)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long userId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe)));
		return des.processByte(get_OffsetData(userId, indexDescribe), operator, value);
	}
	public byte processByte(long userId, String columnName, byte operator,byte value) throws IOException {
		int indexDescribe = des.findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processByte(userId, indexDescribe, operator, value);
	}
	
	///Short//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public short processShort(long userId, int indexDescribe, byte operator,short value) throws IOException {
		if(des.get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.SHORT)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long userId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe)));
		return des.processShort(get_OffsetData(userId, indexDescribe), operator, value);
	}
	public short processShort(long userId, String columnName, byte operator,short value) throws IOException {
		int indexDescribe = des.findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processShort(userId, indexDescribe, operator, value);
	}
	
	///Float//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public float processFloat(long userId, int indexDescribe, byte operator,float value) throws IOException {
		if(des.get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.FLOAT)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long userId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe)));
		return des.processFloat(get_OffsetData(userId, indexDescribe), operator, value);
	}
	public float processFloat(long userId, String columnName, byte operator,float value) throws IOException {
		int indexDescribe = des.findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processFloat(userId, indexDescribe, operator, value);
	}
	
	///Integer////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int processInt(long userId, int indexDescribe, byte operator,int value) throws IOException {
		if(des.get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.INTEGER)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long userId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe)));
		return des.processInt(get_OffsetData(userId, indexDescribe), operator, value);
	}
	public int processInt(long userId, String columnName, byte operator,int value) throws IOException {
		int indexDescribe = des.findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processInt(userId, indexDescribe, operator, value);
	}
	
	///Double/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public double processDouble(long userId, int indexDescribe, byte operator,double value) throws IOException {
		if(des.get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.DOUBLE)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long userId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe)));
		return des.processDouble(get_OffsetData(userId, indexDescribe), operator, value);
	}
	public double processDouble(long userId, String columnName, byte operator,double value) throws IOException {
		int indexDescribe = des.findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processDouble(userId, indexDescribe, operator, value);
	}
	
	///Long///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long processLong(long userId, int indexDescribe, byte operator,long value) throws IOException {
		if(des.get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.LONG)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long userId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe)));
		return des.processLong(get_OffsetData(userId, indexDescribe), operator, value);
	}
	public long processLong(long userId, String columnName, byte operator,long value) throws IOException {
		int indexDescribe = des.findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processLong(userId, indexDescribe, operator, value);
	}
	
	///ByteArray//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte[] processByteArray(long userId, int indexDescribe, byte operator,byte[] value) throws IOException {
		if(des.get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.ByteArray)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long userId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe)));
		return des.processByteArray(get_OffsetData(userId, indexDescribe), operator, value);
	}
	public byte[] processByteArray(long userId, String columnName, byte operator,byte[] value) throws IOException {
		int indexDescribe = des.findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processByteArray(userId, indexDescribe, operator, value);
	}
	///List///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte[] processList(long userId, int indexDescribe, byte operator,byte[] value) throws IOException {
		if(des.get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.LIST)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long userId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe)));
		return des.processList(get_OffsetData(userId, indexDescribe), operator, value);
	}
	public byte[] processList(long userId, String columnName, byte operator,byte[] value) throws IOException {
		int indexDescribe = des.findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processList(userId, indexDescribe, operator, value);
	}
	///String/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String processString(long userId, int indexDescribe, byte operator,String value) throws IOException {
		if(des.get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.STRING)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long userId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe)));
		return des.processString(get_OffsetData(userId, indexDescribe), operator, value);
	}
	public String processString(long userId, String columnName, byte operator,String value) throws IOException {
		int indexDescribe = des.findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processString(userId, indexDescribe, operator, value);
	}
	///IpV6///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte[] processIpV6(long userId, int indexDescribe, byte operator,byte[] value) throws IOException {
		if(des.get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.IPV6)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long userId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe)));
		return des.processIpV6(get_OffsetData(userId, indexDescribe), operator, value);
	}
	public byte[] processIpV6(long userId, String columnName, byte operator,byte[] value) throws IOException {
		int indexDescribe = des.findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processIpV6(userId, indexDescribe, operator, value);
	}
    
    
	
	
	
	
    
    
    
    
	public Object processValidate(long userId, int indexDescribe, String columnName, byte operator, byte Type, Object value) throws IOException {
		if (Type != des.get_DataType_ByIndex(indexDescribe) || des.getColumnName(indexDescribe).equals(columnName)==false) {
			indexDescribe = des.findIndex_ByColumnName(columnName);
			if (indexDescribe==-1 || Type != des.get_DataType_ByIndex(indexDescribe))
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, DB_WriteDatabase writer) " + DBDefine_DataType.getTypeName(Type));
		}
		return des.process(get_OffsetData(userId, indexDescribe), operator, Type, value);
	}
	public Object process(long userId, String columnName, byte operator, byte Type, Object object) throws IOException {
        int indexDescribe = des.findIndex_ByColumnName(columnName);
        if(indexDescribe==-1)
            return null;
        return des.process(get_OffsetData(userId, indexDescribe), operator, Type, object);
    }
	public Object process(long userId, int indexDescribe, byte operator, byte Type, Object object) throws IOException {
		if(Type==des.get_DataType_ByIndex(indexDescribe))
			return des.process(get_OffsetData(userId, indexDescribe), operator, Type, object);
		else
			throw new IOException("Database error "+DBDefine_DataType.getTypeName(des.get_DataType_ByIndex(indexDescribe))+"!="+Type);
	}
    
	public Object readData(long userId,int indexDescribe) throws IOException {
		DBDescribe describe=des.readDescribeByIndex(indexDescribe);
		return des.readData(des.getOffset_BeginData() + userId*(8+des.getDataLength()) + 8 + describe.OffsetRow, describe.Type);
	}
	public Object readData(long userId,long offsetRow, byte Type) throws IOException {
		return des.readData(des.getOffset_BeginData() + userId*(8+des.getDataLength()) + 8 + offsetRow, Type);
	}
	public void writeData(long userId,long offsetRow, byte Type, Object value) throws IOException {
		des.writeData(des.getOffset_BeginData() + userId*(8+des.getDataLength()) + 8 + offsetRow, Type, value);
	}

    
	public void writeParsingRow(long userId, DBDescribe[] listDescribeTables, int numberDescribeTables, MessageSending messageSending) throws IOException {
		int Type;
		for(int i=0;i<numberDescribeTables;i++) {
			messageSending.writeByte(listDescribeTables[i].Type);
			seekTo_OffsetData(userId, i);
			
			Type = listDescribeTables[i].Type;
			if(0<Type && Type<10)
				messageSending.writeBoolean(rfData.readBoolean());
			else if(9<Type && Type<20)
				messageSending.writeByte(rfData.readByte());
			else if(19<Type && Type<40)
				messageSending.writeShort(rfData.readShort());
			else if(39<Type && Type<60)
				messageSending.writeInt(rfData.readInt());
			else if(59<Type && Type<80)
				messageSending.writeFloat(rfData.readFloat());
			else if(79<Type && Type<90)
				messageSending.writeLong(rfData.readLong());
			else if(89<Type && Type<100)
				messageSending.writeDouble(rfData.readDouble());
			else if(Type==DBDefine_DataType.ByteArray || Type==DBDefine_DataType.LIST) {
                int size = rfData.readInt();
                byte[] result = new byte[size];
                rfData.read(result);
                messageSending.writeByteArray(result);
			}else if(Type==DBDefine_DataType.STRING)
				messageSending.writeString(rfData.readUTF());
			else if(Type==DBDefine_DataType.IPV6) {
				 byte[] ipv6 = new byte[16];
                 rfData.read(ipv6);
                 messageSending.writeSpecialArray_WithoutLength(ipv6);
			}else
				throw new IOException("Database error "+this.getClass().getSimpleName()+" → writeParsingRow " + DBDefine_DataType.getTypeName(listDescribeTables[i].Type));
		}
	}

	
    public int getNumberColumn() throws IOException {return des.getNumberDescribe();}
	
	public long countRow() throws IOException {
		int dataLength = des.getDataLength()+8;//Luôn khác 0
		long sumData = rfData.length() - des.getOffset_BeginData();
		if(sumData%dataLength==0)
			return sumData/dataLength;
		else
			return sumData/dataLength+1;
	}
	
	public void changeFileName(short _tableId) throws IOException {
		Path source = Paths.get(path);
		Path newdir = Paths.get(PATH.DATABASE_FOLDER+"/"+_tableId+"/UserData");
		Files.move(source, newdir.resolve(source.getFileName()),StandardCopyOption.REPLACE_EXISTING);
	}

	public void traceTitleRow() throws IOException {
		System.out.printf("%15s", "");
		DBDescribe[] list = des.readDescribes();
		for (DBDescribe describe : list)
			if(describe.Type==DBDefine_DataType.STRING)
				System.out.printf("  %15.15s", describe.ColumnName);
			else if(describe.Type==DBDefine_DataType.ByteArray || describe.Type==DBDefine_DataType.LIST || describe.Type==DBDefine_DataType.IPV6)
				System.out.printf("  %30.30s", describe.ColumnName);
			else
				System.out.printf("  %12.12s", describe.ColumnName);
		System.out.println("");
	}

	public void traceUserId(long userId) throws IOException {
		System.out.print("UserID("+userId+")	");
	    int numberColumn = des.getNumberDescribe();
	    Object value;
	    for(int i=0;i<numberColumn;i++) {
	    	value = readData(userId, i);
	        if(value instanceof byte[])
	            System.out.print(des.getColumnName(i)+"("+Arrays.toString((byte[])value)+")  ");
	        else
	            System.out.print(des.getColumnName(i)+"("+value+")  ");
	    }
	    System.out.println("");
    }
	
	@Override public void close() {if(rfData!=null)try {rfData.close();rfData=null;} catch (IOException e) {e.printStackTrace();}}
	@Override public void deleteFile() {try {Files.deleteIfExists(FileSystems.getDefault().getPath(path));} catch (IOException e) {e.printStackTrace();}}
}
