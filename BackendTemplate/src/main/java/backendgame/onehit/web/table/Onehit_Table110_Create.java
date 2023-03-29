package backendgame.onehit.web.table;

import java.io.IOException;

import backendgame.config.CMD_ONEHIT;
import backendgame.config.CaseCheck;
import backendgame.model.dto.DTO_Create_Table;
import backendgame.onehit.BaseOnehit_AiO;
import backendgame.onehit.BinaryToken;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import bgcore.core.server.BaseBackEnd_Session;
import database.DescribeTable;
import database.table.DBGameTable_UserData;
import database.table.DBString;

public class Onehit_Table110_Create extends BaseOnehit_AiO {

	public Onehit_Table110_Create() {
		super(CMD_ONEHIT.BBWeb_Table_Create_Table);
	}
	
	
	@Override public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
		BinaryToken binaryToken = new BinaryToken();
		if (binaryToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		
		if(currentTimeMillis>binaryToken.timeOut)
			return mgTimeout;
		//////////////////////////////////////////////////////////////////////
		short numberNewDescribeTables = messageReceiving.readShort();
		DescribeTable[] newDescribeTables = new DescribeTable[numberNewDescribeTables];
		DescribeTable describeTable;
		for(short i=0;i<numberNewDescribeTables;i++) {
			describeTable=new DescribeTable();
			describeTable.readMessage(messageReceiving);
			newDescribeTables[i]=describeTable;
		}
		long tokenLifeTime = messageReceiving.readLong();
		//////////////////////////////////////////////////////////////////////
		if(numberNewDescribeTables<0)
			return mgValueNull;
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		//////////////////////////////////////////////////////////////////////
		DTO_Create_Table createTable = managerTable.createTable(binaryToken.adminId);
		if(createTable==null)
			return new MessageSending(cmd,CaseCheck.DATABASE_ERROR_EXIST);
		
		DBGameTable_UserData databaseUserData = null;
		DBString dbString = null;
		try {
			dbString=new DBString(createTable.tableId);
			databaseUserData=new DBGameTable_UserData(createTable.tableId);
			databaseUserData.updateDescribeTable(newDescribeTables, dbString);
			databaseUserData.setLong(DBGameTable_UserData.Offset_Token_LifeTime, tokenLifeTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(dbString!=null)
			dbString.close();
		if(databaseUserData!=null)
			databaseUserData.close();
		
		MessageSending messageSending = new MessageSending(cmd,CaseCheck.HOPLE);
		messageSending.writeShort(createTable.tableId);
		messageSending.writeLong(createTable.timeAvaiable);
		messageSending.writeLong(createTable.accessKey);
		messageSending.writeLong(createTable.readKey);
		messageSending.writeLong(createTable.writeKey);
		messageSending.writeLong(createTable.token_LifeTime);
		return messageSending;
	}

}
