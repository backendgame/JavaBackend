package gameonline.rest.controller_user.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.dto.DatabaseInfoDTO;
import richard.CMD_ONEHIT;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public class Service_TableScreen120_GetInfo_Table extends BaseAuthorization{
	@NotNull @Positive public short tableId;
	@NotNull @Positive public short regionId;
	
	@Override public MyRespone respone() {
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck == CaseCheck.HOPLE) {
					DatabaseInfoDTO tableInfo = new DatabaseInfoDTO(messageReceiving);
					if (System.currentTimeMillis() > tableInfo.AvaiableTime)
						myRespone.setStatus(MyRespone.STATUS_Warning).setMessage("OutOfService : Database chưa được thanh toán nên bị tạm ngưng").setData(tableInfo);
					else
						myRespone.setStatus(MyRespone.STATUS_Success).setData(tableInfo);
				}else if(caseCheck==CaseCheck.TIMEOUT)
					myRespone.setStatus(MyRespone.STATUS_OutOfService).setMessage("Database chưa được thanh toán nên bị tạm ngưng");
				else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));
			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Table_Get_Info);
				messageSending.writeString(token);
				messageSending.writeshort(tableId);
				return messageSending;
			}
		}.run();
		return myRespone;
	}
}
