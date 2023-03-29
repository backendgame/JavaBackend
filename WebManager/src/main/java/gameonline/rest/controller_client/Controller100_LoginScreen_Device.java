package gameonline.rest.controller_client;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gameonline.config.API;
import gameonline.rest.MyRespone;
import gameonline.rest.controller_client.login.ServiceClient_Login0_Device;
import gameonline.rest.controller_client.login.ServiceClient_Login1_SystemAccount;
import gameonline.rest.controller_client.login.ServiceClient_Login2_Facebook;
import gameonline.rest.controller_client.login.ServiceClient_Login3_Google;
import gameonline.rest.controller_client.login.ServiceClient_Login4_AdsId;
import gameonline.rest.controller_client.login.ServiceClient_Login5_AppleId;
import gameonline.rest.controller_client.login.ServiceClient_Login6_EmailCode;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/client/login_screen")
public class Controller100_LoginScreen_Device {public static String PATH=null;

	@ApiOperation("Đăng nhập Device")
	@PostMapping(value = API.Client_LoginScreen_Device, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone login(@Valid @RequestBody ServiceClient_Login0_Device service) {
		return service.respone();
	}
	
	@ApiOperation("Đăng nhập SystemAccount")
	@PostMapping(value = API.Client_LoginScreen_SystemAccount, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone login(@Valid @RequestBody ServiceClient_Login1_SystemAccount service) {
		return service.respone();
	}
	
	@ApiOperation("Đăng nhập Facebook token")
	@PostMapping(value = API.Client_LoginScreen_Facebook, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone login(@Valid @RequestBody ServiceClient_Login2_Facebook service) {
		return service.respone();
	}
	
	@ApiOperation("Đăng nhập google token")
	@PostMapping(value = API.Client_LoginScreen_Google, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone login(@Valid @RequestBody ServiceClient_Login3_Google service) {
		return service.respone();
	}
	
	@ApiOperation("Đăng nhập ADS_ID")
	@PostMapping(value = API.Client_LoginScreen_AdsId, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone login(@Valid @RequestBody ServiceClient_Login4_AdsId service) {
		return service.respone();
	}
	
	@ApiOperation("Đăng nhập AppleId")
	@PostMapping(value = API.Client_LoginScreen_Apple, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone login(@Valid @RequestBody ServiceClient_Login5_AppleId service) {
		return service.respone();
	}
	
	@ApiOperation("Đăng nhập EmailCode")
	@PostMapping(value = API.Client_LoginScreen_EmailCode, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone login(@Valid @RequestBody ServiceClient_Login6_EmailCode service) {
		return service.respone();
	}

}
