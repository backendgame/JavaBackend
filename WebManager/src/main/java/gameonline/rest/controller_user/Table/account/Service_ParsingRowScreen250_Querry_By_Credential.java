package gameonline.rest.controller_user.Table.account;

import javax.validation.constraints.NotEmpty;

import gameonline.rest.MyRespone;
import richard.CMD_ONEHIT;
import richard.MessageSending;

public class Service_ParsingRowScreen250_Querry_By_Credential extends BaseService_ParsingRow{
	@NotEmpty public String Credential;
	
	public MyRespone respone() {
		MessageSending messageSending=new MessageSending(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_Credential);
		messageSending.writeString(token);
		messageSending.writeshort(tableId);
		messageSending.writeString(Credential);
		return onOnehitFull_AccountUserData(messageSending);
	}
}
