package gameonline.rest.controller_user.LoginScreen;

import java.util.HashMap;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dynamodb.TableDynamoDB_AccountLogin;
import dynamodb.TableDynamoDB_UserData;
import gameonline.config.Config;
import gameonline.rest.BaseVariable;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;
import richard.Lib;

public class Service_LoginScreen10_Register_Request extends BaseVariable {
	@Email
	@NotEmpty
	public String email;
	
	public MyRespone respone() {
		long verifyCode = randomCache.getVerifyCode();
		
		Item item = databaseAccount.getItem(email); 
		if(item==null){
			long userId = randomCache.randomTimeId();
			long timeTTL = System.currentTimeMillis()/1000 + 1800;//30 phút 
			
			if (databaseAccount.create(email, timeTTL, getIp(), userId) == false)
				return new MyRespone(MyRespone.STATUS_InternalServerError, TableDynamoDB_AccountLogin.TABLE_NAME+" exist "+email);
			
			HashMap<String, Object> infoVerifyCode = new HashMap<String, Object>();
			infoVerifyCode.put(TableDynamoDB_UserData.Info_VerifyCode, verifyCode);
			infoVerifyCode.put(TableDynamoDB_UserData.Info_VerifyTime, System.currentTimeMillis()+28800000);
			if(databaseUserData.insertPlayer(userId, timeTTL, infoVerifyCode)==false)
				return new MyRespone(MyRespone.STATUS_InternalServerError, "insert UserData error");
			
			threadPool.runThread(new Runnable() {
				public void run() {
					Lib.sendEmail(Config.Gmail_Send, Config.Gmail_AppPassword, email, "VerifyCode (avaiable 60s)", "VerifyCode : "+verifyCode);
//					System.out.println("Gởi mail("+email+") : VerifyCode("+verifyCode+")");
				}
			});
			
			ObjectNode node = new ObjectMapper().createObjectNode();
			node.put(SystemConstant.USERID, userId);
			
			return new MyRespone(MyRespone.STATUS_Success).setData(node);
		}else {
			if(item.hasAttribute(TableDynamoDB_AccountLogin.ATTRIBUTE_TTL))
				return new MyRespone(MyRespone.STATUS_Warning).setMessage("a VerifyCode sent to " + email);
			else				
				return resExisted;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}
