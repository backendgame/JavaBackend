package gameonline.rest.controller_user.HomeScreen;

import javax.validation.constraints.NotNull;

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;

import dynamodb.TableDynamoDB_UserData;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;

public class Service_HomeScreen_UpdateSetting extends BaseAuthorization {
	@NotNull public String jsonSetting;
	
	@Override public MyRespone respone() {
		databaseUserData.table.updateItem(new PrimaryKey(TableDynamoDB_UserData.HASH_KEY, customerUserId),new AttributeUpdate(TableDynamoDB_UserData.ATTRIBUTE_Setting).put(jsonSetting));
		return resSuccess;
	}

}
