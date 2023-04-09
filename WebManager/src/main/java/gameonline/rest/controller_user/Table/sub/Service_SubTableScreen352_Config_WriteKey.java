package gameonline.rest.controller_user.Table.sub;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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

public class Service_SubTableScreen352_Config_WriteKey extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	@NotNull @PositiveOrZero public short subTableID;
	
	@NotNull public String writeKey;
	
	@Override protected MyRespone respone() {
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck == CaseCheck.HOPLE) {
					long writeKey = messageReceiving.readLong();
//					System.out.println("accessKey : "+accessKey);
					
					ObjectNode node = new ObjectMapper().createObjectNode();
//					node.put(SystemConstant.AccessToken, binaryToken.longToToken(accessKey));
					node.put(SystemConstant.AccessToken, writeKey+"");
					myRespone.setStatus(MyRespone.STATUS_Success).setData(node);
				}else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));
			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTable_Config_WriteKey);
				messageSending.writeString(token);
				messageSending.writeShort(tableId);
				messageSending.writeShort(subTableID);
				
				messageSending.writeLong(Long.parseLong(writeKey));
				
				return messageSending;
			}
		}.run();
		return myRespone;
	}
}
