package gameonline.rest.controller_user.Table.sub;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import dynamodb.TableDynamoDB_UserData;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;

public class Service_SubTableScreen_Rename extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull @Positive public short subTableID;
	
	@NotNull public String TableName;
	@NotNull public String Description;
	
	@Override public MyRespone respone() {
		String TABLENODE = "R"+regionId+"T"+tableId;
		try {
			databaseUserData.table.updateItem(new UpdateItemSpec().withPrimaryKey(TableDynamoDB_UserData.HASH_KEY, binaryToken.userId)
					.withUpdateExpression("SET #col.#tbNode.#st["+subTableID+"].#name = :name, #col.#tbNode.#st["+subTableID+"].#des = :des")
					.withNameMap(new NameMap()
							.with("#col", TableDynamoDB_UserData.ATTRIBUTE_Tables)
							.with("#tbNode", TABLENODE)
							.with("#st", TableDynamoDB_UserData.TABLE_SubTables)
							.with("#name", SystemConstant.TableName)
							.with("#des", SystemConstant.TableDescription)
					).withValueMap(new ValueMap()
							.withString(":name", TableName)
							.withString(":des", Description)
					));
			return resSuccess;
		}catch (Exception e) {
			return new MyRespone(MyRespone.STATUS_Exception, getStringException(e));
		}
	}
}
