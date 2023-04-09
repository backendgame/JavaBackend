package gameonline.rest.controller_user.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.client.ClientOneHit;
import gameonline.config.CMD_ONEHIT;
import gameonline.config.CaseCheck;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;

public class Service_TableScreen158_Config_LoginRule extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull public boolean Device;
	@NotNull public boolean SystemAccount;
	@NotNull public boolean Facebook;
	@NotNull public boolean Google;
	@NotNull public boolean AdsId;
	@NotNull public boolean Apple;
	@NotNull public boolean EmailCode;
	
	@Override public MyRespone respone() {
		final MyRespone myRespone=new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {
				myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage("ErrorCode : "+errorCode);
			}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck==CaseCheck.HOPLE)
					myRespone.setStatus(MyRespone.STATUS_Success);
				else if(caseCheck==CaseCheck.TIMEOUT)
					myRespone.setStatus(MyRespone.STATUS_OutOfService).setMessage("Database chưa được thanh toán nên bị tạm ngưng");
				else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));
			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Table_Config_LoginRule);
				messageSending.writeString(token);
				messageSending.writeShort(tableId);
				
				messageSending.writeBoolean(Device);
				messageSending.writeBoolean(SystemAccount);
				messageSending.writeBoolean(Facebook);
				messageSending.writeBoolean(Google);
				messageSending.writeBoolean(AdsId);
				messageSending.writeBoolean(Apple);
				messageSending.writeBoolean(EmailCode);
				return messageSending;
			}
		}.run();
		return myRespone;
	}
	
}
