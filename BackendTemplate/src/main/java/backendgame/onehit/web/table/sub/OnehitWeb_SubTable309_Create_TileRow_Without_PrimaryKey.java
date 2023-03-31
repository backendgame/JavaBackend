package backendgame.onehit.web.table.sub;

import java.io.File;
import java.io.RandomAccessFile;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import backendgame.config.CaseCheck;
import backendgame.onehit.BaseOnehitWeb;
import backendgame.onehit.BinaryToken;
import database.BaseTableData;
import database.DescribeTable;
import database.SubTable;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_TileRow;

public class OnehitWeb_SubTable309_Create_TileRow_Without_PrimaryKey extends BaseOnehitWeb {

	public OnehitWeb_SubTable309_Create_TileRow_Without_PrimaryKey() {super(CMD_ONEHIT.BBWeb_SubTable_Create_TileRow_Without_PrimaryKey);}

	@Override public MessageSending onProcessDatabase(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, BinaryToken richardToken, MessageReceiving messageReceiving) throws Exception {
		short numberNewDescribeTables = messageReceiving.readShort();
		DescribeTable[] newDescribeTables = new DescribeTable[numberNewDescribeTables];
		DescribeTable describeTable;
		for(short i=0;i<numberNewDescribeTables;i++) {
			describeTable=new DescribeTable();
			describeTable.readMessage(messageReceiving);
			newDescribeTables[i]=describeTable;
		}
		////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////
		MessageSending mgResult=null;
		RandomAccessFile rf=null;
		DBGameTable_TileRow tileRow=null;
		try {
			short subTable = databaseUserData.countSubTable();
			File dir = new File(SubTable.getDirectory(databaseUserData.tableId, subTable));if(dir.exists()==false)dir.mkdir();
			rf = new RandomAccessFile(SubTable.getSubFile(databaseUserData.tableId, subTable), "rw");
			rf.write(databaseUserData.read(0, (int) SubTable.HEADER_LENGTH));
			rf.writeByte(SubTable.SubType_Tile_Row);
			
			rf.seek(BaseTableData.Offset_TimeCreate);
			rf.writeLong(currentTimeMillis);
			
			databaseUserData.updateCountSubTable(subTable+1);
			tileRow=new DBGameTable_TileRow(databaseUserData.tableId, subTable);
			tileRow.updateDescribeTable(newDescribeTables, dbString);

			MessageSending messageSending=new MessageSending(cmd,CaseCheck.HOPLE);
			messageSending.writeShort(subTable);
			mgResult = messageSending;
		}catch (Exception e) {
			e.printStackTrace();
			mgResult=new MessageSending(cmd, CaseCheck.EXCEPTION);
			mgResult.writeString(getStringException(e));
		}
		
		if(rf!=null)rf.close();
		if(tileRow!=null)tileRow.close();
		return mgResult;
	}
	
}
