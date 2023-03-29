package backendgame.onehit.web.table.sub.leaderboard;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import database.table.sub.DBGameTable_Leaderboard;

public class OnehitWeb_Leaderboard422_FullQuerry_Range extends BaseOnehit_Leaderboard_FullQuerry {

	public OnehitWeb_Leaderboard422_FullQuerry_Range() {super(CMD_ONEHIT.BBWeb_Leaderboard_FullQuerry_Range);}

	@Override public short[] onGetIndex(DBGameTable_Leaderboard leaderboard, MessageReceiving messageReceiving) throws Exception {
		short indexBegin = messageReceiving.readShort();
		short indexEnd = messageReceiving.readShort();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return null;
		////////////////////////////////////////////////////////////////////////////////////////////////
		short countRow = leaderboard.dataLength;

		if(indexBegin<0)
			indexBegin=0;
		if(indexBegin>countRow-1)
			indexBegin=(short) (countRow-1);
		
		if(indexEnd<0)
			indexEnd=0;
		if(indexEnd>countRow-1)
			indexEnd=(short) (countRow-1);
		////////////////////////////////////////////////////////////////////////////////////////////////
		short[] list = null;
		
		if(indexBegin<indexEnd) {
			list = new short[indexEnd - indexBegin + 1];
			for(short i=indexBegin;i<=indexEnd;i++)
				list[i-indexBegin]=i;
		}else {
			list = new short[indexBegin - indexEnd + 1];
			for(short i=indexBegin;i>=indexEnd;i--)
				list[indexBegin-i]=i;
		}
		
		return list;
	}

}
