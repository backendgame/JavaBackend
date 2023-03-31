package backendgame.onehit.web.table.sub.tile_row;

import backendgame.com.core.MessageReceiving;
import backendgame.config.CMD_ONEHIT;
import database.table.DBString;
import database.table.sub.DBGameTable_TileBinary;

public class OnehitWeb_Sub_TileRow722_Querry_Latest extends BaseOnehit_Sub_TileRow_Querry {

	public OnehitWeb_Sub_TileRow722_Querry_Latest() {super(CMD_ONEHIT.BBWeb_SubTileRow_Querry_Latest);}

	@Override
	public long[] getListIndex(DBGameTable_TileBinary tileBinary, DBString dbString, MessageReceiving messageReceiving) throws Exception {
		short numberData = messageReceiving.readShort();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return null;
		////////////////////////////////////////////////////////////////////////////////////////////////
		long maxUserId = tileBinary.countRow() - 1;
		
		if(numberData>maxUserId)
			numberData=(short) maxUserId;
		
		long[] listIndex = new long[numberData];
		for(short i=0;i<numberData;i++)
			listIndex[i] = maxUserId - i;
		
		return listIndex;
	}


}
