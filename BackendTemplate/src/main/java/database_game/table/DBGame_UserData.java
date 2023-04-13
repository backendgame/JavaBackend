package database_game.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.MessageSending;
import backendgame.com.database.DBDescribe;
import backendgame.com.database.DBProcess_Describe;
import backendgame.com.database.entity.DB_ReadDatabase;
import backendgame.com.database.entity.DB_WriteDatabase;
import begame.config.PATH;

public class DBGame_UserData extends BaseDatabaseGame{//lengthData = des.getDataLength()+8
	public static final long OFF=123;
	
	public RandomAccessFile rfData;
	public DBProcess_Describe des;
	public DBGame_UserData(short _tableId) throws FileNotFoundException {
		path=PATH.DATABASE_FOLDER+"/"+_tableId+"/UserData";
		rfData = new RandomAccessFile(path, "rw");
		des=new DBProcess_Describe(rfData, OFF);
	}
	public DBGame_UserData(String _path,RandomAccessFile _rfData) {
		path=_path;
		rfData=_rfData;
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
	
	public DBDescribe[] getDescribes() throws IOException {
		return des.readDescribes();
	}
	public void setDescribe(DBDescribe[] listDes) throws IOException {
		des.writeDescribes(listDes, true);
	}

//	public void writeData(long userId,DB_ReadDatabase dataOperator) throws IOException {des.writeData(offset, dataOperator);}
	public void writeData(long userId, int indexDescribe, Object value) throws IOException {
		des.writeData(get_OffsetData(userId, indexDescribe), indexDescribe, value);
	}
//	public void writeData(DB_WriteDatabase writer) throws IOException {
//		des.writeData(offset, dataOperator);
//	}
	
	
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
    
	public void writeData(long userId, DB_WriteDatabase dataOperator) throws IOException {
		des.writeData(get_OffsetData(userId, dataOperator.indexDescribe), dataOperator);
	}
    public void readData(long userId, int indexDescribe, DB_ReadDatabase reader) throws IOException {
    	if(des.get_DataType_ByIndex(indexDescribe)==reader.Type)
    		des.readData(get_OffsetData(userId, indexDescribe), reader);
    	else
    		throw new IOException("Database error DBGame_UserData → readData(long userId, int indexDescribe, DB_ReadDatabase reader) : " + DBDefine_DataType.getTypeName(reader.Type) + " != "+des.get_DataType_ByIndex(indexDescribe));
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
	public long getOffsetOfCredential(long userId) throws IOException {
		rfData.seek(des.getOffset_BeginData() + userId*(8+des.getDataLength()));
		return rfData.readLong();
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
	
	
	public void traceUserId(long userId) throws IOException {
	    int numberColumn = des.getNumberDescribe();
	    for(int i=0;i<numberColumn;i++)
	        if(des.readValueData(get_OffsetData(userId, i), i) instanceof byte[])
	            System.out.print(des.getColumnName(i)+"("+Arrays.toString((byte[])des.readValueData(get_OffsetData(userId, i), i))+")  ");
	        else
	            System.out.print(des.getColumnName(i)+"("+des.readValueData(get_OffsetData(userId, i), i)+")  ");
	    System.out.println("");
    }
	public void traceDescribe() throws IOException {
		DBDescribe[] list = des.readDescribes();
		if(list!=null)
			for(DBDescribe describe:list)
				describe.trace();
	}
	
	@Override public void close() {if(rfData!=null)try {rfData.close();rfData=null;} catch (IOException e) {e.printStackTrace();}}
	@Override public void deleteFile() {try {Files.deleteIfExists(FileSystems.getDefault().getPath(path));} catch (IOException e) {e.printStackTrace();}}
}
