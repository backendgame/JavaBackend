package backendgame.onehit.web.table.sub.leaderboard;

import backendgame.com.core.MessageReceiving;
import backendgame.config.CMD_ONEHIT;
import database.table.sub.DBGameTable_Leaderboard;

public class OnehitWeb_Leaderboard421_FullQuerry_Index extends BaseOnehit_Leaderboard_FullQuerry {

	public OnehitWeb_Leaderboard421_FullQuerry_Index() {super(CMD_ONEHIT.BBWeb_Leaderboard_FullQuerry_Index);}

	@Override
	public short[] onGetIndex(DBGameTable_Leaderboard leaderboard, MessageReceiving messageReceiving) throws Exception {
		short numberIndex = messageReceiving.readShort();
		short[] listIndex = new short[numberIndex];
		for(short i=0;i<numberIndex;i++) {
			listIndex[i] = messageReceiving.readShort();
			if(listIndex[i]<0)
				return null;
		}
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return null;
		////////////////////////////////////////////////////////////////////////////////////////////////
		return listIndex;
	}

}
