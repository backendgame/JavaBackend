package gameonline.rest.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import backendgame.com.core.MessageReceiving;
import gameonline.rest.SystemConstant;
import gameonline.rest.database.model.DatabaseId;

public class AccountDTO {
	public byte databaseId;
	public String credential;
	public long passwordLocation;
	public long timeCreate;
	public long userId;
	
	
	public AccountDTO() {

	}
	
	public void read(MessageReceiving messageReceiving) {
		databaseId=messageReceiving.readByte();
		credential=messageReceiving.readString();
		if(databaseId==DatabaseId.SystemAccount || databaseId==DatabaseId.EmailCode)
			passwordLocation=messageReceiving.readLong();
		timeCreate=messageReceiving.readLong();
		userId=messageReceiving.readLong();
	}
	
	public ObjectNode toObjectNode() {
		ObjectNode node = new ObjectMapper().createObjectNode();
		node.put(SystemConstant.DATABASEID, databaseId);
		node.put(SystemConstant.CREDENTIAL, credential);
		node.put(SystemConstant.USERID, userId);
		node.put(SystemConstant.TIMECREATE, new Date(timeCreate).toString());
		return node;
	}
	
	public void toJson(ObjectNode node) {
		node.put(SystemConstant.DATABASEID, databaseId);
		node.put(SystemConstant.CREDENTIAL, credential);
		node.put(SystemConstant.USERID, userId);
		node.put(SystemConstant.TIMECREATE, new Date(timeCreate).toString());
	}
	
	@Override public String toString() {
		switch (databaseId) {
			case DatabaseId.Device:return "Device("+userId+") passwordLocation("+passwordLocation+") "+credential+"		|||→ at "+new Date(timeCreate).toString();
			case DatabaseId.SystemAccount:return "System("+userId+") passwordLocation("+passwordLocation+") "+credential+"		|||→ at "+new Date(timeCreate).toString();
			case DatabaseId.Facebook:return "Facebook("+userId+") passwordLocation("+passwordLocation+") "+credential+"		|||→ at "+new Date(timeCreate).toString();
			case DatabaseId.Google:return "Google("+userId+") passwordLocation("+passwordLocation+") "+credential+"		|||→ at "+new Date(timeCreate).toString();
			case DatabaseId.AdsId:return "AdsId("+userId+") passwordLocation("+passwordLocation+") "+credential+"		|||→ at "+new Date(timeCreate).toString();
			case DatabaseId.Apple:return "Apple("+userId+") passwordLocation("+passwordLocation+") "+credential+"		|||→ at "+new Date(timeCreate).toString();
			case DatabaseId.EmailCode:return "EmailCode("+userId+") passwordLocation("+passwordLocation+") "+credential+"		|||→ at "+new Date(timeCreate).toString();
			default:return "null";
		}
	}
}
