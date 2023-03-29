package gameonline.rest.controller_user.Tile;

import gameonline.rest.BaseVariable;
import gameonline.rest.MyRespone;

public class Service_TileScreen120_Update_Tile extends BaseVariable{
	public String token;
	public short tileId;
	public short regionId;
	
	public MyRespone respone() {
		System.err.println("Wait for process!!!");
		return resSuccess;
	}
}
