package server.onehit.web.table;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BackendSession;
import server.config.CMD_ONEHIT;
import server.onehit.BaseOnehit_AiO;

public class Onehit_Table120_GetInfo extends BaseOnehit_AiO {

	public Onehit_Table120_GetInfo() {
		super(CMD_ONEHIT.BBWeb_Table_Get_Info);
	}

	@Override
	public MessageSending onMessage(BackendSession arg0, MessageReceiving arg1) {
		// TODO Auto-generated method stub
		return null;
	}

//	private void writeFileInFolder(MessageSending messageSending,String pathDirectory) {
//		File directory = new File(pathDirectory);
//		if(directory.exists()==false) {
//			messageSending.writeByte((byte) 0);
//			return;
//		}
//		
//		File[] listFile = directory.listFiles();
//		if(listFile==null) {
//			messageSending.writeByte((byte) 0);
//			return;
//		}
//		
//		byte numberFile = (byte) listFile.length;
//		messageSending.writeByte(numberFile);
//		for(byte i=0;i<numberFile;i++) {
//			messageSending.writeString(listFile[i].getName());
//			messageSending.writeLong(listFile[i].length());
//		}
//	}
//	
//	protected MessageSending onHeaderInfo(DBString dbString, DBGameTable_UserData databaseUserData, MessageReceiving messageReceiving) throws IOException {
//		RandomAccessFile rf = databaseUserData.rf;
//		short _tableId=databaseUserData.tableId;
//		
//		
//		MessageSending messageSending=new MessageSending(cmd,CaseCheck.HOPLE);
//		messageSending.writeCopyData(databaseUserData.read(0, 133));
//		
//		rf.seek(DBGameTable_UserData.Offset_Email);
//		byte numberEmail = rf.readByte();
//		messageSending.writeByte(numberEmail);
//		for(byte i=0;i<numberEmail;i++) {
//			messageSending.writeString(rf.readUTF());//Gmail
//			messageSending.writeString(rf.readUTF());//Password
//		}
//		
//		DBDescribe[] listDescribeTables=databaseUserData.getDescribeTables(dbString);
//		if(listDescribeTables==null)
//			messageSending.writeShort((short) 0);
//		else {
//			messageSending.writeShort((short) listDescribeTables.length);
//			for(DBDescribe describeTable:listDescribeTables) {
//				describeTable.writeMessage(messageSending);
////				describeTable.trace("");
//			}
//		}
//		
//		messageSending.writeLong(rf.length());
//		messageSending.writeLong(new File(PATH.DATABASE_FOLDER+"/"+_tableId+"/"+PATH.AccountLogin_Data).length());
//		messageSending.writeLong(new File(PATH.DATABASE_FOLDER+"/"+_tableId+"/"+PATH.AccountLogin_Index).length());
//		messageSending.writeLong(new File(PATH.DATABASE_FOLDER+"/"+_tableId+"/"+PATH.StringData).length());
//		messageSending.writeLong(new File(PATH.DATABASE_FOLDER+"/"+_tableId+"/"+PATH.StringIndex).length());
//		
//		
//		short numberSubTable = databaseUserData.countSubTable();
//		messageSending.writeShort(numberSubTable);
//		for(short i=0;i<numberSubTable;i++) 
//			writeFileInFolder(messageSending, SubTable.getDirectory(_tableId, i));
//		
//		return messageSending;
//	}
//	
//	
//	@Override public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
//		BinaryToken binaryToken = new BinaryToken();
//		if (binaryToken.decode(messageReceiving.readString()) == false)
//			return mgTokenError;
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		if (OneHitProcessing.currentTimeMillis > binaryToken.timeOut)
//			return mgExpired;
//		short tableId = messageReceiving.readShort();
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		if(tableId<0 || binaryToken.adminId<1)// Validate TableId và UserId
//			return mgInvalid;
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		MessageSending mgResult=null;
//		DBString dbString=null;
//		DBGameTable_UserData databaseUserData=null;
//		try {
//			dbString=new DBString(tableId);
//			databaseUserData=new DBGameTable_UserData(tableId);
//			if(System.currentTimeMillis() > databaseUserData.timeAvaiable + TIME.Day_30)
//				mgResult = mgExpired;
//			else if(binaryToken.adminId != databaseUserData.adminId)
//				mgResult = mgPlayerWrong;
//			else 
//				mgResult = onHeaderInfo(dbString, databaseUserData, messageReceiving);
//		} catch (Exception e) {
////			e.printStackTrace();//Không hiển thị database không tồn tại
//			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);//Tại vì có write thêm data
//			mgResult.writeString(getStringException(e));
//		}
//		
//		if (databaseUserData != null)
//			databaseUserData.close();
//		if (dbString != null)
//			dbString.close();
//
//		return mgResult;
//	}
}
