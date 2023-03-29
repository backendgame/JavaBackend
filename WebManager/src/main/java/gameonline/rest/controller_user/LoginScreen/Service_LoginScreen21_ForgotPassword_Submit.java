package gameonline.rest.controller_user.LoginScreen;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.amazonaws.services.dynamodbv2.document.Item;

import dynamodb.TableDynamoDB_AccountLogin;
import gameonline.rest.BaseVariable;
import gameonline.rest.MyRespone;

public class Service_LoginScreen21_ForgotPassword_Submit extends BaseVariable {
	@NotEmpty
	@Email(message = "Email invalid")
	public String email;
	
    @Size(min = 6, message = "Password phải từ 8 kí tự trở lên")
    @NotEmpty public String password;
	
	@NotNull
	@Positive
	public long timeoutUpdate;
	
	public MyRespone respone() {
		Item item = databaseAccount.getItem(email);
		
		if(item==null)
			return new MyRespone(MyRespone.STATUS_Invalid, "Email not found");
		
		if(item.hasAttribute(TableDynamoDB_AccountLogin.ATTRIBUTE_Timeout_ForgotPassword)==false)
			return new MyRespone(MyRespone.STATUS_Invalid, "ForgotPassword not found");
		
		
		if(timeoutUpdate != item.getLong(TableDynamoDB_AccountLogin.ATTRIBUTE_Timeout_ForgotPassword))
			return new MyRespone(MyRespone.STATUS_Invalid, "VerifyCode error");
		
		if(System.currentTimeMillis() > timeoutUpdate)
			return new MyRespone(MyRespone.STATUS_Invalid, "Timeout");
		
		databaseAccount.changePassword(email, password);
		
		return new MyRespone(MyRespone.STATUS_Success);
	}

}
