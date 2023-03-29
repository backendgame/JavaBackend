package gameonline.rest.controller_user.Table.sub;

import java.util.HashMap;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import dynamodb.TableDynamoDB_UserData;
import gameonline.rest.BaseVariable;
import gameonline.rest.BinaryToken;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public abstract class BaseService_CeateSubTable extends BaseVariable{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	@NotNull public String SubName;
	@NotNull public String Description;
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public abstract MessageSending onCreateMessage(String _token);
	public abstract HashMap<String, Object> onUpdateDynamoDB(MessageReceiving messageReceiving);
	
	private void updateDatabase(HashMap<String, Object> map, long customerUserId, short subTableId) {
		String TABLENODE = "R"+regionId+"T"+tableId;
		databaseUserData.table.updateItem(new UpdateItemSpec().withPrimaryKey(TableDynamoDB_UserData.HASH_KEY, customerUserId)
			.withUpdateExpression("SET #col.#tbNode.#subTable = :t")
			.withNameMap(new NameMap()
				.with("#col", TableDynamoDB_UserData.ATTRIBUTE_Tables)
				.with("#tbNode", TABLENODE)
				.with("#subTable", subTableId+"")
			).withValueMap(new ValueMap().withMap(":t", map))
		);
	}

	public MyRespone process(String _token) {
		BinaryToken binaryToken = new BinaryToken();
		if(binaryToken.decode(_token)==false)
			return resTokenError;
		if(System.currentTimeMillis()>binaryToken.timeOut)
			return resTokenTimeout;
		
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck == CaseCheck.HOPLE) {
					short subTableId = messageReceiving.readShort();
					HashMap<String, Object> map = onUpdateDynamoDB(messageReceiving);
					updateDatabase(map, binaryToken.userId, subTableId);
					map.put(SystemConstant.SUB_TABLEID, subTableId);
					myRespone.setStatus(MyRespone.STATUS_Success).setData(map);
				}else if(caseCheck==CaseCheck.TIMEOUT)
					myRespone.setStatus(MyRespone.STATUS_OutOfService).setMessage("Database chưa được thanh toán nên bị tạm ngưng");
				else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));
			}
			
			@Override public MessageSending doSendMessage() {
//				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTable_Create_Leaderboard);
//				messageSending.writeString(_token);
//				messageSending.writeshort(tableId);
//				messageSending.writeshort(NumberTop);
//				return messageSending;
				return onCreateMessage(_token);
			}
		}.run();
		return myRespone;
	}
	
}
