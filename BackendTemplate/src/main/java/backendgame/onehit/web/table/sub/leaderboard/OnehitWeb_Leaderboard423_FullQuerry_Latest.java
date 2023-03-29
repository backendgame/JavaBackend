package backendgame.onehit.web.table.sub.leaderboard;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import database.table.sub.DBGameTable_Leaderboard;

public class OnehitWeb_Leaderboard423_FullQuerry_Latest extends BaseOnehit_Leaderboard_FullQuerry {

	public OnehitWeb_Leaderboard423_FullQuerry_Latest() {super(CMD_ONEHIT.BBWeb_Leaderboard_FullQuerry_Latest);}

	@Override
	public short[] onGetIndex(DBGameTable_Leaderboard leaderboard, MessageReceiving messageReceiving) throws Exception {
		short numberIndex = messageReceiving.readShort();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false || numberIndex<0)
			return null;
		////////////////////////////////////////////////////////////////////////////////////////////////
		if (numberIndex > leaderboard.dataLength)
			numberIndex = leaderboard.dataLength;
		
		short[] listIndex = new short[numberIndex];
		for(short i=0;i<numberIndex;i++) {
			listIndex[i] = (short) (leaderboard.dataLength - i - 1);
		}
		
		return listIndex;
	}



}
