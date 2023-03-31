package backendgame.onehit.web.table.sub.tile_binary;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import database.table.DBString;
import database.table.sub.DBGameTable_TileBinary;

public abstract class BaseOnehit_Sub_TileBinary_Querry extends BaseOnehit_Sub_TileBinary {

	public BaseOnehit_Sub_TileBinary_Querry(short command_id) {super(command_id);}
	
	public abstract long[] getListIndex(DBGameTable_TileBinary tileBinary, DBString dbString, MessageReceiving messageReceiving) throws Exception;
	
	@Override
	protected MessageSending onProcess(DBGameTable_TileBinary tileBinary, DBString dbString, MessageReceiving messageReceiving) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
