package gameonline.rest.controller_user.Table.sub;

import java.util.HashMap;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import gameonline.config.CMD_ONEHIT;
import gameonline.rest.SystemConstant;
import gameonline.rest.database.model.DescribeTable;
import gameonline.rest.database.model.SubTable_Type;

/*
  "describeTables": [
      {
        "FieldName": "e2dd8eb7-f",
        "Type": 13,
        "Size": 1,
        "DefaultValue": 51
      },
      {
        "FieldName": "f5860ede-7",
        "Type": 84,
        "Size": 8,
        "DefaultValue": 1666263491857
      },
      {
        "FieldName": "7678833a-c",
        "Type": 1,
        "Size": 1,
        "DefaultValue": true
      },
      {
        "FieldName": "21129ee3-4",
        "Type": 11,
        "Size": 1,
        "DefaultValue": -124
      },
      {
        "FieldName": "1d9635b2-e",
        "Type": 1,
        "Size": 1,
        "DefaultValue": true
      }
    ]
 * */

public class Service_SubTableScreen309_Create_TileRow_Without_PrimaryKey extends BaseService_CeateSubTable{
	public DescribeTable[] describeTables;

	@Override public MessageSending onCreateMessage(String _token) {	
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTable_Create_TileRow_Without_PrimaryKey);
		messageSending.writeString(_token);
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

	@Override public HashMap<String, Object> onUpdateDynamoDB(MessageReceiving messageReceiving) {
		HashMap<String, Object> map = new HashMap<>();
		map.put(SystemConstant.TableName, SubName);
		map.put(SystemConstant.TableDescription, Description);
		map.put(SystemConstant.TableType, SubTable_Type.SubType_SubUserData);
		return map;
	}
	
	
	
	

}
