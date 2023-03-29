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
import gameonline.rest.controller_user.Table.account.Service_ParsingRowScreen250_Querry_By_Credential;
import gameonline.rest.controller_user.Table.account.Service_ParsingRowScreen251_Querry_By_ListUserId;
import gameonline.rest.controller_user.Table.account.Service_ParsingRowScreen252_Querry_By_UserIdRange;
import gameonline.rest.controller_user.Table.account.Service_ParsingRowScreen253_Querry_By_LatestCreate;
import gameonline.rest.controller_user.Table.account.Service_ParsingRowScreen285_Update_UserData;
import gameonline.rest.controller_user.Table.account.Service_TableRowScreen290_Update_AccountStatus;
import gameonline.rest.controller_user.Table.account.Service_TableRowScreen299_Insert_Account;
import gameonline.test.Service_TableScreen999_Random_Account;

//@Api(tags = "Màn hình Table", description = "TableInfo luôn được cache dưới FE")
@RestController
@RequestMapping(path = "/api/lobby/data/table_rows")
public class Controller004_TableRowScreen {public static String PATH=null;
	
	@PostMapping(value = API.BinaryRowQuerry_By_Credential, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone querryRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_ParsingRowScreen250_Querry_By_Credential service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.BinaryRowQuerry_By_ListUserId, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone configRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_ParsingRowScreen251_Querry_By_ListUserId service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.BinaryRowQuerry_By_UserIdRange, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone querryRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_ParsingRowScreen252_Querry_By_UserIdRange service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.BinaryRowQuerry_By_LatestAccount, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone querryRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_ParsingRowScreen253_Querry_By_LatestCreate service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.ParsingRow_Update_UserData, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone updateRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_ParsingRowScreen285_Update_UserData service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.ParsingRow_Update_AccountStatus, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone updateRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableRowScreen290_Update_AccountStatus service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.TABLE_Create_Account, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone insertRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableRowScreen299_Insert_Account service) {
		return service.process(token);
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	@PostMapping(value = API.TABLERow_QuerryAccountData_ByListUserId, produces = MediaType.APPLICATION_JSON_VALUE)
//	public MyRespone querryRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableRowScreen110_ParsingQuerry_By_ListUserId service) {
//		return service.process(token);
//	}
//	
//	@PostMapping(value = API.TABLERow_QuerryAccountData_ByRange, produces = MediaType.APPLICATION_JSON_VALUE)
//	public MyRespone querryRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen116_Querry_AccountData_By_UseridRange service) {
//		return service.process(token);
//	}
//	
//	@PostMapping(value = API.TABLERow_QuerryAccountData_ByLatest, produces = MediaType.APPLICATION_JSON_VALUE)
//	public MyRespone querryRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen117_Querry_AccountData_By_LatestCreate service) {
//		return service.process(token);
//	}
//	
//	@PostMapping(value = API.TABLERow_QuerryAccountData_ByCredential, produces = MediaType.APPLICATION_JSON_VALUE)
//	public MyRespone querryRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen118_Querry_AccountData_By_Credential service) {
//		return service.process(token);
//	}
	
	
	@PostMapping(value = "create-random-account", produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone createRandomAccount(@RequestHeader("token") final String token, @Valid @RequestBody Service_TableScreen999_Random_Account service) {
		return service.process(token);
	}
	
	
}
