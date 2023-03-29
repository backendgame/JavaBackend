package gameonline.rest.controller_user.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import dynamodb.TableDynamoDB_UserData;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;

public class Service_TableScreen_Change_TableName extends BaseAuthorization {
	@NotNull public String tableName;
	@NotNull public String description;
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@Override
	public MyRespone respone() {
//		databaseUserData.updateTableDatabase(binaryToken.userId, regionId, tableId, tableName);
//		System.out.println("===>"+StringUtils.isNullOrEmpty(tableName));
		
		String preName = TableDynamoDB_UserData.ATTRIBUTE_Tables+"."+"R"+regionId+"T"+tableId+".";
		databaseUserData.table.updateItem(new UpdateItemSpec().withPrimaryKey(TableDynamoDB_UserData.HASH_KEY, customerUserId)
				.withUpdateExpression("SET " + preName + SystemConstant.TableName + " = :tableName, " + preName + SystemConstant.TableDescription + " = :tableDescription")
				.withValueMap(new ValueMap()
						.withString(":tableName", tableName)
						.withString(":tableDescription", description)));
		
		return resSuccess;
	}
}
