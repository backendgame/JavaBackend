package database.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.core.TimeManager;
import backendgame.config.PATH;
import database.DatabaseId;

public class Querry_AccountLogin {
	public short tableId;
	public RandomAccessFile rfData;
	public RandomAccessFile rfBTree;
	public Querry_AccountLogin(short _tableId) throws IOException{
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
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long[] getUserId(String credential) throws IOException {
		long[] listOffset = getOffset(credential);
		if(listOffset==null)
			return null;
		
		byte databaseId;
		short tmp;
		short numberUserId=(short) listOffset.length;
		for(short i=0;i<numberUserId;i++) {
			rfData.seek(listOffset[i]);
			tmp=rfData.readShort();
			rfData.seek(listOffset[i]+2+tmp-1);
			databaseId=rfData.readByte();
			if(databaseId==DatabaseId.SystemAccount || databaseId==DatabaseId.EmailCode)
				rfData.seek(listOffset[i]+2+tmp+16);
			else
				rfData.seek(listOffset[i]+2+tmp+8);
			listOffset[i] = rfData.readLong();
		}
				
		return listOffset;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long[] getOffset(String credential) throws IOException {
		byte[] data = credential.getBytes();
		short dataLength = (short) data.length;
		byte[] dataCompare = new byte[dataLength];
		
		long btree = getBtreeId(dataLength, dataCompare, data);
		if(btree==-1)
			return null;
		
		long left = btree;
		while(left-1>0 && calculateCompare(left-1, dataLength, dataCompare, data)==0)
			left--;
		
		long countAccount = rfBTree.length()/8;
		long right = btree;
		while(right+1<countAccount && calculateCompare(right+1, dataLength, dataCompare, data)==0)
			right++;
		
		int size = (int) (right-left+1);
		long[] result = new long[size];
		for(int i=0;i<size;i++) {
			rfBTree.seek((left+i)*8);
			result[i] = rfBTree.readLong();
		}
		
		return result;
	}
	
	private long getBtreeId(short dataLength, byte[] dataCompare, byte[] data) throws IOException {
		if(rfBTree.length()==0)
			return -1;
		
		long l = 0;
		long r = rfBTree.length()/8 - 1;//r là nơi sẽ indexData mới
		long m;
		int compare;
		while (l < r) {
			m = (l + r) / 2;
			
			compare = calculateCompare(m, dataLength, dataCompare, data);
			if(compare==0)
				return m;
			
			if(compare>0)
				l = m + 1;
			else
				r = m - 1;
		}
		
		compare = calculateCompare(l, dataLength, dataCompare, data);
		if(compare==0)
			return l;

		return -1;
	}
	public short calculateCompare(long btreeId, short dataLength, byte[] dataCompare, byte[] data) throws IOException {
		rfBTree.seek(btreeId*8);
		rfData.seek(rfBTree.readLong());
		short compare = (short) (dataLength + 1 - rfData.readShort());
		if(compare==0) {
			rfData.read(dataCompare);
			for(int i=0;i<dataLength;i++)
				if(data[i]!=dataCompare[i])
					return (short) (data[i]-dataCompare[i]);
			return 0;
		}else
			return compare;
	}
	
	
	
	

	public void close() {if(rfBTree!=null)try {rfBTree.close();rfBTree=null;} catch (IOException e) {e.printStackTrace();}if(rfData!=null)try {rfData.close();rfData=null;} catch (IOException e) {e.printStackTrace();}}
	/////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////
	public void traceAccount(long offset, DBString dbString) throws IOException {
		rfData.seek(offset);
		short length = rfData.readShort();
		byte[]data = new byte[length-1];
		rfData.read(data);
		byte _databaseId = rfData.readByte();
		if(_databaseId==DatabaseId.SystemAccount || _databaseId==DatabaseId.EmailCode)
			System.out.println("Credential("+new String(data)+")	DatabaseId("+_databaseId+")	Password("+dbString.getStringByLocation(rfData.readLong())+")	TimeCreate("+TimeManager.gI().getStringTime(rfData.readLong())+")	userId : "+rfData.readLong());
		else
			System.out.println("Credential("+new String(data)+")	DatabaseId("+_databaseId+")	TimeCreate("+TimeManager.gI().getStringTime(rfData.readLong())+")	userId : "+rfData.readLong());
	}
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
