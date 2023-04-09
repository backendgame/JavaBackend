package gameonline.rest.controller_user.Table.sub;

import java.util.HashMap;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import gameonline.config.CMD_ONEHIT;
import gameonline.rest.SystemConstant;
import gameonline.rest.database.model.SubTable_Type;

public class Service_SubTableScreen305_Create_Tile_Binary extends BaseService_CeateSubTable{
	@Override public MessageSending onCreateMessage(String _token) {	
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTable_Create_Tile_Binary);
		messageSending.writeString(_token);
		messageSending.writeShort(tableId);
		return messageSending;
	}

	@Override public HashMap<String, Object> onUpdateDynamoDB(MessageReceiving messageReceiving) {
		HashMap<String, Object> map = new HashMap<>();
		map.put(SystemConstant.TableName, SubName);
		map.put(SystemConstant.TableDescription, Description);
		map.put(SystemConstant.TableType, SubTable_Type.SubType_Tile_Binary);
		return map;
	}
	
	
	
	

}
