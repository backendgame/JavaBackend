package database_game.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import backendgame.com.database.DBProcess_DescribeRowLoop;
import backendgame.com.database.DBString;
import backendgame.com.database.entity.DBDescribe;
import backendgame.com.database.entity.DataType;
import begame.config.PATH;

public class DBGameTable_UserData extends BaseDatabaseGame{
	public static final long OFFSET_CountSubTable			= 60;
	public static final long Offset_Token_LifeTime			= 62;
	
	public static final long Offset_Tracking_AllTime 		= 70;//16 byte
	public static final long Offset_Tracking_Yesterday 		= 86;//20 byte
	public static final long Offset_Tracking_ThisMonth 		= 106;//20 byte
	
	public static final long Offset_Permission_Device 			= 126;
	public static final long Offset_Permission_SystemAccount 	= 127;
	public static final long Offset_Permission_Facebook 		= 128;
	public static final long Offset_Permission_Google 			= 129;
	public static final long Offset_Permission_AdsId 			= 130;
	public static final long Offset_Permission_Apple 			= 131;
	public static final long Offset_Permission_EmailCode		= 132;
	
	
	public static final long Offset_Email			 		= 256;
	
	public static final long Offset_DescribeTables	 		= 1024;//ID → short → List DescribeTables

	private DBProcess_DescribeRowLoop process;
	public DBGameTable_UserData(short tableId) throws FileNotFoundException {
		path=PATH.DATABASE_FOLDER+"/"+tableId+"/UserData";
		rfData = new RandomAccessFile(path, "rw");
	}



	private void initProcess() {if(process==null) {process=new DBProcess_DescribeRowLoop(rfData, Offset_DescribeTables);}}
	public void updateDescribeTable(DBDescribe[] list, DBString dbString) throws IOException {
		initProcess();
		
		int numberCol = list==null?0:list.length;
		DBDescribe[] listWithUserId = new DBDescribe[numberCol+1];
		DBDescribe describe = new DBDescribe();
		listWithUserId[0]=describe;
		
		describe.OffsetRow=0;
		describe.ViewId=0;
		describe.State=0;
		describe.Permission=0;
		describe.Size=8;
		describe.Type=DataType.LONG;
		describe.DefaultData=new byte[8];
		describe.ColumnName="OffsetCredential";
		
		for(int i=0;i<numberCol;i++)
			listWithUserId[i+1]=list[i];
		process.writeDescribes(listWithUserId);
	}
	public DBDescribe[] getDescribeTables(DBString dbString) throws IOException {
		initProcess();
		DBDescribe[] list = process.readDescribes();
		if(list==null || list.length==0)
			return null;
		
		int numberDescribe = list.length;
		DBDescribe[] result = new DBDescribe[numberDescribe-1];
		for(int i=1;i<numberDescribe;i++)
			result[i-1]=list[i];
		return result;
	}
	

	
	
	public int getDataLength() throws IOException {
		initProcess();
		return process.getDataLength();
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////
	@Override public void deleteFile(){try {Files.deleteIfExists(FileSystems.getDefault().getPath(path));} catch (IOException e) {e.printStackTrace();}}
	///////////////////////////////////////////////////////////////////////////////////



	public long countRow() throws IOException {
		initProcess();
		return process.countRow();
	}


	public void setLong(long offset, long value) throws IOException {rfData.seek(offset);rfData.writeLong(value);}
}
