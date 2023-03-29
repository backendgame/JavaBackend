package backendgame.onehit.web.table.sub.tile_row;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import database.table.DBString;
import database.table.sub.DBGameTable_TileBinary;

public class OnehitWeb_Sub_TileRow721_Querry_Range extends BaseOnehit_Sub_TileRow_Querry {

	public OnehitWeb_Sub_TileRow721_Querry_Range() {super(CMD_ONEHIT.BBWeb_SubTileRow_Querry_Range);}

	@Override
	public long[] getListIndex(DBGameTable_TileBinary tileBinary, DBString dbString, MessageReceiving messageReceiving) throws Exception {
		long indexBegin = messageReceiving.readLong();
		long indexEnd = messageReceiving.readLong();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return null;
		////////////////////////////////////////////////////////////////////////////////////////////////
		long countRow = tileBinary.countRow();

		if(indexBegin<0)
			indexBegin=0;
		if(indexBegin>countRow-1)
			indexBegin=countRow-1;
		
		if(indexEnd<0)
			indexEnd=0;
		if(indexEnd>countRow-1)
			indexEnd=countRow-1;
		////////////////////////////////////////////////////////////////////////////////////////////////
		return rangeToArray(indexBegin, indexEnd);
	}



}
