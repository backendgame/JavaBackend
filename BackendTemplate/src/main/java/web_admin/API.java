package web_admin;

public class API {
	
	public static final String LoginScreen_Login = "/api/login_screen";
	
	public static final String HOME_Reload_UserData = "Home-Reload";
	public static final String HOME_Update_Setting = "Home-UpdateSetting";
	//////////////////////////////
	public static final String TABLE_Create_Table	= "Table-Create";
	public static final String TABLE_Change_TableName	= "Table-ChangeName";
	public static final String TABLE_GetInfo_Table	= "Table-GetInfo";
	public static final String TABLE_Delete_Table	= "Table-Delete";
	//////////////////////////////
	public static final String TABLE_Config_AccessToken	= "Config-AccessToken";
	public static final String TABLE_Config_ReadToken	= "Config-ReadToken";
	public static final String TABLE_Config_WriteToken	= "Config-WriteToken";
	public static final String TABLE_Config_TokenLifeTime	= "Config-TokenLifeTime";
	public static final String TABLE_Config_MailService	= "Config-MailService";
	public static final String TABLE_Config_DescribeTables= "Config-DescribeTables";
	public static final String TABLE_Config_LoginRule	= "Config-LoginRule";
	public static final String TABLE_Config_LogoutAllAccount= "Config-LogoutAllAccount";
	//////////////////////////////
	public static final String TABLE_Create_Account		= "Row-CreateAccount";
	
	public static final String BinaryRowQuerry_By_ListUserId	= "BinaryRowQuerry-ListUserId";
	public static final String BinaryRowQuerry_By_UserIdRange	= "BinaryRowQuerry-UserIdRange";
	public static final String BinaryRowQuerry_By_LatestAccount	= "BinaryRowQuerry-LatestAccount";
	public static final String BinaryRowQuerry_By_Credential	= "BinaryRowQuerry-Credential";
	
	
//	public static final String TABLERow_QuerryAccount_ByRange		= "RowQuerryAccount-RangeUserId";
//	public static final String TABLERow_QuerryAccount_ByLatest		= "RowQuerryAccount-LatestCreate";
	
	public static final String TABLERow_QuerryAccountData_ByCredential	= "RowQuerryAccountData-Credential";
	public static final String TABLERow_QuerryAccountData_ByListUserId	= "RowQuerryAccountData-ListUserId";
	public static final String TABLERow_QuerryAccountData_ByRange		= "RowQuerryAccountData-RangeUserId";
	public static final String TABLERow_QuerryAccountData_ByLatest		= "RowQuerryAccountData-LatestCreate";
	
	public static final String ParsingRow_Update_UserData				= "Update-UserData";
	public static final String ParsingRow_Update_AccountStatus			= "Update-AccountStatus";
	
	public static final String TABLERow_Update_Row		= "Row-Update";
	//////////////////////////////
	public static final String SubTable_Create_Leaderboard		= "Create-Leaderboard";
	public static final String SubTable_Create_SubUserData		= "Create-SubUserData";
	public static final String SubTable_Create_TileBinary		= "Create-TileBinary";
	public static final String SubTable_Create_TileRow			= "Create-TileRow";
	public static final String SubTable_Rename_SubTable			= "SubTable-Rename";
	
	public static final String Leaderboard_Config				= "Config";
	public static final String Leaderboard_QuerryIndex			= "QuerryIndex";
	public static final String Leaderboard_QuerryFull_Index		= "QuerryFull-Index";
	public static final String Leaderboard_QuerryFull_Range		= "QuerryFull-Range";
	public static final String Leaderboard_QuerryFull_Latest	= "QuerryFull-Latest";
	public static final String Leaderboard_UpdateData			= "UpdateData";
	
	public static final String SubTable_Config_Tile_Custom			= "SubTable-config-tile-custom";
	public static final String SubTable_Config_Tile_Row			= "SubTable-config-tile-row";
	public static final String SubTable_Insert		= "SubTable-insert";
	public static final String SubTable_Querry		= "SubTable-querry";
	public static final String SubTable_Update		= "SubTable-update";
	public static final String SubTable_Delete		= "SubTable-delete";
	//////////////////////////////
	public static final String Client_LoginScreen_Device			= "Login-Device";
	public static final String Client_LoginScreen_SystemAccount		= "Login-SystemAccount";
	public static final String Client_LoginScreen_Facebook			= "Login-Facebook";
	public static final String Client_LoginScreen_Google			= "Login-Google";
	public static final String Client_LoginScreen_AdsId				= "Login-AdsId";
	public static final String Client_LoginScreen_Apple				= "Login-Apple";
	public static final String Client_LoginScreen_EmailCode			= "Login-EmailCode";
	
	public static final String TILE_Create		= "Tile-Create";
	public static final String TILE_Config		= "Tile-Config";
	public static final String TILE_Querry		= "Tile-Querry";
	public static final String TILE_Update		= "Tile-Update";
	public static final String TILE_Delete		= "Tile-Delete";



	////////////////////////////// Paypal payment
	public static final String PAYPAL_CHECKOUT_TRANSACTION		= "Checkout-Payment";

	public static final String PAYPAL_REVIEW_TRANSACTION		= "Review-Payment";
	public static final String PAYPAL_EXECUTE_TRANSACTION		= "Execute-Payment";


}
