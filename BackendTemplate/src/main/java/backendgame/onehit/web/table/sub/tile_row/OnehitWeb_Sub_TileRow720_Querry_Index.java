package backendgame.onehit.web.table.sub.tile_row;

import backendgame.com.core.MessageReceiving;
import backendgame.config.CMD_ONEHIT;
import database.table.DBString;
import database.table.sub.DBGameTable_TileBinary;

public class OnehitWeb_Sub_TileRow720_Querry_Index extends BaseOnehit_Sub_TileRow_Querry {

	public OnehitWeb_Sub_TileRow720_Querry_Index() {super(CMD_ONEHIT.BBWeb_SubTileRow_Querry_Index);}

	@Override
	public long[] getListIndex(DBGameTable_TileBinary tileBinary, DBString dbString, MessageReceiving messageReceiving) throws Exception {
		short numberData = messageReceiving.readShort();
		long[] listIndex = new long[numberData];
		for(short i=0;i<numberData;i++)
			listIndex[i] = messageReceiving.readLong();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return null;
		////////////////////////////////////////////////////////////////////////////////////////////////
		return listIndex;
	}



}
