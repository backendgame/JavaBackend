package gameonline.rest.controller_user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gameonline.config.API;
import gameonline.rest.MyRespone;
import gameonline.rest.controller_user.Tile.Service_TileScreen01_Create_Tile;
import gameonline.rest.controller_user.Tile.Service_TileScreen05_Config_Tile;
import gameonline.rest.controller_user.Tile.Service_TileScreen110_Querry_Tile;
import gameonline.rest.controller_user.Tile.Service_TileScreen120_Update_Tile;
import gameonline.rest.controller_user.Tile.Service_TileScreen129_Delete_Tile;

@RestController
@RequestMapping(path = "/api/lobby/data/tiles")
public class Controller008_TileScreen {
	public static String PATH = null;

	@PostMapping(value = API.TILE_Create, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone createTable(@RequestBody Service_TileScreen01_Create_Tile service) {
		return service.respone();
	}
	
	@PostMapping(value = API.TILE_Config, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone createTable(@RequestBody Service_TileScreen05_Config_Tile service) {
		return service.respone();
	}
	
	@PostMapping(value = API.TILE_Querry, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone createTable(@RequestBody Service_TileScreen110_Querry_Tile service) {
		return service.respone();
	}
	
	@PostMapping(value = API.TILE_Update, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone createTable(@RequestBody Service_TileScreen120_Update_Tile service) {
		return service.respone();
	}
	
	@PostMapping(value = API.TILE_Delete, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone createTable(@RequestBody Service_TileScreen129_Delete_Tile service) {
		return service.respone();
	}
}
