package gameonline.rest.controller_user.Table.sub;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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

public class Service_SubTableScreen350_Config_AccessKey extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	@NotNull @PositiveOrZero public short subTableID;
	
	@NotNull public String accessToken;
	
	@Override protected MyRespone respone() {
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck == CaseCheck.HOPLE) {
					long accessKey = messageReceiving.readLong();
//					System.out.println("accessKey : "+accessKey);
					
					ObjectNode node = new ObjectMapper().createObjectNode();
//					node.put(SystemConstant.AccessToken, binaryToken.longToToken(accessKey));
					node.put(SystemConstant.AccessToken, accessKey+"");
					myRespone.setStatus(MyRespone.STATUS_Success).setData(node);
				}else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));
			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTable_Config_AccessKey);
				messageSending.writeString(token);
				messageSending.writeshort(tableId);
				messageSending.writeshort(subTableID);
				
				messageSending.writeLong(Long.parseLong(accessToken));
				
				return messageSending;
			}
		}.run();
		return myRespone;
	}
}
