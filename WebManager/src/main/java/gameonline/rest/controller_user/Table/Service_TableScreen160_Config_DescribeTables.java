package gameonline.rest.controller_user.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.client.ClientOneHit;
import gameonline.config.CMD_ONEHIT;
import gameonline.config.CaseCheck;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.database.model.DescribeTable;

public class Service_TableScreen160_Config_DescribeTables extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	public DescribeTable[] describeTables;
	
	@Override public MyRespone respone() {
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		final MyRespone myRespone=new MyRespone();
		new ClientOneHit(regionId) {public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseConnectionError).setMessage("ErrorCode : "+errorCode);}
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Table_Config_DescribeTables);
				messageSending.writeString(token);
				messageSending.writeShort(tableId);
				
				if(describeTables==null) {
					messageSending.writeShort((short) 0);
				}else {
					short numberDescribeTables = (short) describeTables.length;
					messageSending.writeShort(numberDescribeTables);
					for(short i=0;i<numberDescribeTables;i++) 
						describeTables[i].writeMessage(messageSending);
				}

				return messageSending;
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
		}.run();
		return myRespone;
	}
}
