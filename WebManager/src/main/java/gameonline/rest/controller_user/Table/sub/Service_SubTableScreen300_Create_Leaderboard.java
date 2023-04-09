package gameonline.rest.controller_user.Table.sub;

import java.util.HashMap;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import gameonline.config.CMD_ONEHIT;
import gameonline.rest.SystemConstant;
import gameonline.rest.database.model.SubTable_Type;

public class Service_SubTableScreen300_Create_Leaderboard extends BaseService_CeateSubTable{
	@NotNull @Positive public short NumberTop;
	@NotNull @Positive public byte DataType;
	@NotNull public String ColumnName;

	@Override public MessageSending onCreateMessage(String _token) {	
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTable_Create_Leaderboard);
		messageSending.writeString(_token);
		messageSending.writeShort(tableId);
		messageSending.writeShort(NumberTop);
		messageSending.writeByte(DataType);
		messageSending.writeString(ColumnName);
		return messageSending;
	}

	@Override public HashMap<String, Object> onUpdateDynamoDB(MessageReceiving messageReceiving) {
		HashMap<String, Object> map = new HashMap<>();
		map.put(SystemConstant.TableName, SubName);
		map.put(SystemConstant.TableDescription, Description);
		map.put(SystemConstant.NumberRow, NumberTop);
		map.put(SystemConstant.TableType, SubTable_Type.SubType_Leaderboard);
		map.put(SystemConstant.DATA_TYPE, DataType);
		return map;
	}
	
	
	
	

}
