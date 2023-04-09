package gameonline.rest.controller_user.HomeScreen;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;

public class Service_HomeScreen_Reload_UserData extends BaseAuthorization {

	@Override
	public MyRespone respone() {
		Item item = databaseUserData.getAllDataForHomeScreen(customerUserId);
		if(item==null)
			return resNotFound;
		try {
			return new MyRespone(MyRespone.STATUS_Success).setData(new ObjectMapper().readTree(item.toJSON()));
		} catch (JsonProcessingException e) {
			return new MyRespone(MyRespone.STATUS_Success).setMessage(getStringException(e));
		}
	}

}
