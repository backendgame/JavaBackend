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
		for(int i=0;i<numberDescribeTables;i++) {
			messageSending.writeByte(listDescribeTables[i].Type);
			seekTo_OffsetData(userId, i);
			
	        switch (listDescribeTables[i].Type) {
                case DBDefine_DataType.BOOLEAN:messageSending.writeBoolean(rfData.readBoolean());break;

                case DBDefine_DataType.BYTE:
                case DBDefine_DataType.STATUS:
                case DBDefine_DataType.PERMISSION:
                case DBDefine_DataType.AVARTAR:messageSending.writeByte(rfData.readByte());break;

                case DBDefine_DataType.SHORT:messageSending.writeShort(rfData.readShort());break;

                case DBDefine_DataType.FLOAT:messageSending.writeFloat(rfData.readFloat());break;
                
                case DBDefine_DataType.IPV4:
                case DBDefine_DataType.INTEGER:messageSending.writeInt(rfData.readInt());break;

                case DBDefine_DataType.DOUBLE:messageSending.writeDouble(rfData.readDouble());break;

                case DBDefine_DataType.USER_ID:
                case DBDefine_DataType.TIMEMILI:
                case DBDefine_DataType.LONG:messageSending.writeLong(rfData.readLong());break;

                case DBDefine_DataType.STRING:messageSending.writeString(rfData.readUTF());break;
	            
	            case DBDefine_DataType.ByteArray:
                case DBDefine_DataType.LIST:
                    int size = rfData.readInt();
                    byte[] result = new byte[size];
                    rfData.read(result);
                    messageSending.writeByteArray(result);
                    break;
	            
                case DBDefine_DataType.IPV6:
                    byte[] ipv6 = new byte[16];
                    rfData.read(ipv6);
                    messageSending.writeSpecialArray_WithoutLength(ipv6);
                    break;
	            default:
	                throw new IOException("Database error "+this.getClass().getSimpleName()+" → writeParsingRow " + DBDefine_DataType.getTypeName(listDescribeTables[i].Type));
	        }
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
