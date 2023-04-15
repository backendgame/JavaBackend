package database_game.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import backendgame.com.core.MessageSending;
import backendgame.com.core.TimeManager;
import backendgame.com.database.DBBase2Primary_Indexing;
import backendgame.com.database.DBDescribe;
import begame.config.PATH;
import database_game.DatabaseId;

public class DBGame_AccountLogin extends BaseDatabaseGame{//Cập nhật UserData thường chỉ dùng đến UserId = RowId của UserData
	
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
	
	
	public RandomAccessFile rfData;
	public RandomAccessFile rfBTree;
	public DBGame_AccountLogin(short _tableId) throws FileNotFoundException {
		tableId=_tableId;
		path=PATH.DATABASE_FOLDER+"/"+_tableId+"/AccountLogin";
		rfData = new RandomAccessFile(path, "rw");
		rfBTree = new RandomAccessFile(path+INDEX_EXTENSION, "rw");
	}
	private DBBase2Primary_Indexing btree;
	private void initBtree() {if(btree==null)btree=new DBBase2Primary_Indexing(rfData,rfBTree);}
	

	public void setPermissionAccount(long userId,byte permission) {}
	public void setIndex(String columnName) {}
	public void removeIndex(String columnName) {}
	
	
	public long countRow() throws IOException {return rfBTree.length()/8;}
	public long getOffsetByIndex(long id) throws IOException {rfBTree.seek(id*8);return rfBTree.readLong();}
	
	
	public long querryOffset(long rowId) {return 0;}
	public long[] querryOffset(long[] rowId) {return null;}
	public long querryRowId(String credential, byte databaseId) throws IOException {
		initBtree();
		if(btree.querryOffset(credential, databaseId)!=-1)
			return rfData.readLong();
		return -1;
	}

	public long[] querryUserId(String credential) throws IOException {
		initBtree();
		long[] listOffset = btree.querryOffset(credential);
		if(listOffset!=null) {
			int numberOffset = listOffset.length;
			for(int i=0;i<numberOffset;i++) {
				rfData.seek(listOffset[i]);
				rfData.readUTF();
				if(rfData.readBoolean())
					rfData.skipBytes(1);
				listOffset[i] = rfData.readLong();
			}
		}
		return listOffset;
	}
	
	public long insertAccount(String credential, byte databaseId, String password, DBGame_UserData databaseUserData) throws IOException {
		initBtree();
		long offsetInsert = btree.insertNewRow(credential, databaseId);
		if(offsetInsert!=-1) {
			long newUserId = databaseUserData.countRow();
			rfData.writeLong(newUserId);
		    if(databaseId==DatabaseId.SystemAccount) 
		        rfData.writeUTF(password);
			rfData.writeByte(0);//AccountStatus
			rfData.writeLong(System.currentTimeMillis());//TimeCreateAccount
			databaseUserData.insertRow(newUserId, offsetInsert);
			return newUserId;
		}else
		    return -1;
	}

	public long querryOffset(String credential, byte databaseId) throws IOException {
		initBtree();
		return btree.querryOffset(credential, databaseId);
	}
	
	public long[] querryOffset(String credential) throws IOException {
		initBtree();
		return btree.querryOffset(credential);
	}
	public void writeInfoByOffset(MessageSending messageSending, long credentialOffset) throws IOException {
		rfData.seek(credentialOffset);
		messageSending.writeString(rfData.readUTF());//Credential
		byte databaseId = rfData.readBoolean()?rfData.readByte():-1;
		messageSending.writeByte(databaseId);
		
		messageSending.writeLong(rfData.readLong());
		if(databaseId==DatabaseId.SystemAccount)
		    messageSending.writeString(rfData.readUTF());
		messageSending.writeByte(rfData.readByte());//AccountStatus
		messageSending.writeLong(rfData.readLong());//TimeCreateAccount
	}
	
	public void updateStatus(long offset, byte status) throws IOException {
		rfData.seek(offset);
		rfData.readUTF();
		if(rfData.readBoolean()) {
			byte databaseId = rfData.readByte();
		    if(databaseId==DatabaseId.SystemAccount)
				rfData.readUTF();
		}
		rfData.readLong();
		rfData.writeByte(status);
	}
	
	public void traceInfo(String credential, DBGame_UserData databaseUserData) throws IOException {
		long[] listUserId = querryUserId(credential);
		if(listUserId==null)
			System.out.println("No User");
		else {
			for(long userId:listUserId)
				traceInfo(userId, databaseUserData);
		}
	}
	
//	public void traceInfo(long userId, DBGame_UserData databaseUserData) throws IOException {
//		long offsetCredential = databaseUserData.getOffsetOfCredential(userId);
//		rfData.seek(offsetCredential);
//		String credential=rfData.readUTF();
//		byte databaseId = rfData.readBoolean()?rfData.readByte():-1;
//		long userid = rfData.readLong();
//		String password = databaseId==DatabaseId.SystemAccount?rfData.readUTF():null;
//		byte status = rfData.readByte();
//		long timeCreateAccount = rfData.readLong();
//		
//		
//		System.out.println(DatabaseId.getName(databaseId)+"	status("+status+")	"+TimeManager.gI().getStringTime(timeCreateAccount)+"	Credential("+credential+")	userid("+userid+")	password("+password+")");
//	}
//	public void traceInfo(String credential, DBGame_UserData databaseUserData) throws IOException {
//		long[] listUserId = querryUserId(credential);
//		System.out.println(Arrays.toString(listUserId));
//		if(listUserId==null)
//			System.out.println("No User");
//		else
//			for(long userId:listUserId)
//				traceInfo(userId, databaseUserData, list);
//	}
	public void traceInfo(long userId, DBGame_UserData databaseUserData) throws IOException {
		long offsetCredential = databaseUserData.getOffsetOfCredential(userId);
		rfData.seek(offsetCredential);
		String credential=rfData.readUTF();
		byte databaseId = rfData.readBoolean()?rfData.readByte():-1;
		long userid = rfData.readLong();
		String password = databaseId==DatabaseId.SystemAccount?rfData.readUTF():null;
		byte status = rfData.readByte();
		long timeCreateAccount = rfData.readLong();
		
		DBDescribe[] list = databaseUserData.des.readDescribes();
		
		System.out.print(DatabaseId.getName(databaseId)+"	status("+status+")	"+TimeManager.gI().getStringTime(timeCreateAccount)+"	Credential("+credential+")	userid("+userid+")	password("+password+")");
		int numberColumn=databaseUserData.getNumberColumn();
		for(int i=0;i<numberColumn;i++)
			System.out.print("	"+list[i].ColumnName+"("+databaseUserData.readData(userid, i)+")");
		
		System.out.println();
	}
	
	public void writeData(long offset,byte[] _data) throws IOException {rfData.seek(offset);rfData.write(_data);}
	public void writeLong(long offset,long _value) throws IOException {rfData.seek(offset);rfData.writeLong(_value);;}
	@Override public void close() {if(rfBTree!=null)try {rfBTree.close();rfBTree=null;} catch (IOException e) {e.printStackTrace();}if(rfData!=null)try {rfData.close();rfData=null;} catch (IOException e) {e.printStackTrace();}}
	@Override public void deleteFile(){try {Files.deleteIfExists(FileSystems.getDefault().getPath(path));} catch (IOException e) {e.printStackTrace();}try {Files.deleteIfExists(FileSystems.getDefault().getPath(path+INDEX_EXTENSION));} catch (IOException e) {e.printStackTrace();}}
}
