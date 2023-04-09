package gameonline.rest.controller_user.LoginScreen;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.Expected;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import backendgame.com.core.TimeManager;
import dynamodb.TableDynamoDB_AccountLogin;
import dynamodb.TableDynamoDB_UserData;
import gameonline.rest.BaseVariable;
import gameonline.rest.BinaryToken;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;

public class Service_LoginScreen_Login_With_EmailPassword extends BaseVariable {
	@NotEmpty
	@Email(message = "Email invalid")
	public String email; 
	
    @NotEmpty(message = "Thiếu password")
    @Size(min = 6, message = "Password phải từ 8 kí tự trở lên")
	public String password;
	
	public MyRespone respone() {
		Item item = databaseAccount.getItem(email);
//		try {
////			item = databaseAccount.getItem(email);
//			item = databaseAccount.table.updateItem(new UpdateItemSpec()
//					.withPrimaryKey(TableDynamoDB_AccountLogin.HASH_KEY, email)
//					.withAttributeUpdate(new AttributeUpdate(TableDynamoDB_AccountLogin.ATTRIBUTE_ipAddress).put(getIp()))
//					.withExpected(new Expected(TableDynamoDB_AccountLogin.HASH_KEY).exists())
//					.withReturnValues(ReturnValue.ALL_NEW)
//					).getItem();
//		}catch (Exception e) {
//			return new MyRespone(MyRespone.STATUS_InternalServerError, "Email not found", Lib.getStringException(e));
//		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(item==null)
			return new MyRespone(MyRespone.STATUS_InternalServerError, "Email not found");
		
		if(item.hasAttribute(TableDynamoDB_AccountLogin.ATTRIBUTE_password)==false)
			return new MyRespone(MyRespone.STATUS_InternalServerError, "Account had been not verified yet");
		
		if(password.equals(item.get(TableDynamoDB_AccountLogin.ATTRIBUTE_password))==false)
			return new MyRespone(MyRespone.STATUS_Invalid, "Email or password is incorrect");
		
		if(item.hasAttribute(TableDynamoDB_AccountLogin.ATTRIBUTE_userId)==false)
			return new MyRespone(MyRespone.STATUS_InternalServerError, "UserId not Exist");
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		long userId = item.getLong(TableDynamoDB_AccountLogin.ATTRIBUTE_userId);
		
		try {
//			item = databaseUserData.getAllDataForHomeScreen(userId);
			item = databaseUserData.table.updateItem(new UpdateItemSpec()
					.withPrimaryKey(TableDynamoDB_UserData.HASH_KEY, userId)
					.withAttributeUpdate(new AttributeUpdate(TableDynamoDB_UserData.ATTRIBUTE_lastTimeLogin).put(System.currentTimeMillis()))
					.withExpected(new Expected(TableDynamoDB_UserData.HASH_KEY).exists())
					.withReturnValues(ReturnValue.ALL_NEW)
					).getItem();
		}catch (Exception e) {
			return new MyRespone(MyRespone.STATUS_InternalServerError, "UserId not found", getStringException(e));
		}
		
		if(item==null)
			return new MyRespone(MyRespone.STATUS_InternalServerError, "UserId not found");
		
		item.with(SystemConstant.TOKEN, new BinaryToken(userId, System.currentTimeMillis() + TimeManager.A_DAY_MILLISECOND).toHashString());
		
		try {
			return new MyRespone(MyRespone.STATUS_Success).setData(new ObjectMapper().readTree(item.toJSON()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new MyRespone(MyRespone.STATUS_InternalServerError, "Json error");
		}
	}

}
