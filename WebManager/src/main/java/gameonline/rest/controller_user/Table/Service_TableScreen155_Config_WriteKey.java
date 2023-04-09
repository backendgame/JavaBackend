package gameonline.rest.controller_user.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.client.ClientOneHit;
import gameonline.config.CMD_ONEHIT;
import gameonline.config.CaseCheck;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;

public class Service_TableScreen155_Config_WriteKey extends BaseAuthorization{
	@NotNull public String writeToken;
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
				if(caseCheck == CaseCheck.HOPLE) {
					long writeKey = messageReceiving.readLong();
//					System.out.println("accessKey : "+writeKey);
					
					ObjectNode node = new ObjectMapper().createObjectNode();
//					node.put(SystemConstant.WriteToken, binaryToken.longToToken(writeKey));
					node.put(SystemConstant.WriteToken, writeKey+"");
					myRespone.setStatus(MyRespone.STATUS_Success).setData(node);
				}else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));
			}
			
			@Override public MessageSending doSendMessage() {
//				long tmp = binaryToken.tokenToLong(writeToken);
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Table_Config_WriteKey);
				messageSending.writeString(token);
				messageSending.writeShort(tableId);
				messageSending.writeLong(Long.parseLong(writeToken));
				return messageSending;
			}
		}.run();
		return myRespone;
	}
}
