package gameonline.rest.controller_user.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;
import richard.CMD_ONEHIT;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public class Service_TableScreen153_Config_ReadKey extends BaseAuthorization{
	@NotNull public String readToken;
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
					long readKey = messageReceiving.readLong();
//					System.out.println("accessKey : "+readKey);
					
					ObjectNode node = new ObjectMapper().createObjectNode();
//					node.put(SystemConstant.ReadToken, binaryToken.longToToken(readKey));
					node.put(SystemConstant.ReadToken, readKey+"");
					myRespone.setStatus(MyRespone.STATUS_Success).setData(node);
				}else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));
			}
			
			@Override public MessageSending doSendMessage() {
//				long tmp = binaryToken.tokenToLong(readToken);
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Table_Config_ReadKey);
				messageSending.writeString(token);
				messageSending.writeshort(tableId);
				messageSending.writeLong(Long.parseLong(readToken));
				return messageSending;
			}
		}.run();
		return myRespone;
	}
}
