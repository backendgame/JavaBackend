package gameonline.rest.controller_user.Table.sub;

import java.util.HashMap;

import gameonline.rest.SystemConstant;
import gameonline.rest.database.model.SubTable_Type;
import richard.CMD_ONEHIT;
import richard.MessageReceiving;
import richard.MessageSending;

public class Service_SubTableScreen305_Create_Tile_Binary extends BaseService_CeateSubTable{
	@Override public MessageSending onCreateMessage(String _token) {	
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTable_Create_Tile_Binary);
		messageSending.writeString(_token);
		messageSending.writeshort(tableId);
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
