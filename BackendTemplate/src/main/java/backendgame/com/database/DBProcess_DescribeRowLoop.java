package backendgame.com.database;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBProcess_DescribeRowLoop extends DBProcess_Describe{

	public DBProcess_DescribeRowLoop(RandomAccessFile _rfData, long _offsetDescribe) {
		super(_rfData, _offsetDescribe);
	}

	
	
	
	
	public long get_OffsetData(long rowId, int indexDescribe) throws IOException {
        return getOffset_BeginData() + rowId * getDataLength() + get_OffsetRow_of_Describe_ByIndex(indexDescribe);
    }

    public void seekTo_OffsetData(long rowId, int indexDescribe) throws IOException {
        rfData.seek(get_OffsetData(rowId, indexDescribe));
    }
    public Object process(long rowId, String columnName, byte pperator, byte Type, Object object) throws IOException {
        int indexDescribe = findIndex_ByColumnName(columnName);
        if(indexDescribe==-1)
            return null;
        return processRow(rowId, indexDescribe, pperator, Type, object);
    }
	
    public Object processRow(long rowId, int indexDescribe, byte pperator, byte Type, Object object) throws IOException {
        return process(get_OffsetData(rowId, indexDescribe), pperator, Type, object);
    }
    public Object readValueData(long rowId, int indexDescribe) throws IOException {
        return readValueData(get_OffsetData(rowId, indexDescribe), indexDescribe);
    }
	
	
	
	
	
	public long countRow() throws IOException {
		int dataLength = getDataLength();
		long sumDataLength = (rfData.length() - getOffset_BeginData());
		if(dataLength==0)
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
