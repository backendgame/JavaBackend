package backendgame.onehit.web.table.sub.tile_primarykey;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import database.DescribeTable;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_SubUserData;

public class OnehitWeb_SubTable500_Config_DescribeTables extends BaseOnehit_SubTable {

	public OnehitWeb_SubTable500_Config_DescribeTables() {super(CMD_ONEHIT.BBWeb_SubTable_Config_DescribeTables);}

	@Override
	public MessageSending onProcess(DBGameTable_SubUserData subTable, DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception {
		short numberNewDescribeTables = messageReceiving.readShort();
		DescribeTable[] newDescribeTables = new DescribeTable[numberNewDescribeTables];
		DescribeTable describeTable;
		for(short i=0;i<numberNewDescribeTables;i++) {
			describeTable=new DescribeTable();
			describeTable.readMessage(messageReceiving);
			newDescribeTables[i]=describeTable;
		}
		
		subTable.updateDescribeTable(newDescribeTables, dbString);
		return mgOK;
	}






}
