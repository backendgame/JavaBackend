package gameonline.rest.controller_user.Table;

import java.util.HashMap;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.client.ClientOneHit;
import dynamodb.TableDynamoDB_UserData;
import gameonline.config.CMD_ONEHIT;
import gameonline.config.CaseCheck;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;
import gameonline.rest.database.model.DescribeTable;

/*
 {
  "description": "string",
  "regionId": 1,
  "tableName": "aaaaa",
  "tokenLifeTimeMili": 1,

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
  }
 * */
public class Service_TableScreen110_Create_Table extends BaseAuthorization{
	@NotEmpty public String tableName;
	@NotEmpty public String description;
	@NotNull @Positive public short regionId;
	@NotNull @PositiveOrZero public long tokenLifeTimeMili;//28800000 : 8 tiếng
	public DescribeTable[] describeTables;
	
	@Override public MyRespone respone() {
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck == CaseCheck.HOPLE) {
					final short tableId = messageReceiving.readShort();
					
					ObjectNode node = new ObjectMapper().createObjectNode();
					node.put(SystemConstant.TABLEID, tableId);
					node.put(SystemConstant.AvaiableTime, messageReceiving.readLong()+"");
					node.put(SystemConstant.AccessToken, messageReceiving.readLong()+"");
					node.put(SystemConstant.ReadToken, messageReceiving.readLong()+"");
					node.put(SystemConstant.WriteToken, messageReceiving.readLong()+"");
					
					threadPool.runThread(new Runnable() {
						@Override
						public void run() {
							HashMap<String, Object> mapTable = new HashMap<>();
							mapTable.put(SystemConstant.TableName, tableName);
							mapTable.put(SystemConstant.TableDescription, description);
							
							String TABLENODE = "R"+regionId+"T"+tableId;
							databaseUserData.table.updateItem(new UpdateItemSpec().withPrimaryKey(TableDynamoDB_UserData.HASH_KEY, customerUserId)
									.withUpdateExpression("SET "+TableDynamoDB_UserData.ATTRIBUTE_Tables+"."+TABLENODE+" = :mapTable")
									.withValueMap(new ValueMap().withMap(":mapTable", mapTable)));
						}
					});
						
					myRespone.setStatus(MyRespone.STATUS_Success).setData(node);
				}else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));
			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Table_Create_Table);
				messageSending.writeString(token);
				
				if(describeTables==null) {
					messageSending.writeShort((short) 0);
				}else {
					short numberDescribeTables = (short) describeTables.length;
					messageSending.writeShort(numberDescribeTables);
					for(short i=0;i<numberDescribeTables;i++) 
						describeTables[i].writeMessage(messageSending);
				}
				
				messageSending.writeLong(tokenLifeTimeMili);
				return messageSending;
			}
		}.run();
		return myRespone;
	}

}
