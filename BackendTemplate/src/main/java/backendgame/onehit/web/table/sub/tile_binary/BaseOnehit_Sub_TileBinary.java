package backendgame.onehit.web.table.sub.tile_binary;

import java.io.File;

import backendgame.config.CaseCheck;
import backendgame.config.PATH;
import backendgame.onehit.BaseOnehit_AiO;
import backendgame.onehit.BinaryToken;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import bgcore.core.OneHitProcessing;
import bgcore.core.server.BaseBackEnd_Session;
import database.SubTable;
import database.table.DBString;
import database.table.sub.DBGameTable_TileBinary;

public abstract class BaseOnehit_Sub_TileBinary extends BaseOnehit_AiO{

	public BaseOnehit_Sub_TileBinary(short command_id) {super(command_id);}
	protected abstract MessageSending onProcess(DBGameTable_TileBinary tileBinary, DBString dbString, MessageReceiving messageReceiving) throws Exception;
	
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
		DBGameTable_TileBinary tileBinary=null;
		DBString dbString = null;
		try {
			tileBinary=new DBGameTable_TileBinary(tableId, subId);
			dbString = new DBString(tableId);
			if(System.currentTimeMillis() > tileBinary.timeAvaiable)
				mgResult = mgExpired;
			else if(binaryToken.adminId != tileBinary.adminId)
				mgResult = mgPlayerWrong;
			else if(tileBinary.getType() != SubTable.SubType_Tile_Binary)
				mgResult = mgDatabaseNotExist;
			else 
				mgResult = onProcess(tileBinary, dbString, messageReceiving);
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);//Tại vì có write thêm data
			mgResult.writeString(getStringException(e));
		}
		
		if(dbString!=null)dbString.close();
		if(tileBinary != null)tileBinary.close();
		return mgResult;
	}


	
	
}
