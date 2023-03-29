package gameonline.rest.controller_user.Table.sub.leaderboard;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.dto.Leaderboard_FulQuerryDTO;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public abstract class BaseService_Leaderboard_Querry extends BaseAuthorization {
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull @PositiveOrZero public short subTableID;

	public MyRespone onOnehitFull_AccountUserData(final MessageSending messageSending) {
		final MyRespone myRespone=new MyRespone();

		new ClientOneHit(regionId) {
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck==CaseCheck.HOPLE) {
					myRespone.setStatus(MyRespone.STATUS_Success).setData(new Leaderboard_FulQuerryDTO(messageReceiving));
				}else if(caseCheck==CaseCheck.TIMEOUT)
					myRespone.setStatus(MyRespone.STATUS_OutOfService).setMessage("Database chưa được thanh toán nên bị tạm ngưng");
				else
					myRespone.setStatus(MyRespone.STATUS_BadRequest).setMessage(CaseCheck.getString(caseCheck));
			}
			
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseConnectionError).setMessage("ErrorCode : "+errorCode);}
			
			@Override public MessageSending doSendMessage() {return messageSending;}
		}.run();
		return myRespone;
	}

}
