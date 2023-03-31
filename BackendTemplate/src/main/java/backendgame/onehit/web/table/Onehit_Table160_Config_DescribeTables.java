package backendgame.onehit.web.table;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BaseBackEnd_Session;
import backendgame.config.CMD_ONEHIT;
import backendgame.config.CaseCheck;
import backendgame.config.PATH;
import backendgame.onehit.BaseOnehit_AiO;
import backendgame.onehit.BinaryToken;
import database.DescribeTable;
import database.table.DBGameTable_UserData;
import database.table.DBString;

public class Onehit_Table160_Config_DescribeTables extends BaseOnehit_AiO {

	public Onehit_Table160_Config_DescribeTables() {
		super(CMD_ONEHIT.BBWeb_Table_Config_DescribeTables);
	}
	
	
	private String getCloneName(short _tableId) {
		File file;
		String currentPath = PATH.DATABASE_FOLDER+"/"+_tableId+"/"+PATH.UserData;
		for(int i=0;i<10;i++) {
			file=new File(currentPath+i);
			if(file.exists())
				file.delete();
			else
				return currentPath+i;
		}
		return null;
	}
	
	@Override public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
		BinaryToken binaryToken = new BinaryToken();
		if (binaryToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (currentTimeMillis > binaryToken.timeOut)
			return mgExpired;
		short tableId = messageReceiving.readShort();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(tableId<0 || binaryToken.adminId<1)// Validate TableId và UserId
			return mgInvalid;
		if(messageReceiving.avaiable()>256)
			return mgOutOfRange;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		short numberNewDescribeTables = messageReceiving.readShort();
		DescribeTable[] newDescribeTables = new DescribeTable[numberNewDescribeTables];
		DescribeTable describeTable;
		for(short i=0;i<numberNewDescribeTables;i++) {
			describeTable=new DescribeTable();
			describeTable.readMessage(messageReceiving);
			newDescribeTables[i]=describeTable;
			describeTable.trace();
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		MessageSending mgResult=null;
		DBString dbString=null;
		DBGameTable_UserData databaseUserData=null;
		try {
			databaseUserData=new DBGameTable_UserData(tableId);
			dbString=new DBString(tableId);
			if(System.currentTimeMillis() > databaseUserData.timeAvaiable)
				mgResult = mgExpired;
			else if(binaryToken.adminId != databaseUserData.adminId)
				mgResult = mgPlayerWrong;
			else if(databaseUserData.countRow()==0) {
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				databaseUserData.updateDescribeTable(newDescribeTables, dbString);
				mgResult=mgOK;
			}/*else mgResult=null*/
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);
			mgResult.writeString(getStringException(e));
		}
		
		if(mgResult!=null) {
			if (databaseUserData != null)
				databaseUserData.close();
			if (dbString != null)
				dbString.close();
			return mgResult;
		}
		
		String pathClone = getCloneName(tableId);
		if(pathClone==null) {
			databaseUserData.close();
			dbString.close();
			return mgExist;
		}
		//////////////////////////////////////////////////////////////
		RandomAccessFile rfClone=null;
		try {
			rfClone=new RandomAccessFile(pathClone, "rw");
			databaseUserData.cloneWithData(rfClone, newDescribeTables, dbString);
			databaseUserData.close();
			rfClone.close();
			new File(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.UserData).delete();
			new File(pathClone).renameTo(new File(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.UserData));
			mgResult = mgOK;
		} catch (IOException e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);
			mgResult.writeString(getStringException(e));
		}
		
		databaseUserData.close();
		dbString.close();
		return mgResult;
	}
	
}
