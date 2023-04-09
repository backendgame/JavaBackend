package gameonline.rest.controller_user.Table.sub.leaderboard;

import javax.validation.constraints.NotNull;

import backendgame.com.core.MessageSending;
import gameonline.config.CMD_ONEHIT;
import gameonline.rest.MyRespone;

public class Service_Leaderboard421_FullQuerry_Index extends BaseService_Leaderboard_Querry{
	@NotNull public long[] listIndex;
	
	public MyRespone respone() {
		short numberIndex = (short) listIndex.length;
		
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Leaderboard_FullQuerry_Index);
		messageSending.writeString(token);
		messageSending.writeShort(tableId);
		messageSending.writeShort(subTableID);
		
		messageSending.writeShort(numberIndex);
		for(short i=0;i<numberIndex;i++)
			messageSending.writeLong(listIndex[i]);
		
		return onOnehitFull_AccountUserData(messageSending);
	}
}
