package database;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

import backendgame.config.Lib;
import backendgame.config.PATH;
import backendgame.config.TIME;
import backendgame.model.dto.DTO_Create_Table;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;

public class Manager_DBGameTable {
	public Object lockVersion;
	public Object lockCreateTable;
	public Object[] lockTable;

	private RandomAccessFile rfTableId;
	private Random random;
	public short currentTableId;
	
	private Manager_DBGameTable() {
		random=new Random();
		lockVersion=new Object();
		lockCreateTable=new Object();
		lockTable = new Object[Short.MAX_VALUE];
		for(short i=0;i<Short.MAX_VALUE;i++)
			lockTable[i]=new Object();
		//////////////////////////////////////////////////////////////////////////////////////
		File f = new File(PATH.DATABASE_FOLDER);
		if(f.exists()==false)
			f.mkdirs();
		//////////////////////////////////////////////////////////////////////////////////////
		try {
			rfTableId = new RandomAccessFile(PATH.DATABASE_FOLDER + "/TableId", "rw");
			if(rfTableId.length()>1)
				currentTableId = rfTableId.readShort();
			System.out.println("Current tableId : "+currentTableId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void updateKey(DBGameTable_UserData databaseUserData,long offset,byte[] data){
//		try {databaseUserData.rf.seek(offset);databaseUserData.rf.write(data);} catch (IOException e) {e.printStackTrace();return;}
//		
//		File tmp;
//		RandomAccessFile rfTMP;
//		short subTable = 0;
//		try {subTable = databaseUserData.countSubTable();} catch (IOException e) {e.printStackTrace();return;}
//		
//		for(short i=0;i<subTable;i++) {
//			tmp=new File(SubTable.getSubFile(databaseUserData.tableId, i));
//			if(tmp.exists() && tmp.length()>SubTable.HEADER_LENGTH) {
//				rfTMP=null;
//				try {
//					rfTMP=new RandomAccessFile(tmp, "rw");
//					rfTMP.seek(offset);
//					rfTMP.write(data);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				
//				if(rfTMP!=null)
//					try {rfTMP.close();} catch (IOException e) {e.printStackTrace();}
//			}
//		}
//	}
	
	public DTO_Create_Table createTable(long userId) {
		DTO_Create_Table result = null;
		synchronized (lockCreateTable) {
			for (short i = (short) (currentTableId + 1); i < Short.MAX_VALUE; i++) {
				result = doCreateTable(i, userId);
				if(result!=null)
					return result;
			}
			
			for (short i = 1; i <= currentTableId; i++){
				result = doCreateTable(i, userId);
				if(result!=null)
					return result;
			}
		}
		return result;
	}
	
	private void resetFile(String s) {RandomAccessFile rf = null;try {rf = new RandomAccessFile(s, "rw");rf.setLength(0);} catch (IOException e1) {e1.printStackTrace();}if(rf!=null)try {rf.close();} catch (IOException e) {e.printStackTrace();}}
	public DTO_Create_Table doCreateTable(short tableId, long userIdAdmin) {
		RandomAccessFile rf = null;
		File root = new File(PATH.DATABASE_FOLDER+"/"+tableId);
		/////////////////////Check create
		if(root.exists()) {
			File file = new File(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.UserData);
			if(file.exists() && file.length()>=DBGameTable_UserData.LENGTH_HEADER) {
				try {
					rf = new RandomAccessFile(file, "rw");
					if(System.currentTimeMillis()>rf.readLong() + TIME.Day_30) {//Quá hạn 30 ngày sẽ bị xóa khỏi database
						rf.close();
						return null;
					}else
						rf.setLength(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			resetFile(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.AccountLogin_Data);
			resetFile(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.AccountLogin_Index);
			resetFile(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.StringData);
			resetFile(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.StringIndex);
			
			File[] listDelete = root.listFiles();
			if(listDelete!=null)
				for(File directory:listDelete)
					if(directory.isDirectory())
						Lib.deleteFolder(directory);
			
			if(rf!=null)
				try {rf.close();} catch (IOException e) {e.printStackTrace();}
		}else
			root.mkdir();
		/////////////////////DoCreate
		DTO_Create_Table result = null;
		DBGameTable_AccountLogin accountLogin=null;
		DBString dbString=null;
		try {
			result=new DTO_Create_Table(tableId,random);
			
			rf = new RandomAccessFile(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.UserData, "rw");
			rf.seek(0);
			rf.setLength(0);
			rf.seek(0);
			
			rf.writeLong(System.currentTimeMillis() + result.timeAvaiable);//Time Avaiable
			rf.writeLong(userIdAdmin);//AdminId
			rf.writeByte(0);//Permission
			rf.writeByte(0);//LogoutId
			rf.writeShort(0);//DataLength
			rf.writeLong(result.accessKey);//AccessKey
			rf.writeLong(result.readKey);//ReadKey
			rf.writeLong(result.writeKey);//WriteKey
			rf.writeLong(System.currentTimeMillis());//Time Create
			rf.writeLong(0);//Version
			
			rf.writeShort(0);//SubTable
			rf.writeLong(result.token_LifeTime);//Token Life Time
			
			rf.seek(DBGameTable_UserData.Offset_Permission_Device);
			rf.writeBoolean(true);//Device
			rf.writeBoolean(true);//System Account
			rf.writeBoolean(true);//Facebook
			rf.writeBoolean(true);//Google
			rf.writeBoolean(true);//Ads_id
			rf.writeBoolean(true);//AppleId
			rf.writeBoolean(true);//EmailCode
			
			rf.seek(DBGameTable_UserData.LENGTH_HEADER);//Write data đầu tiên
			rf.writeByte(1);
			rf.writeLong(0);
			accountLogin = new DBGameTable_AccountLogin(tableId);
			dbString = new DBString(tableId);
			dbString.getStringId("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(dbString!=null)
			dbString.close();
		if(accountLogin!=null)
			accountLogin.close();
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}

		
		if(result!=null)try {rfTableId.seek(0);rfTableId.writeShort(tableId);currentTableId=tableId;} catch (IOException e1) {e1.printStackTrace();}
		return result;
	}
	
	public final long getUserId(short tableId) {
		long USERID=0;
		RandomAccessFile rf=null;
		try {
			rf = new RandomAccessFile(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.UserData, "rw");
			rf.seek(BaseTableData.Offset_AdminId);
			USERID = rf.readLong();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(rf!=null)
			try {rf.close();} catch (IOException e) {e.printStackTrace();}
		return USERID;
	}

	public void deleteFolder(File file) {
		if(file.isDirectory()){
			File[] listfile = file.listFiles();
			for(File f:listfile)
				deleteFolder(f);
		}
		file.delete();
	}
	
	private static Manager_DBGameTable instance = null;
	public static Manager_DBGameTable gI() {
		if(instance==null) {
			instance=new Manager_DBGameTable();
			DBGameTable_AccountLogin.manager=instance;
		}
		return instance;
	}
}
