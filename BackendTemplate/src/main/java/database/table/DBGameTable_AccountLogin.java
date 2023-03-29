package database.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.config.PATH;
import bgcore.core.MessageSending;
import bgcore.core.OneHitProcessing;
import database.DatabaseId;
import database.Manager_DBGameTable;

public class DBGameTable_AccountLogin {
	public static Manager_DBGameTable manager=null;
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public short tableId;
	public RandomAccessFile rfData;
	public RandomAccessFile rfBTree;
	private byte[] data;
	private int dataLength;
	private byte[] dataCompare;
	private int compare;
	private long l;
	public DBGameTable_AccountLogin(short _tableId) throws IOException{
		tableId=_tableId;
		rfData=null;
		rfBTree=null;
		try {
			rfData = new RandomAccessFile(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.AccountLogin_Data, "rw");
			rfBTree = new RandomAccessFile(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.AccountLogin_Index, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			close();
			throw new IOException();
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void writeInfoByOffset(MessageSending messageSending,long offset) throws IOException{
		rfData.seek(offset);
		short _length = rfData.readShort();
		
		byte[] _data = new byte[_length-1];
		rfData.read(_data);
		byte _databaseId = rfData.readByte();
		
		messageSending.writeString(new String(_data));//Credential
		messageSending.writeByte(_databaseId);//DatabaseId
		
		if(_databaseId==DatabaseId.SystemAccount || _databaseId==DatabaseId.EmailCode) {
//			rfData.seek(offset + 2 + _length + 8);
			rfData.skipBytes(8);
		}
		
		messageSending.writeLong(rfData.readLong());//TimeCreate
		messageSending.writeLong(rfData.readLong());//UserId
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private byte[] toBytes(String credential,byte databaseId) {
		byte[] dataString = credential.getBytes();
		int length = dataString.length;
		byte[] dataGroup = new byte[length+1];
		
		dataGroup[length]=databaseId;
		for(int i=0;i<length;i++)
			dataGroup[i]=dataString[i];
		return dataGroup;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getCredentialByLocation(long offset) throws IOException {
		rfData.seek(offset);
		byte[] dataStr = new byte[rfData.readShort()-1];
		rfData.read(dataStr);
		return new String(dataStr);
	}
	public long getOffset(String credential,byte databaseId) throws IOException {
		return getOffset(toBytes(credential, databaseId));
	}
	public long getUserId(long offset) throws IOException {
		rfData.seek(offset);
		short credentialLength = rfData.readShort();
		rfData.skipBytes(credentialLength-1);
		byte databaseId = rfData.readByte();
		if(databaseId==DatabaseId.SystemAccount || databaseId==DatabaseId.EmailCode)
			rfData.skipBytes(16);
		else
			rfData.skipBytes(8);
		return rfData.readLong();
	}

	private long rfDataWrite(long location, byte[] _data, long passwordLocation, DBGameTable_UserData dbUserData) throws IOException {
		long userIdCreate = dbUserData.countRow();
		
		rfData.seek(location);
		rfData.writeShort(_data.length);
		rfData.write(_data);
		if(_data[_data.length-1]==DatabaseId.SystemAccount || _data[_data.length-1]==DatabaseId.EmailCode)
			rfData.writeLong(passwordLocation);
		rfData.writeLong(OneHitProcessing.currentTimeMillis);//Time create account
		rfData.writeLong(userIdCreate);
		dbUserData.insert(location);
		
		return userIdCreate;
	}
	public long insertRow(String credential,byte databaseId, long passwordLocation, DBGameTable_UserData dbUserData) throws IOException {
		byte[] _data=toBytes(credential, databaseId);
		if(rfBTree.length()==0) {
			long beginLocation = rfData.length();
			long userIdCreate = rfDataWrite(beginLocation, _data, passwordLocation, dbUserData);
			rfBTree.seek(0);
			rfBTree.writeLong(beginLocation);
//			return beginLocation;
			return userIdCreate;
		}
		////////////////////////////////////////////////////////////////////
		synchronized (manager.lockTable[tableId]) {
			long id = getOffset(_data);
			if(id>-1)
				return -1;
			
			long location = rfData.length();
			long userIdCreate = rfDataWrite(location, _data, passwordLocation, dbUserData);
			//////////////////////l luôn trùng với r
			long lengBTree = rfBTree.length();
			if(compare<0) {//S nằm bên trái l
				if(l*8==lengBTree) {
					rfBTree.seek(lengBTree);
					rfBTree.writeLong(location);
				}else {
					_data=new byte[(int) (lengBTree - l*8)];
					rfBTree.seek(l*8);
					rfBTree.read(_data);
					
					rfBTree.seek(l*8);
					rfBTree.writeLong(location);
					rfBTree.write(_data);
				}
			}else {//S nằm bên phải l
				l++;//Dịch qua phải 1 vị trí
				if(l*8==lengBTree) {//lúc khởi tạo thì r = length/8 -1
					rfBTree.seek(lengBTree);
					rfBTree.writeLong(location);
				}else {
					_data=new byte[(int) (lengBTree - l*8)];
					rfBTree.seek(l*8);
					rfBTree.read(_data);
					
					rfBTree.seek(l*8);
					rfBTree.writeLong(location);
					rfBTree.write(_data);
				}
			}
//			return location;
			return userIdCreate;
		}
	}
	
	
	private long getOffset(byte[] _data) throws IOException {
		if(rfBTree.length()==0)
			return -1;
		
		data = _data;
		dataLength = data.length;
		dataCompare = new byte[dataLength];
		
		l = 0;
		long r = rfBTree.length()/8 - 1;//r là nơi sẽ indexData mới
		long m;
		while (l < r) {
			m = (l + r) / 2;
			
			calculateCompare(m);
			if(compare==0) {
				rfBTree.seek(m*8);
				return rfBTree.readLong();
			}
			
			if(compare>0)
				l = m + 1;
			else
				r = m - 1;
		}
		
		calculateCompare(l);
		if(compare==0){
			rfBTree.seek(l*8);
			return rfBTree.readLong();
		}

		return -1;
	}
	
	private void calculateCompare(long id) throws IOException {
		rfBTree.seek(id*8);
		rfData.seek(rfBTree.readLong());
		compare = dataLength - rfData.readShort();
		if(compare==0) {
			rfData.read(dataCompare);
			for(int i=0;i<dataLength;i++)
				if(data[i]!=dataCompare[i]) {
					compare = data[i]-dataCompare[i];
					return;
				}
			compare=0;
		}		
	}

	public void close() {if(rfBTree!=null)try {rfBTree.close();rfBTree=null;} catch (IOException e) {e.printStackTrace();}if(rfData!=null)try {rfData.close();rfData=null;} catch (IOException e) {e.printStackTrace();}}
	/////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////
	public void trace() {
		try {
			trace((int) (rfBTree.length()/8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void trace(int _length) throws IOException{
		System.out.println("rfData("+rfData.length()+")	rfBTree("+rfBTree.length()+")	==>	Row : "+(rfBTree.length()/8));
		int length = (int) (rfBTree.length()/8);
		length = length<_length?length:_length;
	
		rfBTree.seek(0);
		for(int i=0;i<length;i++) {
			long location = rfBTree.readLong();
			rfData.seek(location);
			byte[] str=new byte[rfData.readShort()-1];
			byte databaseId = rfData.readByte();
			rfData.read(str);
			
			switch (databaseId) {
				case DatabaseId.Device:System.out.println("	Device("+i+") = "+new String(str)+"		Btree("+location+")	");break;
				case DatabaseId.SystemAccount:System.out.println("	SystemAccount("+i+") = "+new String(str)+"		Btree("+location+")	");break;
				case DatabaseId.Facebook:System.out.println("	Facebook("+i+") = "+new String(str)+"		Btree("+location+")	");break;
				case DatabaseId.Google:System.out.println("	Google("+i+") = "+new String(str)+"		Btree("+location+")	");break;
				case DatabaseId.AdsId:System.out.println("	AdsId("+i+") = "+new String(str)+"		Btree("+location+")	");break;
				case DatabaseId.Apple:System.out.println("	Apple("+i+") = "+new String(str)+"		Btree("+location+")	");break;
				case DatabaseId.EmailCode:System.out.println("	EmailCode("+i+") = "+new String(str)+"		Btree("+location+")	");break;
				default:break;
			}
		}
	}	
}
