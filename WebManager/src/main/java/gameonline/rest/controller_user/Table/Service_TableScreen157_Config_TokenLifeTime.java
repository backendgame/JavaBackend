package gameonline.rest.controller_user.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import richard.CMD_ONEHIT;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public class Service_TableScreen157_Config_TokenLifeTime extends BaseAuthorization{
	@NotNull public long tokenLifeTime;
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	
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
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Table_Config_TokenLifeTime);
				messageSending.writeString(token);
				messageSending.writeshort(tableId);
				messageSending.writeLong(tokenLifeTime);
				return messageSending;
			}
		}.run();
		return myRespone;
	}
}
