package gameonline.rest.controller_user.LoginScreen;

import java.math.BigDecimal;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dynamodb.TableDynamoDB_UserData;
import gameonline.rest.BaseVariable;
import gameonline.rest.BinaryToken;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;
import richard.TimeManager;

public class Service_LoginScreen11_Register_Submit extends BaseVariable {
	@NotNull public long userId;
	@NotNull public long verifyCode;

    @Size(min = 6, message = "Password phải từ 8 kí tự trở lên")
    @NotEmpty public String password;
    
	
	
	public MyRespone respone() {
		Item item = databaseUserData.getItem(userId);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(item==null)
			return resNotFound;
		if(item.hasAttribute(TableDynamoDB_UserData.ATTRIBUTE_verify)==false)
			return new MyRespone(MyRespone.STATUS_InternalServerError, "Info_VerifyCode not exist");
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Map<String, Object> mapInfo = item.getMap(TableDynamoDB_UserData.ATTRIBUTE_verify);
		
		///////////////////////////
		if(mapInfo.containsKey(TableDynamoDB_UserData.Info_VerifyTime)==false)
			return new MyRespone(MyRespone.STATUS_InternalServerError, "VerifyTime not exist");
		if(System.currentTimeMillis() > ((BigDecimal)mapInfo.get(TableDynamoDB_UserData.Info_VerifyTime)).longValue()) 
			return new MyRespone(MyRespone.STATUS_Invalid, "VerifyTime invalid");
		if(mapInfo.containsKey(TableDynamoDB_UserData.Info_VerifyCode)==false)
			return new MyRespone(MyRespone.STATUS_InternalServerError, "VerifyCode not exist");
		if(verifyCode != ((BigDecimal)mapInfo.get(TableDynamoDB_UserData.Info_VerifyCode)).longValue())
			return new MyRespone(MyRespone.STATUS_Invalid, "VerifyCode invalid");
		
		///////////////////////////
		databaseAccount.completeRegisterWithVerifycode(userId, password);
		databaseUserData.completeRegisterWithVerifycode(userId);
		
		BinaryToken token = new BinaryToken();
		token.userId = userId;
		token.timeOut = System.currentTimeMillis() + TimeManager.A_DAY_MILLISECOND;

		ObjectNode node = new ObjectMapper().createObjectNode();
		node.put(SystemConstant.TOKEN, token.toHashString());
		
		return new MyRespone(MyRespone.STATUS_Success).setData(node);
	}

}
