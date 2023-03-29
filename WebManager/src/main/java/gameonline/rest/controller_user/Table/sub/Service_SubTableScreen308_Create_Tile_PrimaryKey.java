package gameonline.rest.controller_user.Table.sub;

import java.util.HashMap;

import gameonline.rest.SystemConstant;
import gameonline.rest.database.model.DescribeTable;
import gameonline.rest.database.model.SubTable_Type;
import richard.CMD_ONEHIT;
import richard.MessageReceiving;
import richard.MessageSending;

public class Service_SubTableScreen308_Create_Tile_PrimaryKey extends BaseService_CeateSubTable{
	public DescribeTable primaryKey;
	public DescribeTable[] describeTables;
	
	@Override public MessageSending onCreateMessage(String _token) {	
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTable_Create_Tile_PrimaryKey);
		messageSending.writeString(_token);
		messageSending.writeshort(tableId);
		
		if(describeTables==null) {
			messageSending.writeshort((short) 0);
		}else {
			short numberDescribeTables = (short) describeTables.length;
			messageSending.writeshort(numberDescribeTables);
			for(short i=0;i<numberDescribeTables;i++) 
				describeTables[i].writeMessage(messageSending);
		}
		
		return messageSending;
	}

	@Override public HashMap<String, Object> onUpdateDynamoDB(MessageReceiving messageReceiving) {
		HashMap<String, Object> map = new HashMap<>();
		map.put(SystemConstant.TableName, SubName);
		map.put(SystemConstant.TableDescription, Description);
		map.put(SystemConstant.TableType, SubTable_Type.SubType_Tile_Row);
		return map;
	}
	
	
	
	

}
