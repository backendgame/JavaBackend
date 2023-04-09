package gameonline.rest;

import java.util.regex.Pattern;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import backendgame.com.core.ThreadPool;
import dynamodb.TableDynamoDB_AccountLogin;
import dynamodb.TableDynamoDB_UserData;
import gameonline.config.RandomCache;

public class BaseVariable {
	public static Pattern patternUsername;
//	public static Pattern patternEmail;
	
	public static RandomCache randomCache;
	public static ThreadPool threadPool;
	
	
//	200: Success
//	400 : Bad request - dữ liệu gửi lên không hợp lệ
//	401 : Unauthorized - user chưa được xác thực và truy cập vào resource yêu cầu phải xác thực
//	403: Forbidden - user không có quyền truy cập vào resource
//	404: Not found - không tồn tại resource
//	500: Internal Server Error - có lỗi xẩy ra trong hệ thống
	public static MyRespone resSuccess, resBadRequest, resUnauthorized, resForbidden, resNotFound, resInternalServerError, resInvalid, resDatabaseError;
	public static MyRespone resTokenTimeout,resTokenError;
	public static MyRespone resExisted;
	public static MyRespone resSuspend;
	
	public static TableDynamoDB_AccountLogin databaseAccount;
	public static TableDynamoDB_UserData databaseUserData;
	
	public static void setupGlobalService() {
		threadPool=ThreadPool.gI();
		
		randomCache=RandomCache.gI();
		
		databaseAccount = TableDynamoDB_AccountLogin.gI();
		databaseUserData = TableDynamoDB_UserData.gI();
		
		patternUsername=Pattern.compile("^[A-Za-z]\\w{5,29}$");
//		patternEmail=Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$");
		
		resInvalid = new MyRespone(MyRespone.STATUS_Invalid);
		resSuspend = new MyRespone(MyRespone.STATUS_Suspend);
		resDatabaseError = new MyRespone(MyRespone.STATUS_DatabaseError);
		
		resSuccess = new MyRespone(MyRespone.STATUS_Success);
		resBadRequest = new MyRespone(MyRespone.STATUS_BadRequest);
		resUnauthorized = new MyRespone(MyRespone.STATUS_Unauthorized);
		resForbidden = new MyRespone(MyRespone.STATUS_Forbidden);
		resNotFound = new MyRespone(MyRespone.STATUS_NotFound);
		resInternalServerError = new MyRespone(MyRespone.STATUS_InternalServerError);
		
		resTokenTimeout = new MyRespone(MyRespone.STATUS_TokenTimeout);
		resExisted = new MyRespone(MyRespone.STATUS_Existed);
	}
	
	public final String getStringException(Exception paramException) {
		String str = paramException.toString();
		StackTraceElement[] arrayOfStackTraceElement;
		if ((arrayOfStackTraceElement = paramException.getStackTrace()) != null) {
			int i = arrayOfStackTraceElement.length;
			for (byte b = 0; b < i; b++) {
				StackTraceElement stackTraceElement = arrayOfStackTraceElement[b];
				str = str + "\n" + stackTraceElement;
			}
		}
		return str;
	}
	
	protected String getIp() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr();
	}
}
