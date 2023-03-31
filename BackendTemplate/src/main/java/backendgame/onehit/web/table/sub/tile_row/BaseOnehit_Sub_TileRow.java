package backendgame.onehit.web.table.sub.tile_row;

import java.io.File;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OneHitProcessing;
import backendgame.com.core.server.BaseBackEnd_Session;
import backendgame.config.CaseCheck;
import backendgame.config.PATH;
import backendgame.onehit.BaseOnehit_AiO;
import backendgame.onehit.BinaryToken;
import database.SubTable;
import database.table.DBString;
import database.table.sub.DBGameTable_TileRow;

public abstract class BaseOnehit_Sub_TileRow extends BaseOnehit_AiO{

	public BaseOnehit_Sub_TileRow(short command_id) {super(command_id);}
	protected abstract MessageSending onProcess(DBGameTable_TileRow tileRow, DBString dbString, MessageReceiving messageReceiving);
	
	@Override public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
		BinaryToken binaryToken = new BinaryToken();
		if (binaryToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (OneHitProcessing.currentTimeMillis > binaryToken.timeOut)
			return mgExpired;
		short tableId = messageReceiving.readShort();
		short subId = messageReceiving.readShort();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(tableId<0 || subId<0 || binaryToken.adminId<1)// Validate TableId và UserId
			return mgInvalid;
		if(new File(PATH.DATABASE_FOLDER+"/"+tableId).exists()==false)
			return mgNotExist;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		MessageSending mgResult;
		DBGameTable_TileRow tileRow=null;
		DBString dbString = null;
		try {
			tileRow=new DBGameTable_TileRow(tableId, subId);
			dbString = new DBString(tableId);
			if(System.currentTimeMillis() > tileRow.timeAvaiable)
				mgResult = mgExpired;
			else if(binaryToken.adminId != tileRow.adminId)
				mgResult = mgPlayerWrong;
			else if(tileRow.getType() != SubTable.SubType_Tile_Row)
				mgResult = mgDatabaseNotExist;
			else 
				mgResult = onProcess(tileRow, dbString, messageReceiving);
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);//Tại vì có write thêm data
			mgResult.writeString(getStringException(e));
		}
		
		if(dbString!=null)dbString.close();
		if(tileRow != null)tileRow.close();
		return mgResult;
	}


	
	
}
