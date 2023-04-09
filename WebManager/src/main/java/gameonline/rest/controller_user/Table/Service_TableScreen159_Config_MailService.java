package gameonline.rest.controller_user.Table;

import java.util.HashMap;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.client.ClientOneHit;
import gameonline.config.CMD_ONEHIT;
import gameonline.config.CaseCheck;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;

public class Service_TableScreen159_Config_MailService extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	public HashMap<String, String> listEmail;
	
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
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Table_Config_MailService);
				messageSending.writeString(token);
				messageSending.writeShort(tableId);
				
				Set<String> listKey = listEmail.keySet();
				messageSending.writeByte((byte) listKey.size());
				for(String key:listKey) {
					messageSending.writeString(key);
					messageSending.writeString(listEmail.get(key));
				}
				return messageSending;
			}
		}.run();
		return myRespone;
	}
	
}
