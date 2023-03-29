package backendgame.onehit.web.table.sub.tile_row;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.table.DBString;
import database.table.sub.DBGameTable_TileRow;

public class OnehitWeb_Sub_TileRow750_Data_Insert extends BaseOnehit_Sub_TileRow {

	public OnehitWeb_Sub_TileRow750_Data_Insert() {super(CMD_ONEHIT.BBWeb_SubTileRow_Data_Insert);}

	@Override
	protected MessageSending onProcess(DBGameTable_TileRow tileRow, DBString dbString, MessageReceiving messageReceiving) {
		// TODO Auto-generated method stub
		return null;
	}




}
