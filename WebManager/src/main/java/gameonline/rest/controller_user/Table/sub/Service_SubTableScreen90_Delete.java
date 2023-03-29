package gameonline.rest.controller_user.Table.sub;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;

import dynamodb.TableDynamoDB_UserData;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import richard.CMD_ONEHIT;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public class Service_SubTableScreen90_Delete extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull @Positive public short subTableID;
	
	@Override public MyRespone respone() {
		
		databaseUserData.table.updateItem(new UpdateItemSpec().withPrimaryKey(TableDynamoDB_UserData.HASH_KEY, customerUserId)
				.withUpdateExpression("REMOVE #col.#tbNode.#subTable").withNameMap(new NameMap()
					.with("#col", TableDynamoDB_UserData.ATTRIBUTE_Tables)
					.with("#tbNode", "R"+regionId+"T"+tableId)
					.with("#subTable", subTableID+"")
				));
		
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck == CaseCheck.HOPLE) {
					
					short leaderboardId = messageReceiving.readShort();
					System.err.println("Tạo thành công leaderboardId("+leaderboardId+")");
					
				}else if(caseCheck==CaseCheck.TIMEOUT)
					myRespone.setStatus(MyRespone.STATUS_OutOfService).setMessage("Database chưa được thanh toán nên bị tạm ngưng");
				else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));

			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTable_Delete);
				messageSending.writeString(token);
				messageSending.writeshort(tableId);
				messageSending.writeshort(subTableID);
				return messageSending;
			}
		}.run();
		return myRespone;
	}

}
