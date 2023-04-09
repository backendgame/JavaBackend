package gameonline.rest.controller_user.Table.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import backendgame.com.core.MessageSending;
import gameonline.config.CMD_ONEHIT;
import gameonline.rest.MyRespone;

public class Service_ParsingRowScreen253_Querry_By_LatestCreate extends BaseService_ParsingRow{
	@NotNull @Positive public short length;
	
	public MyRespone respone() {
		MessageSending messageSending=new MessageSending(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_LatestCreate);
		messageSending.writeString(token);
		messageSending.writeShort(tableId);
		messageSending.writeShort(length);
		return onOnehitFull_AccountUserData(messageSending);
	}
}
