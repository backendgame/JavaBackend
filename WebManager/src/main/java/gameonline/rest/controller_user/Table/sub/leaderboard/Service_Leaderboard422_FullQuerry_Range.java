package gameonline.rest.controller_user.Table.sub.leaderboard;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import backendgame.com.core.MessageSending;
import gameonline.config.CMD_ONEHIT;
import gameonline.rest.MyRespone;

public class Service_Leaderboard422_FullQuerry_Range extends BaseService_Leaderboard_Querry{
	@NotNull @PositiveOrZero public short indexBegin;
	@NotNull @PositiveOrZero public short indexEnd;
	
	public MyRespone respone() {
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Leaderboard_FullQuerry_Range);
		messageSending.writeString(token);
		messageSending.writeShort(tableId);
		messageSending.writeShort(subTableID);
		
		messageSending.writeShort(indexBegin);
		messageSending.writeShort(indexEnd);
		return onOnehitFull_AccountUserData(messageSending);
	}
}
