package begame.onehit.web.table;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BaseBackEnd_Session;
import backendgame.com.database.DBString;
import begame.config.CMD_ONEHIT;
import begame.config.CaseCheck;
import begame.config.PATH;
import begame.onehit.BaseOnehit_AiO;
import begame.onehit.BinaryToken;
import begame.onehit.web.dto.DTO_Create_Table;
import begame.onehit.web.dto.DTO_Describe;
import database_game.table.DBGameTable_UserData;

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
		DTO_Describe[] newDescribeTables = new DTO_Describe[numberNewDescribeTables];
		for(short i=0;i<numberNewDescribeTables;i++)
			newDescribeTables[i].readMessage(messageReceiving);
		
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
			dbString=new DBString(PATH.dbStringName(createTable.tableId));
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
