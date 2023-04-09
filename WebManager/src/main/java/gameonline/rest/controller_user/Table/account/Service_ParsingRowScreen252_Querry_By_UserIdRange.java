package gameonline.rest.controller_user.Table.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import backendgame.com.core.MessageSending;
import gameonline.config.CMD_ONEHIT;
import gameonline.rest.MyRespone;

public class Service_ParsingRowScreen252_Querry_By_UserIdRange extends BaseService_ParsingRow{
	@NotNull @Positive public long userIdBegin;
	@NotNull @Positive public long userIdEnd;
	
	public MyRespone respone() {
		MessageSending messageSending=new MessageSending(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_UserIdRange);
		messageSending.writeString(token);
		messageSending.writeShort(tableId);
		messageSending.writeLong(userIdBegin);
		messageSending.writeLong(userIdEnd);
		return onOnehitFull_AccountUserData(messageSending);
	}
}
