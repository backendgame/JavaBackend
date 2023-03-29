package gameonline.rest.controller_user.Table.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.dto.ParsingRow_QuerryAccount_DTO;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public abstract class BaseService_ParsingRow extends BaseAuthorization {
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	public MyRespone onOnehitFull_AccountUserData(final MessageSending messageSending) {
		final MyRespone myRespone=new MyRespone();
		new ClientOneHit(regionId) {
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck==CaseCheck.HOPLE) {
					myRespone.setStatus(MyRespone.STATUS_Success).setData(new ParsingRow_QuerryAccount_DTO(messageReceiving));
				}else if(caseCheck==CaseCheck.TIMEOUT)
					myRespone.setStatus(MyRespone.STATUS_OutOfService).setMessage("Database chưa được thanh toán nên bị tạm ngưng");
				else
					myRespone.setStatus(MyRespone.STATUS_BadRequest).setMessage(CaseCheck.getString(caseCheck));
			}
			
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage("ErrorCode : "+errorCode);}
			
			@Override public MessageSending doSendMessage() {return messageSending;}
		}.run();
		return myRespone;
	}

}
