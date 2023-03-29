package gameonline.rest.controller_user.Table.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import richard.CMD_ONEHIT;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public class Service_TableRowScreen290_Update_AccountStatus extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;

	@NotNull @Positive public long userId;
	@NotNull public byte status;
	
	public MyRespone respone() {
		final MyRespone myRespone=new MyRespone();
		new ClientOneHit(regionId) {
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck==CaseCheck.HOPLE)
					myRespone.setStatus(MyRespone.STATUS_Success);
				else
					myRespone.setStatus(MyRespone.STATUS_InternalServerError).setMessage(CaseCheck.getString(caseCheck));
			}
			
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage("ErrorCode : "+errorCode);}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Row_Update_AccountStatus);
				messageSending.writeString(token);
				messageSending.writeshort(tableId);
				
				messageSending.writeLong(userId);
				messageSending.writeByte(status);
				return messageSending;
			}
		}.run();
		return myRespone;
	}
}
