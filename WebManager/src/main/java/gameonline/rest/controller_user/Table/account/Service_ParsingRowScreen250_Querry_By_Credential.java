package gameonline.rest.controller_user.Table.account;

import javax.validation.constraints.NotEmpty;

import backendgame.com.core.MessageSending;
import gameonline.config.CMD_ONEHIT;
import gameonline.rest.MyRespone;

public class Service_ParsingRowScreen250_Querry_By_Credential extends BaseService_ParsingRow{
	@NotEmpty public String Credential;
	
	public MyRespone respone() {
		MessageSending messageSending=new MessageSending(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_Credential);
		messageSending.writeString(token);
		messageSending.writeShort(tableId);
		messageSending.writeString(Credential);
		return onOnehitFull_AccountUserData(messageSending);
	}
}
