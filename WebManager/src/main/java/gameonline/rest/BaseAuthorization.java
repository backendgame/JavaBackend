package gameonline.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BaseAuthorization extends BaseVariable{
	@JsonIgnore protected long customerUserId;
	@JsonIgnore protected String token;
	@JsonIgnore protected BinaryToken binaryToken;
	public MyRespone process(String _token) {
		token=_token;
		binaryToken = new BinaryToken();
		if(binaryToken.decode(token)==false)
			return resTokenError;
		if(System.currentTimeMillis()>binaryToken.timeOut)
			return resTokenTimeout;
		
		
		customerUserId = binaryToken.userId;
		return respone();
//		this.token = token;
		/**Verify token ➜ userId
		 * Nếu verify thất bại
		 *    return resUnauthorized;
		 * Thành công ➜ xử lý respone
		 * 
		 * */
	}
	
	protected abstract MyRespone respone();
}
