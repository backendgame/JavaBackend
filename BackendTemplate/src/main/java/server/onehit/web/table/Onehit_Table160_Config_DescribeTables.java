package server.onehit.web.table;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BackendSession;
import backendgame.com.database.DBDescribe;
import database_game.table.DBGame_UserData;
import server.config.CMD_ONEHIT;
import server.onehit.web.BaseOnehit_VerifyToken;

public class Onehit_Table160_Config_DescribeTables extends BaseOnehit_VerifyToken {

	public Onehit_Table160_Config_DescribeTables() {
		super(CMD_ONEHIT.BBWeb_Table_Config_DescribeTables);
	}
	
	private String getCloneName(String currentPath) {
		for(int i=0;i<100;i++)
			if(new File(currentPath+i).exists()==false)
				return currentPath+i;
		return null;
	}
	
	@Override protected MessageSending onPassToken(BackendSession session, MessageReceiving messageReceiving, short tableId) {
		short numberNewDescribeTables = messageReceiving.readShort();
		if(numberNewDescribeTables<0)
			return mgValueNull;
		
		DBDescribe[] newDes = new DBDescribe[numberNewDescribeTables];
		for(short i=0;i<numberNewDescribeTables;i++)
			newDes[i].readMessage(messageReceiving);
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		DBGame_UserData databaseOld=null;
		DBGame_UserData databaseNew=null;
		
		try {
			databaseOld=new DBGame_UserData(tableId);
			String pathClone = getCloneName(databaseOld.path);
			if(pathClone==null)
				throw new IOException("Can't not clone UserData -> UserData0-UserData99 is existed");
			else{
				DBDescribe[] oldDes = databaseOld.getDescribes();
				
				databaseNew=new DBGame_UserData(pathClone);
				databaseNew.tableId=tableId;
				databaseNew.setDescribe(newDes);
				
				////////////////////////////////////////////////////////////////////////////////////////////////setDescribe xong mới có dataLeng và defaultData
				byte[] defaultData = databaseNew.des.getDefaultData();
				long numberRow = databaseOld.countRow();
				if(defaultData==null || defaultData.length==0)
					for(long i=0;i<numberRow;i++)
						databaseNew.setCredentialOffset(i, databaseOld.getOffsetOfCredential(i));
				else {
					for(long i=0;i<numberRow;i++)
						databaseNew.reNewUser(i, databaseOld.getOffsetOfCredential(i), defaultData);
					
					ArrayList<DBDescribe> cloneDes = new ArrayList<>();
					if(newDes!=null && oldDes!=null && newDes.length>0 && oldDes.length>0)
						for(DBDescribe nDes:newDes)
							for(DBDescribe oDes:oldDes)
								if(nDes.Type==oDes.Type && nDes.ColumnName.equals(oDes.ColumnName))
									cloneDes.add(nDes);
					
					if(cloneDes.size()>0)
						for(DBDescribe dbDes:cloneDes) {
							int offsetRow_OLD = databaseOld.des.findDescribe_ByCoulmnId(dbDes.ColumnName).OffsetRow;
							for(long i=0;i<numberRow;i++)
								databaseNew.writeData(i, dbDes.OffsetRow, dbDes.Type, databaseOld.readData(i, offsetRow_OLD, dbDes.Type));
						}
				}
				////////////////////////////////////////////////////////////////////////////////////////////////
				databaseOld.close();
				databaseNew.close();
				
				databaseOld.deleteFile();
				databaseNew.changeFileName(tableId);//Đổi tên file
				return mgOK;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(databaseOld!=null)
			databaseOld.close();
		if(databaseNew!=null)
			databaseNew.close();
		return mgOK;
	}
	

//	
//	@Override public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
//		BinaryToken binaryToken = new BinaryToken();
//		if (binaryToken.decode(messageReceiving.readString()) == false)
//			return mgTokenError;
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		if (currentTimeMillis > binaryToken.timeOut)
//			return mgExpired;
//		short tableId = messageReceiving.readShort();
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		if(tableId<0 || binaryToken.adminId<1)// Validate TableId và UserId
//			return mgInvalid;
//		if(messageReceiving.avaiable()>256)
//			return mgOutOfRange;
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		short numberNewDescribeTables = messageReceiving.readShort();
//		DescribeTable[] newDescribeTables = new DescribeTable[numberNewDescribeTables];
//		DescribeTable describeTable;
//		for(short i=0;i<numberNewDescribeTables;i++) {
//			describeTable=new DescribeTable();
//			describeTable.readMessage(messageReceiving);
//			newDescribeTables[i]=describeTable;
//			describeTable.trace();
//		}
//		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		if(messageReceiving.validate()==false)
//			return mgVariableInvalid;
//		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		MessageSending mgResult=null;
//		DBString dbString=null;
//		DBGameTable_UserData databaseUserData=null;
//		try {
//			databaseUserData=new DBGameTable_UserData(tableId);
//			dbString=new DBString(tableId);
//			if(System.currentTimeMillis() > databaseUserData.timeAvaiable)
//				mgResult = mgExpired;
//			else if(binaryToken.adminId != databaseUserData.adminId)
//				mgResult = mgPlayerWrong;
//			else if(databaseUserData.countRow()==0) {
//				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//				databaseUserData.updateDescribeTable(newDescribeTables, dbString);
//				mgResult=mgOK;
//			}/*else mgResult=null*/
//		} catch (Exception e) {
//			e.printStackTrace();
//			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);
//			mgResult.writeString(getStringException(e));
//		}
//		
//		if(mgResult!=null) {
//			if (databaseUserData != null)
//				databaseUserData.close();
//			if (dbString != null)
//				dbString.close();
//			return mgResult;
//		}
//		
//		String pathClone = getCloneName(tableId);
//		if(pathClone==null) {
//			databaseUserData.close();
//			dbString.close();
//			return mgExist;
//		}
//		//////////////////////////////////////////////////////////////
//		RandomAccessFile rfClone=null;
//		try {
//			rfClone=new RandomAccessFile(pathClone, "rw");
//			databaseUserData.cloneWithData(rfClone, newDescribeTables, dbString);
//			databaseUserData.close();
//			rfClone.close();
//			new File(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.DBGameTable_UserData).delete();
//			new File(pathClone).renameTo(new File(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.DBGameTable_UserData));
//			mgResult = mgOK;
//		} catch (IOException e) {
//			e.printStackTrace();
//			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);
//			mgResult.writeString(getStringException(e));
//		}
//		
//		databaseUserData.close();
//		dbString.close();
//		return mgResult;
//	}

}
