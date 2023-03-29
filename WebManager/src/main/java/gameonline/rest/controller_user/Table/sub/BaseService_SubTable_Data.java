package gameonline.rest.controller_user.Table.sub;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public abstract class BaseService_SubTable_Data extends BaseAuthorization {
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull @Positive public short subTableID;
	
	public abstract MessageSending onMessageData();
	
	@Override
	protected MyRespone respone() {
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
//				byte caseCheck = messageReceiving.readByte();
//				if(caseCheck == CaseCheck.HOPLE) {
//					
//					short leaderboardId = messageReceiving.readShort();
//					System.err.println("Tạo thành công leaderboardId("+leaderboardId+")");
//					
//				}else if(caseCheck==CaseCheck.TIMEOUT)
//					myRespone.setStatus(MyRespone.STATUS_OutOfService).setMessage("Database chưa được thanh toán nên bị tạm ngưng");
//				else
//					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));

			}
			
			@Override public MessageSending doSendMessage() {
				return onMessageData();
			}
		}.run();
		return myRespone;
	}

}
