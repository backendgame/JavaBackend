package gameonline.rest.controller_user;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gameonline.config.API;
import gameonline.rest.MyRespone;
import gameonline.rest.controller_user.Table.Service_TableScreen110_Create_Table;
import gameonline.rest.controller_user.Table.Service_TableScreen_Change_TableName;
import gameonline.rest.controller_user.Table.Service_TableScreen120_GetInfo_Table;
import gameonline.rest.controller_user.Table.Service_TableScreen190_Delete_Table;
import gameonline.rest.controller_user.Table.Service_TableScreen151_Config_AccessToken;
import gameonline.rest.controller_user.Table.Service_TableScreen153_Config_ReadKey;
import gameonline.rest.controller_user.Table.Service_TableScreen155_Config_WriteKey;
import gameonline.rest.controller_user.Table.Service_TableScreen157_Config_TokenLifeTime;
import gameonline.rest.controller_user.Table.Service_TableScreen159_Config_MailService;
import gameonline.rest.controller_user.Table.Service_TableScreen160_Config_DescribeTables;
import gameonline.rest.controller_user.Table.Service_TableScreen158_Config_LoginRule;
import gameonline.rest.controller_user.Table.Service_TableScreen170_Config_LogoutAllAccount;
import gameonline.test.Service_TableScreen0_Change_TimeAvaiable;
import io.swagger.annotations.ApiOperation;

//@Api(tags = "Màn hình Table", description = "TableInfo luôn được cache dưới FE")
@RestController
@RequestMapping(path = "/api/lobby/data/tables")
public class Controller003_TableScreen {public static String PATH=null;
	
	@PostMapping(value = API.TABLE_Create_Table, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone createTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen110_Create_Table service) {
		return service.process(token);
	}
	
	@ApiOperation("Đổi tên table ➜ cập nhật ở dynamoDB")
	@PostMapping(value = API.TABLE_Change_TableName, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone configTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen_Change_TableName service) {
		return service.process(token);
	}
	
	@ApiOperation("Lấy thông tin config cho màn hình Config table")
	@PostMapping(value = API.TABLE_GetInfo_Table, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone getTableInfo(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen120_GetInfo_Table service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.TABLE_Delete_Table, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone deleteTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen190_Delete_Table service) {
		return service.process(token);
	}
	//////////////////////////
		@ApiOperation("accessToken là chuỗi rỗng thì sẽ gen ra accessToken mới")
		@PostMapping(value = API.TABLE_Config_AccessToken, produces = MediaType.APPLICATION_JSON_VALUE)
		public MyRespone configTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen151_Config_AccessToken service) {
			return service.process(token);
		}
		
		@ApiOperation("readToken là chuỗi rỗng thì sẽ gen ra readToken mới")
		@PostMapping(value = API.TABLE_Config_ReadToken, produces = MediaType.APPLICATION_JSON_VALUE)
		public MyRespone configTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen153_Config_ReadKey service) {
			return service.process(token);
		}
		
		@ApiOperation("writeToken là chuỗi rỗng thì sẽ gen ra writeToken mới")
		@PostMapping(value = API.TABLE_Config_WriteToken, produces = MediaType.APPLICATION_JSON_VALUE)
		public MyRespone configTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen155_Config_WriteKey service) {
			return service.process(token);
		}
		
		@ApiOperation("TokenLifeTime thì sẽ không giới hạn timeout")
		@PostMapping(value = API.TABLE_Config_TokenLifeTime, produces = MediaType.APPLICATION_JSON_VALUE)
		public MyRespone configTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen157_Config_TokenLifeTime service) {
			return service.process(token);
		}
		
		@ApiOperation("LoginRule")
		@PostMapping(value = API.TABLE_Config_LoginRule, produces = MediaType.APPLICATION_JSON_VALUE)
		public MyRespone configTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen158_Config_LoginRule service) {
			return service.process(token);
		}
		
		@ApiOperation("Email send verifyCode lúc client đăng nhập")
		@PostMapping(value = API.TABLE_Config_MailService, produces = MediaType.APPLICATION_JSON_VALUE)
		public MyRespone configTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen159_Config_MailService service) {
			return service.process(token);
		}
		
		@ApiOperation("DescribeTables")
		@PostMapping(value = API.TABLE_Config_DescribeTables, produces = MediaType.APPLICATION_JSON_VALUE)
		public MyRespone configTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen160_Config_DescribeTables service) {
			return service.process(token);
		}
		
		@ApiOperation("Đăng xuất tất cả client")
		@PostMapping(value = API.TABLE_Config_LogoutAllAccount, produces = MediaType.APPLICATION_JSON_VALUE)
		public MyRespone configTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen170_Config_LogoutAllAccount service) {
			return service.process(token);
		}
		
		@ApiOperation("TimeAvaiable")
		@PostMapping(value = "TimeAvaiable", produces = MediaType.APPLICATION_JSON_VALUE)
		public MyRespone configTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen0_Change_TimeAvaiable service) {
			return service.process(token);
		}
}
