package gameonline.rest.controller_user.Table.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.MyRespone;
import richard.CMD_ONEHIT;
import richard.MessageSending;

public class Service_ParsingRowScreen253_Querry_By_LatestCreate extends BaseService_ParsingRow{
	@NotNull @Positive public short length;
	
	public MyRespone respone() {
		MessageSending messageSending=new MessageSending(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_LatestCreate);
		messageSending.writeString(token);
		messageSending.writeshort(tableId);
		messageSending.writeshort(length);
		return onOnehitFull_AccountUserData(messageSending);
	}
}
