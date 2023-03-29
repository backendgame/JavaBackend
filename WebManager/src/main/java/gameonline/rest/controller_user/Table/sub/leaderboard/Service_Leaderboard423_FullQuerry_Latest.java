package gameonline.rest.controller_user.Table.sub.leaderboard;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.MyRespone;
import richard.CMD_ONEHIT;
import richard.MessageSending;

public class Service_Leaderboard423_FullQuerry_Latest extends BaseService_Leaderboard_Querry{
	@NotNull @Positive public short length;
	
	public MyRespone respone() {
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Leaderboard_FullQuerry_Latest);
		messageSending.writeString(token);
		messageSending.writeshort(tableId);
		messageSending.writeshort(subTableID);
		
		messageSending.writeshort(length);
		return onOnehitFull_AccountUserData(messageSending);
	}
}
