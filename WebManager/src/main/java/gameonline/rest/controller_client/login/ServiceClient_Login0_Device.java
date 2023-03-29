package gameonline.rest.controller_client.login;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.MyRespone;
import gameonline.rest.database.model.DatabaseId;
import richard.CMD_ONEHIT;
import richard.MessageSending;

public class ServiceClient_Login0_Device extends BaseClient_Login {
	@NotNull @Positive public short tableId;
	@NotNull @Positive public short regionId;
	@NotNull public long AccessKey;
	@NotEmpty public String deviceId; 
	
	public MyRespone respone() {
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBClient_Table_Login_Without_Password);
		messageSending.writeshort(tableId);
		messageSending.writeLong(AccessKey);
		messageSending.writeByte(DatabaseId.Device);
		messageSending.writeString(deviceId);
		return processClientLogin(regionId, messageSending);
	}

}
