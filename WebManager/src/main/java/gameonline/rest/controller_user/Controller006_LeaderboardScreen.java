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
import gameonline.rest.controller_user.Table.sub.leaderboard.Service_Leaderboard400_Config;
import gameonline.rest.controller_user.Table.sub.leaderboard.Service_Leaderboard420_QuerryIndex;
import gameonline.rest.controller_user.Table.sub.leaderboard.Service_Leaderboard421_FullQuerry_Index;
import gameonline.rest.controller_user.Table.sub.leaderboard.Service_Leaderboard422_FullQuerry_Range;
import gameonline.rest.controller_user.Table.sub.leaderboard.Service_Leaderboard423_FullQuerry_Latest;
import gameonline.rest.controller_user.Table.sub.leaderboard.Service_Leaderboard460_UpdateData;
import gameonline.rest.controller_user.Table.sub.leaderboard.Service_Leaderboard490_LoadConfig;

@RestController
@RequestMapping(path = "/api/lobby/data/leaderboard")
public class Controller006_LeaderboardScreen {

	

	@PostMapping(value = API.Leaderboard_Config, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone configSubTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_Leaderboard400_Config service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.Leaderboard_QuerryIndex, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone configSubTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_Leaderboard420_QuerryIndex service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.Leaderboard_QuerryFull_Index, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone configSubTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_Leaderboard421_FullQuerry_Index service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.Leaderboard_QuerryFull_Range, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone configSubTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_Leaderboard422_FullQuerry_Range service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.Leaderboard_QuerryFull_Latest, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone configSubTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_Leaderboard423_FullQuerry_Latest service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.Leaderboard_UpdateData, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone configSubTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_Leaderboard460_UpdateData service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.Leaderboard_LoadConfig, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone configSubTable(@RequestHeader("token") final String token, @Valid @RequestBody Service_Leaderboard490_LoadConfig service) {
		return service.process(token);
	}
	
}
