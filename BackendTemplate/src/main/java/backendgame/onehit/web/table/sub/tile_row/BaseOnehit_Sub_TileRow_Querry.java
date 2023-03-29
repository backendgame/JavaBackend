package backendgame.onehit.web.table.sub.tile_row;

import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.table.DBString;
import database.table.sub.DBGameTable_TileBinary;
import database.table.sub.DBGameTable_TileRow;

public abstract class BaseOnehit_Sub_TileRow_Querry extends BaseOnehit_Sub_TileRow{

	public BaseOnehit_Sub_TileRow_Querry(short command_id) {super(command_id);}

	public abstract long[] getListIndex(DBGameTable_TileBinary tileBinary, DBString dbString, MessageReceiving messageReceiving) throws Exception;
	

	@Override
	protected MessageSending onProcess(DBGameTable_TileRow tileRow, DBString dbString, MessageReceiving messageReceiving) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
