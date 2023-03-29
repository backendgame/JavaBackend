package backendgame.onehit.web.table.sub.tile_binary;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import database.table.DBString;
import database.table.sub.DBGameTable_TileBinary;

public class OnehitWeb_Sub_TileBinary620_Querry_Index extends BaseOnehit_Sub_TileBinary_Querry {

	public OnehitWeb_Sub_TileBinary620_Querry_Index() {super(CMD_ONEHIT.BBWeb_SubTileBinary_Querry_Index);}

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