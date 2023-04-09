package gameonline.rest.controller_user.LoginScreen;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;

import backendgame.com.core.TimeManager;
import dynamodb.TableDynamoDB_AccountLogin;
import gameonline.config.Config;
import gameonline.rest.BaseVariable;
import gameonline.rest.MyRespone;

public class Service_LoginScreen20_ForgotPassword_Request extends BaseVariable {
	@NotEmpty
	@Email(message = "Email invalid")
	public String email;
	
	public MyRespone respone() {
		Item item = databaseAccount.getItem(email);
		
		if(item==null)
			return new MyRespone(MyRespone.STATUS_Invalid, "Email not found");
		
		if(item.hasAttribute(TableDynamoDB_AccountLogin.ATTRIBUTE_Timeout_ForgotPassword) && item.getLong(TableDynamoDB_AccountLogin.ATTRIBUTE_Timeout_ForgotPassword) > System.currentTimeMillis())
			return new MyRespone(MyRespone.STATUS_TokenTimeout, "Email had been send!!!");
		
		long timeoutUpdate = System.currentTimeMillis()+TimeManager.A_HOUR_MILLISECOND;
		
		System.out.println("---->"+timeoutUpdate);
		
		databaseAccount.table.updateItem(new UpdateItemSpec().withPrimaryKey(TableDynamoDB_AccountLogin.HASH_KEY, email).withAttributeUpdate(new AttributeUpdate(TableDynamoDB_AccountLogin.ATTRIBUTE_Timeout_ForgotPassword).put(timeoutUpdate)));
		threadPool.runThread(new Runnable() {
			public void run() {
				Config.sendEmail(Config.Gmail_Send, Config.Gmail_AppPassword, email, Config.Domain + " - Password Recovery", "Password reset link : "+timeoutUpdate);
			}
		});
		
		return new MyRespone(MyRespone.STATUS_Success);
	}

}
