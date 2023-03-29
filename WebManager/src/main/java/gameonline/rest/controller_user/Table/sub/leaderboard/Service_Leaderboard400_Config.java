package gameonline.rest.controller_user.Table.sub.leaderboard;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import richard.CMD_ONEHIT;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public class Service_Leaderboard400_Config extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	@NotNull @PositiveOrZero public short subTableID;
	
	@NotNull @Positive public short NumberRow;
	@NotNull @Positive public byte DataType;
	@NotNull public String ColumnName;
	
	@Override protected MyRespone respone() {
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck == CaseCheck.HOPLE) {
					myRespone.setStatus(MyRespone.STATUS_Success);
				}else
					myRespone.setStatus(MyRespone.STATUS_InternalServerError).setMessage(CaseCheck.getString(caseCheck));
			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Leaderboard_Config);
				messageSending.writeString(token);
				messageSending.writeshort(tableId);
				messageSending.writeshort(subTableID);
				
				messageSending.writeshort(NumberRow);
				messageSending.writeByte(DataType);
				messageSending.writeString(ColumnName);
				
				return messageSending;
			}
		}.run();
		return myRespone;
	}
}
