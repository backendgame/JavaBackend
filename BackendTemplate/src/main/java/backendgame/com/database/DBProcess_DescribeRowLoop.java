package backendgame.com.database;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.database.entity.DB_WriteDatabase;

public class DBProcess_DescribeRowLoop extends DBProcess_Describe{

	public DBProcess_DescribeRowLoop(RandomAccessFile _rfData, long _offsetDescribe) {
		super(_rfData, _offsetDescribe);
	}
	
	
	
	public void writeData(long rowId, DB_WriteDatabase writer) throws IOException {
		DBDescribe describe = readDescribeByIndex(writer.indexDescribe);
		if(describe==null || writer.Type!=describe.Type || writer.columnName.equals(describe.ColumnName)==false) {
			describe=findDescribe_ByCoulmnId(writer.columnName);
			if(describe==null || writer.Type!=describe.Type)
				throw new IOException("DBGame_UserData error : can't not found describe");
			writer.Type=describe.Type;
		}
		
		writeData(getOffset_BeginData() + rowId*getDataLength() + describe.OffsetRow, writer.Type, writer.value);
	}
	
	public long get_OffsetData(long rowId, int indexDescribe) throws IOException {
        return getOffset_BeginData() + rowId*getDataLength() + get_OffsetRow_of_Describe_ByIndex(indexDescribe);
    }
	
    public void seekTo_OffsetData(long rowId, int indexDescribe) throws IOException {
        rfData.seek(get_OffsetData(rowId, indexDescribe));
    }
//    public Object process(long rowId, String columnName, byte pperator, byte Type, Object object) throws IOException {
//        int indexDescribe = findIndex_ByColumnName(columnName);
//        if(indexDescribe==-1)
//            return null;
//        return processRow(rowId, indexDescribe, pperator, Type, object);
//    }
//    public Object processRow(long rowId, int indexDescribe, byte pperator, byte Type, Object object) throws IOException {
//        return process(get_OffsetData(rowId, indexDescribe), pperator, Type, object);
//    }
//    public Object readValueData(long rowId, int indexDescribe) throws IOException {
//        return readValueData(get_OffsetData(rowId, indexDescribe), indexDescribe);
//    }
	
	
	
	
    
    ///Boolean////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean processBoolean(long rowId, int indexDescribe, byte operator,boolean value) throws IOException {
		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.BOOLEAN)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
		return processBoolean(get_OffsetData(rowId, indexDescribe), operator, value);
	}
	public boolean processBoolean(long rowId, String columnName, byte operator,boolean value) throws IOException {
		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processBoolean(rowId, indexDescribe, operator, value);
	}
	
	///Byte///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte processByte(long rowId, int indexDescribe, byte operator,byte value) throws IOException {
		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.BYTE)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
		return processByte(get_OffsetData(rowId, indexDescribe), operator, value);
	}
	public byte processByte(long rowId, String columnName, byte operator,byte value) throws IOException {
		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processByte(rowId, indexDescribe, operator, value);
	}
	
	///Short//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public short processShort(long rowId, int indexDescribe, byte operator,short value) throws IOException {
		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.SHORT)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
		return processShort(get_OffsetData(rowId, indexDescribe), operator, value);
	}
	public short processShort(long rowId, String columnName, byte operator,short value) throws IOException {
		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processShort(rowId, indexDescribe, operator, value);
	}
	
	///Float//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public float processFloat(long rowId, int indexDescribe, byte operator,float value) throws IOException {
		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.FLOAT)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
		return processFloat(get_OffsetData(rowId, indexDescribe), operator, value);
	}
	public float processFloat(long rowId, String columnName, byte operator,float value) throws IOException {
		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processFloat(rowId, indexDescribe, operator, value);
	}
	
	///Integer////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int processInt(long rowId, int indexDescribe, byte operator,int value) throws IOException {
		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.INTEGER)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
		return processInt(get_OffsetData(rowId, indexDescribe), operator, value);
	}
	public int processInt(long rowId, String columnName, byte operator,int value) throws IOException {
		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processInt(rowId, indexDescribe, operator, value);
	}
	
	///Double/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public double processDouble(long rowId, int indexDescribe, byte operator,double value) throws IOException {
		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.DOUBLE)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
		return processDouble(get_OffsetData(rowId, indexDescribe), operator, value);
	}
	public double processDouble(long rowId, String columnName, byte operator,double value) throws IOException {
		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processDouble(rowId, indexDescribe, operator, value);
	}
	
	///Long///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long processLong(long rowId, int indexDescribe, byte operator,long value) throws IOException {
		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.LONG)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
		return processLong(get_OffsetData(rowId, indexDescribe), operator, value);
	}
	public long processLong(long rowId, String columnName, byte operator,long value) throws IOException {
		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processLong(rowId, indexDescribe, operator, value);
	}
	
	///ByteArray//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte[] processByteArray(long rowId, int indexDescribe, byte operator,byte[] value) throws IOException {
		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.ByteArray)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
		return processByteArray(get_OffsetData(rowId, indexDescribe), operator, value);
	}
	public byte[] processByteArray(long rowId, String columnName, byte operator,byte[] value) throws IOException {
		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processByteArray(rowId, indexDescribe, operator, value);
	}
	///List///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte[] processList(long rowId, int indexDescribe, byte operator,byte[] value) throws IOException {
		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.LIST)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
		return processList(get_OffsetData(rowId, indexDescribe), operator, value);
	}
	public byte[] processList(long rowId, String columnName, byte operator,byte[] value) throws IOException {
		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processList(rowId, indexDescribe, operator, value);
	}
	///String/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String processString(long rowId, int indexDescribe, byte operator,String value) throws IOException {
		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.STRING)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
		return processString(get_OffsetData(rowId, indexDescribe), operator, value);
	}
	public String processString(long rowId, String columnName, byte operator,String value) throws IOException {
		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processString(rowId, indexDescribe, operator, value);
	}
	///IpV6///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte[] processIpV6(long rowId, int indexDescribe, byte operator,byte[] value) throws IOException {
		if(get_DataType_ByIndex(indexDescribe)!=DBDefine_DataType.IPV6)
			throw new IOException("Database error "+getClass().getSimpleName()+"→ processBoolean(long rowId, int indexDescribe, byte operator,boolean value) : "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
		return processIpV6(get_OffsetData(rowId, indexDescribe), operator, value);
	}
	public byte[] processIpV6(long rowId, String columnName, byte operator,byte[] value) throws IOException {
		int indexDescribe = findIndex_ByColumnName(columnName);//Low performance
		if(indexDescribe==-1)
			throw new IOException("Column("+columnName+") is not exist");
		return processIpV6(rowId, indexDescribe, operator, value);
	}
    
	
	public long countRow() throws IOException {
		int dataLength = getDataLength();
		long sumDataLength = (rfData.length() - getOffset_BeginData());
		if(dataLength==0 || sumDataLength<0)
			return 0;
	//	System.out.println("******************************************************************");
	//	System.out.println("--->dataLength : "+dataLength);
	//	System.out.println("--->offsetDescribeTables : "+offsetBeginDescribe);
	//	System.out.println("--->offsetData : "+offsetData);
	//	System.out.println("--->sumDataLength : "+sumDataLength);
	//	System.out.println("--->rfData : "+rfData.length());
	//	System.out.println("******************************************************************");
		
		if(sumDataLength%dataLength==0)
			return sumDataLength/dataLength;
		else
			return sumDataLength/dataLength + 1;
	}
}
