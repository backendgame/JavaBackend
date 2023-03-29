package gameonline.rest.controller_user;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gameonline.config.API;
import gameonline.rest.MyRespone;
import gameonline.rest.controller_user.LoginScreen.Service_LoginScreen10_Register_Request;
import gameonline.rest.controller_user.LoginScreen.Service_LoginScreen11_Register_Submit;
import gameonline.rest.controller_user.LoginScreen.Service_LoginScreen20_ForgotPassword_Request;
import gameonline.rest.controller_user.LoginScreen.Service_LoginScreen21_ForgotPassword_Submit;
import gameonline.rest.controller_user.LoginScreen.Service_LoginScreen_Login_With_EmailPassword;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(path = "/api/login_screen")
public class Controller001_LoginScreen {public static String PATH=null;

	@ApiOperation("Đăng nhập")
	@PostMapping(value = API.LoginScreen_Login_With_EmailPassword, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone loginWithEmailPassword(@Valid @RequestBody Service_LoginScreen_Login_With_EmailPassword service) {
		return service.respone();
	}
	
	@ApiOperation("Đăng ký")
	@PostMapping(value = API.LoginScreen_Register_GetVerifyCode, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone email_Request_VerifyCode(@Valid @RequestBody Service_LoginScreen10_Register_Request service) {
		return service.respone();
	}
	
	@ApiOperation("Verify Account đăng ký")
	@PostMapping(value = API.LoginScreen_Register_CompleteVerifycode, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone email_LoginBy_VerifyCode(@Valid @RequestBody Service_LoginScreen11_Register_Submit service) {
		return service.respone();
	}
	
	
	@ApiOperation("Quên mật khẩu")
	@PostMapping(value = API.LoginScreen_ForgotPassword_Request, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone email_LoginBy_VerifyCode(@Valid @RequestBody Service_LoginScreen20_ForgotPassword_Request service) {
		return service.respone();
	}
	
	@ApiOperation("Quên mật khẩu")
	@PostMapping(value = API.LoginScreen_ForgotPassword_Submit, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone email_LoginBy_VerifyCode(@Valid @RequestBody Service_LoginScreen21_ForgotPassword_Submit service) {
		return service.respone();
	}
	
}
