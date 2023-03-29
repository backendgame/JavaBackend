package gameonline.rest.controller_client.login;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.MyRespone;
import gameonline.rest.database.model.DatabaseId;
import richard.CMD_ONEHIT;
import richard.MessageSending;

public class ServiceClient_Login4_AdsId extends BaseClient_Login {
	@NotNull @Positive public short tableId;
	@NotNull @Positive public short regionId;
	@NotNull public long AccessKey;
	@NotEmpty public String adsId; 
	
	public MyRespone respone() {
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBClient_Table_Login_Without_Password);
		messageSending.writeshort(tableId);
		messageSending.writeLong(AccessKey);
		messageSending.writeByte(DatabaseId.AdsId);
		messageSending.writeString(adsId);
		return processClientLogin(regionId, messageSending);
	}

}
