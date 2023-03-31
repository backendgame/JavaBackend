package backendgame.onehit.web.table.sub.tile_row;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import database.table.DBString;
import database.table.sub.DBGameTable_TileRow;

public class OnehitWeb_Sub_TileRow780_Data_Update extends BaseOnehit_Sub_TileRow {

	public OnehitWeb_Sub_TileRow780_Data_Update() {super(CMD_ONEHIT.BBWeb_SubTileRow_Data_Update);}

	@Override
	protected MessageSending onProcess(DBGameTable_TileRow tileRow, DBString dbString, MessageReceiving messageReceiving) {
		// TODO Auto-generated method stub
		return null;
	}




}
