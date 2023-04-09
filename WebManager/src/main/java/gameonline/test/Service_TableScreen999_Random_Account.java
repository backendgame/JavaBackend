package gameonline.test;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.client.ClientOneHit;
import gameonline.config.CMD_ONEHIT;
import gameonline.config.CaseCheck;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;

public class Service_TableScreen999_Random_Account extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;

	
	
	@NotNull @PositiveOrZero public short numberAccount;
	@NotNull public String passwordDefault;
	@NotNull public boolean Device;
	@NotNull public boolean SystemAccount;
	@NotNull public boolean Facebook;
	@NotNull public boolean Google;
	@NotNull public boolean AdsId;
	@NotNull public boolean Apple;
	@NotNull public boolean EmailCode;
	
	public MyRespone respone() {
		final MyRespone myRespone=new MyRespone();

		new ClientOneHit(regionId) {
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				if(messageReceiving.discoveryByte()==CaseCheck.HOPLE)
					myRespone.setStatus(MyRespone.STATUS_Success);
				else
					myRespone.setStatus(MyRespone.STATUS_Invalid);
				myRespone.setMessage(CaseCheck.getString(messageReceiving.readByte()));
			}
			
			public void onNetworkError(byte errorCode) {
				myRespone.status = MyRespone.STATUS_BadRequest;
				myRespone.message = "ErrorCode : "+errorCode;
			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Row_Random_Account);
				messageSending.writeString(token);
				messageSending.writeShort(tableId);
				
				messageSending.writeShort(numberAccount);
				messageSending.writeBoolean(Device);
				messageSending.writeBoolean(SystemAccount);
				messageSending.writeBoolean(Facebook);
				messageSending.writeBoolean(Google);
				messageSending.writeBoolean(AdsId);
				messageSending.writeBoolean(Apple);
				messageSending.writeBoolean(EmailCode);
				messageSending.writeString(passwordDefault);
				return messageSending;
			}
		}.run();
		return myRespone;
	}
}
