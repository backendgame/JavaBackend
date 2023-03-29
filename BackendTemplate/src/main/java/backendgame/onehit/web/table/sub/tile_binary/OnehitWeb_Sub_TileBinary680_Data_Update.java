package backendgame.onehit.web.table.sub.tile_binary;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.table.DBString;
import database.table.sub.DBGameTable_TileBinary;

public class OnehitWeb_Sub_TileBinary680_Data_Update extends BaseOnehit_Sub_TileBinary {

	public OnehitWeb_Sub_TileBinary680_Data_Update() {super(CMD_ONEHIT.BBWeb_SubTileBinary_Data_Update);}

	@Override
	protected MessageSending onProcess(DBGameTable_TileBinary tileBinary, DBString dbString, MessageReceiving messageReceiving) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
