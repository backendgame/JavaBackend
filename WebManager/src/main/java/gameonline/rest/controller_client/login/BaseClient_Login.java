package gameonline.rest.controller_client.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.client.ClientOneHit;
import gameonline.config.CaseCheck;
import gameonline.rest.BaseVariable;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;

public class BaseClient_Login extends BaseVariable {

	
	protected MyRespone processClientLogin(short regionId,final MessageSending messageSending) {
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {
				myRespone.status=MyRespone.STATUS_DatabaseError;
			}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				ObjectNode node = new ObjectMapper().createObjectNode();
				switch (caseCheck) {
					case CaseCheck.NEW:
						node.put(SystemConstant.TOKEN, messageReceiving.readString());
						node.put(SystemConstant.USERID, messageReceiving.readLong());
						myRespone.status=MyRespone.STATUS_Success;
						myRespone.setData(node);
						break;
					case CaseCheck.EXIST:
						node.put(SystemConstant.TOKEN, messageReceiving.readString());
						node.put(SystemConstant.USERID, messageReceiving.readLong());
						node.put(SystemConstant.DATA, messageReceiving.readByteArray());
						myRespone.status=MyRespone.STATUS_Success;
						myRespone.setData(node);
						break;
					default:
						myRespone.status=MyRespone.STATUS_DatabaseError;
						myRespone.message = CaseCheck.getString(caseCheck);
						break;
				}
			}
			
			@Override public MessageSending doSendMessage() {
				return messageSending;
			}
		}.run();
		return myRespone;
	}
	
}
