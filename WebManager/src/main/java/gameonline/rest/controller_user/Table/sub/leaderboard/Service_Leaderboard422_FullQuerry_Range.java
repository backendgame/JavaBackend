package gameonline.rest.controller_user.Table.sub.leaderboard;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import gameonline.rest.MyRespone;
import richard.CMD_ONEHIT;
import richard.MessageSending;

public class Service_Leaderboard422_FullQuerry_Range extends BaseService_Leaderboard_Querry{
	@NotNull @PositiveOrZero public short indexBegin;
	@NotNull @PositiveOrZero public short indexEnd;
	
	public MyRespone respone() {
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Leaderboard_FullQuerry_Range);
		messageSending.writeString(token);
		messageSending.writeshort(tableId);
		messageSending.writeshort(subTableID);
		
		messageSending.writeshort(indexBegin);
		messageSending.writeshort(indexEnd);
		return onOnehitFull_AccountUserData(messageSending);
	}
}
