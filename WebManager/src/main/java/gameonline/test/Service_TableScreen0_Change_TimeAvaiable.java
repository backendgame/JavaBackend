package gameonline.test;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import richard.CMD_ONEHIT;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public class Service_TableScreen0_Change_TimeAvaiable extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@Override public MyRespone respone() {
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				myRespone.setStatus(MyRespone.STATUS_Success).setMessage(CaseCheck.getString(messageReceiving.readByte()));
			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Test_Time_Avaiable);
				messageSending.writeString(token);
				messageSending.writeshort(tableId);
				return messageSending;
			}
		}.run();
		return myRespone;
	}
	

}
