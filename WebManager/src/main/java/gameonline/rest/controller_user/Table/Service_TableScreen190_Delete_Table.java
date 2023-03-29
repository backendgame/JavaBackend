package gameonline.rest.controller_user.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;

import dynamodb.TableDynamoDB_UserData;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import richard.CMD_ONEHIT;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public class Service_TableScreen190_Delete_Table extends BaseAuthorization{
	@NotNull @Positive public short tableId;
	@NotNull @Positive public short regionId;
	
	@Override public MyRespone respone() {
		databaseUserData.table.updateItem(new UpdateItemSpec().withPrimaryKey(TableDynamoDB_UserData.HASH_KEY, 1661057845615l).withUpdateExpression("REMOVE "+TableDynamoDB_UserData.ATTRIBUTE_Tables+"."+"R"+regionId+"T"+tableId));
		
		new ClientOneHit(regionId) {@Override public void onReceiveMessage(MessageReceiving messageReceiving) {}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Table_Delete);
				messageSending.writeString(token);
				messageSending.writeshort(tableId);
				return messageSending;
			}
		}.runThread();
		return resSuccess;
	}
}
