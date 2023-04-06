package database_game.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import backendgame.com.database.DBString;
import backendgame.com.database.ProcessDescribe;
import backendgame.com.database.entity.DataType;
import backendgame.com.database.entity.DescribeData;
import begame.config.PATH;
import begame.onehit.web.dto.DTO_Describe;

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

	private ProcessDescribe process;
	public DBGameTable_UserData(short tableId) throws FileNotFoundException {
		path=PATH.DATABASE_FOLDER+"/"+tableId+"/UserData";
		rfData = new RandomAccessFile(path, "rw");
	}



	private void initProcess() {if(process==null) {process=new ProcessDescribe(rfData, Offset_DescribeTables);}}
	public void writeDescribes(DescribeData[] list) throws IOException {
		initProcess();
		process.writeDescribes(list);
	}
	
	public DTO_Describe[] getDescribeTables(DBString dbString) throws IOException {
		initProcess();
		DescribeData[] list = process.readDescribes();
		if(list==null || list.length==0)
			return null;
		
		int numberDescribe = list.length;
		DTO_Describe[] result = new DTO_Describe[numberDescribe-1];
		DTO_Describe describeDTO;
		DescribeData describeData;
		for(int i=1;i<numberDescribe;i++) {//Không tính OffsetCredential 
			describeData=list[i];
			describeDTO=new DTO_Describe();
			
			describeDTO.ColumnId = describeData.ColumnId_StringName;
			describeDTO.ColumnName = dbString.readStringByOffset(describeDTO.ColumnId);
			describeDTO.ViewId = describeData.ViewId;
			describeDTO.Type = describeData.Type;
			describeDTO.Size = describeData.Size;
			describeDTO.DefaultData = describeData.DefaultData;

			result[i-1]=describeDTO;
		}
		return result;
	}
	
	public void updateDescribeTable(DTO_Describe[] newDescribeTables, DBString dbString) throws IOException {
		int numberDes = newDescribeTables==null?0:newDescribeTables.length;
		
		DescribeData des;
		DescribeData[] listDes = new DescribeData[numberDes+1];
		
		des=new DescribeData();
		des.ColumnId_StringName=dbString.getOffset("OffsetCredential");
		des.Type=DataType.LONG;
		des.Size=8;
		listDes[0]=des;
		
		for(int i=0;i<numberDes;i++) {
			des=new DescribeData();
			des.ColumnId_StringName=dbString.getOffset(newDescribeTables[i].ColumnName);
			des.ViewId=newDescribeTables[i].ViewId;
			des.Type=newDescribeTables[i].Type;
			des.Size=newDescribeTables[i].Size;
			des.DefaultData=newDescribeTables[i].DefaultData;
			
			listDes[i+1]=des;
		}
		
		process.writeDescribes(listDes);
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





	



//	public long getCredentialOffset(long userId) throws IOException {
//		rfData.seek(LENGTH_HEADER + userId*(9+dataLength) + 1 + dataLength);
//		return rf.readLong();
//	}
}
