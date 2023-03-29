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
import gameonline.rest.controller_user.Table.sub.Service_SubTableScreen300_Create_Leaderboard;
import gameonline.rest.controller_user.Table.sub.Service_SubTableScreen305_Create_Tile_Binary;
import gameonline.rest.controller_user.Table.sub.Service_SubTableScreen308_Create_Tile_PrimaryKey;
import gameonline.rest.controller_user.Table.sub.Service_SubTableScreen309_Create_TileRow_Without_PrimaryKey;
import gameonline.rest.controller_user.Table.sub.Service_SubTableScreen350_Config_AccessKey;
import gameonline.rest.controller_user.Table.sub.Service_SubTableScreen351_Config_ReadKey;
import gameonline.rest.controller_user.Table.sub.Service_SubTableScreen352_Config_WriteKey;
import gameonline.rest.controller_user.Table.sub.Service_SubTableScreen_Rename;

//@Api(tags = "Màn hình Table", description = "TableInfo luôn được cache dưới FE")
@RestController
@RequestMapping(path = "/api/lobby/data/sub_table")
public class Controller005_SubTableScreen {public static String PATH=null;

	@PostMapping(value = API.SubTable_Create_Leaderboard, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone insertRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_SubTableScreen300_Create_Leaderboard service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.SubTable_Create_TileBinary, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone insertRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_SubTableScreen305_Create_Tile_Binary service) {
		return service.process(token);
	}
	@PostMapping(value = API.SubTable_Create_TilePrimaryKey, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone insertRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_SubTableScreen308_Create_Tile_PrimaryKey service) {
		return service.process(token);
	}
	@PostMapping(value = API.SubTable_Create_TileRow, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone insertRow(@RequestHeader("token") final String token, @Valid @RequestBody Service_SubTableScreen309_Create_TileRow_Without_PrimaryKey service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.SubTable_Rename, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone rename(@RequestHeader("token") final String token, @Valid @RequestBody Service_SubTableScreen_Rename service) {
		return service.process(token);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@PostMapping(value = API.SubTable_Config_AccessToken, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone config(@RequestHeader("token") final String token, @Valid @RequestBody Service_SubTableScreen350_Config_AccessKey service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.SubTable_Config_ReadToken, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone config(@RequestHeader("token") final String token, @Valid @RequestBody Service_SubTableScreen351_Config_ReadKey service) {
		return service.process(token);
	}
	
	@PostMapping(value = API.SubTable_Config_WriteToken, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone config(@RequestHeader("token") final String token, @Valid @RequestBody Service_SubTableScreen352_Config_WriteKey service) {
		return service.process(token);
	}
	
	
}
