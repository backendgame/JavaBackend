package backendgame.onehit;

import java.io.IOException;
import java.util.regex.Pattern;

import backendgame.Server_BackendGame;
import backendgame.config.CaseCheck;
import backendgame.config.CipherBinary;
import backendgame.config.RichardToken;
import backendgame.onehit.web.table.Onehit_Table110_Create;
import backendgame.onehit.web.table.Onehit_Table120_GetInfo;
import backendgame.onehit.web.table.Onehit_Table151_Config_AccessKey_Set;
import backendgame.onehit.web.table.Onehit_Table153_Config_ReadKey_Set;
import backendgame.onehit.web.table.Onehit_Table155_Config_WriteKey_Set;
import backendgame.onehit.web.table.Onehit_Table157_Config_TokenLifeTime;
import backendgame.onehit.web.table.Onehit_Table158_Config_LoginRule;
import backendgame.onehit.web.table.Onehit_Table159_Config_MailService;
import backendgame.onehit.web.table.Onehit_Table160_Config_DescribeTables;
import backendgame.onehit.web.table.Onehit_Table170_Logout_All_Client;
import backendgame.onehit.web.table.Onehit_Table190_Delete;
import backendgame.onehit.web.table.account_data.OnehitWeb_ParsingRow250_Querry_By_Credential;
import backendgame.onehit.web.table.account_data.OnehitWeb_ParsingRow251_Querry_By_ListUserId;
import backendgame.onehit.web.table.account_data.OnehitWeb_ParsingRow252_Querry_By_UserIdRange;
import backendgame.onehit.web.table.account_data.OnehitWeb_ParsingRow253_Querry_By_LatestCreate;
import backendgame.onehit.web.table.account_data.OnehitWeb_ParsingRow285_Update_UserData;
import backendgame.onehit.web.table.account_data.OnehitWeb_TableRow290_Update_AccountStatus;
import backendgame.onehit.web.table.account_data.OnehitWeb_TableRow299_Insert_Account;
import backendgame.onehit.web.table.sub.OnehitWeb_SubTable300_Create_Leaderboard;
import backendgame.onehit.web.table.sub.OnehitWeb_SubTable305_Create_Tile_Binary;
import backendgame.onehit.web.table.sub.OnehitWeb_SubTable308_Create_Tile_PrimaryKey;
import backendgame.onehit.web.table.sub.OnehitWeb_SubTable309_Create_TileRow_Without_PrimaryKey;
import backendgame.onehit.web.table.sub.OnehitWeb_SubTable350_Config_AccessKey;
import backendgame.onehit.web.table.sub.OnehitWeb_SubTable351_Config_ReadKey;
import backendgame.onehit.web.table.sub.OnehitWeb_SubTable352_Config_WriteKey;
import backendgame.onehit.web.table.sub.OnehitWeb_SubTable399_Delete;
import backendgame.onehit.web.table.sub.leaderboard.OnehitWeb_Leaderboard400_Config;
import backendgame.onehit.web.table.sub.leaderboard.OnehitWeb_Leaderboard420_Querry_Id;
import backendgame.onehit.web.table.sub.leaderboard.OnehitWeb_Leaderboard421_FullQuerry_Index;
import backendgame.onehit.web.table.sub.leaderboard.OnehitWeb_Leaderboard422_FullQuerry_Range;
import backendgame.onehit.web.table.sub.leaderboard.OnehitWeb_Leaderboard423_FullQuerry_Latest;
import backendgame.onehit.web.table.sub.leaderboard.OnehitWeb_Leaderboard460_UpdateData;
import backendgame.onehit.web.table.sub.leaderboard.OnehitWeb_Leaderboard490_LoadConfig;
import backendgame.onehit.web.table.sub.tile_binary.OnehitWeb_Sub_TileBinary600_Config;
import backendgame.onehit.web.table.sub.tile_binary.OnehitWeb_Sub_TileBinary620_Querry_Index;
import backendgame.onehit.web.table.sub.tile_binary.OnehitWeb_Sub_TileBinary621_Querry_Range;
import backendgame.onehit.web.table.sub.tile_binary.OnehitWeb_Sub_TileBinary622_Querry_Latest;
import backendgame.onehit.web.table.sub.tile_binary.OnehitWeb_Sub_TileBinary650_Data_Insert;
import backendgame.onehit.web.table.sub.tile_binary.OnehitWeb_Sub_TileBinary680_Data_Update;
import backendgame.onehit.web.table.sub.tile_primarykey.OnehitWeb_SubTable500_Config_DescribeTables;
import backendgame.onehit.web.table.sub.tile_primarykey.OnehitWeb_SubTable520_SimpleQuerry_Index;
import backendgame.onehit.web.table.sub.tile_primarykey.OnehitWeb_SubTable521_SimpleQuerry_Range;
import backendgame.onehit.web.table.sub.tile_primarykey.OnehitWeb_SubTable522_SimpleQuerry_Latest;
import backendgame.onehit.web.table.sub.tile_primarykey.OnehitWeb_SubTable540_FullQuerry_Index;
import backendgame.onehit.web.table.sub.tile_primarykey.OnehitWeb_SubTable541_FullQuerry_Range;
import backendgame.onehit.web.table.sub.tile_primarykey.OnehitWeb_SubTable542_FullQuerry_Latest;
import backendgame.onehit.web.table.sub.tile_primarykey.OnehitWeb_SubTable580_Data_Update;
import backendgame.onehit.web.table.sub.tile_row.OnehitWeb_Sub_TileRow700_Config;
import backendgame.onehit.web.table.sub.tile_row.OnehitWeb_Sub_TileRow720_Querry_Index;
import backendgame.onehit.web.table.sub.tile_row.OnehitWeb_Sub_TileRow721_Querry_Range;
import backendgame.onehit.web.table.sub.tile_row.OnehitWeb_Sub_TileRow722_Querry_Latest;
import backendgame.onehit.web.table.sub.tile_row.OnehitWeb_Sub_TileRow750_Data_Insert;
import backendgame.onehit.web.table.sub.tile_row.OnehitWeb_Sub_TileRow780_Data_Update;
import backendgame.onehit.web.test.OnehitWeb_TableRow_Random_Account;
import backendgame.onehit.web.test.OnehitWeb_Test_Table_TimeAvaiable;
import bgcore.core.IActionOnehit;
import bgcore.core.MessageSending;
import bgcore.core.OneHitProcessing;
import database.Manager_DBGameTable;
import database.operators.BB_Operators_Binary;
import database.operators.BB_Operators_Boolean;
import database.operators.BB_Operators_Byte;
import database.operators.BB_Operators_Double;
import database.operators.BB_Operators_Float;
import database.operators.BB_Operators_Integer;
import database.operators.BB_Operators_List;
import database.operators.BB_Operators_Long;
import database.operators.BB_Operators_Short;
import database.operators.BB_Operators_String;
import database.table.DBString;

public abstract class BaseOnehit_AiO extends OneHitProcessing {
	public static Manager_DBGameTable managerTable;

	public static BB_Operators_Boolean operatorBoolean;
	public static BB_Operators_Byte operatorByte;
	public static BB_Operators_Short operatorShort;
	public static BB_Operators_Integer operatorInteger;
	public static BB_Operators_Float operatorFloat;
	public static BB_Operators_Long operatorLong;
	public static BB_Operators_Double operatorDouble;
	public static BB_Operators_String operatorsString;
	public static BB_Operators_List operatorsList;
	public static BB_Operators_Binary operatorsBinary;
	
	
	protected static MessageSending mgOK;
	protected static MessageSending mgValueNull;
	protected static MessageSending mgNotEnought;
	protected static MessageSending mgOutOfRange;
	protected static MessageSending mgPasswordError;
	protected static MessageSending mgDatabaseNotExist;
	protected static MessageSending mgExpired;
	protected static MessageSending mgTokenError;
	protected static MessageSending mgSyntaxError;
	protected static MessageSending mgTimeout;
	protected static MessageSending mgVariableInvalid;
	protected static MessageSending mgInvalid;
	protected static MessageSending mgException;
	protected static MessageSending mgExist;
	protected static MessageSending mgNotExist;
	protected static MessageSending mgPlayerWrong;
	protected static MessageSending mgPlayerNotFound;
	protected static MessageSending mgPlayerInvalidData;

	public static Pattern patternUsername;
	public static Pattern patternEmail;
	
	public BaseOnehit_AiO(short command_id) {
		super(command_id);
	}
	
	public static void setup() {
		managerTable = Manager_DBGameTable.gI();
//		dataType = BB_DataType.gI();
//		
//		randomCache=RandomCache.gI();
//		ClientToken.randomCache=randomCache;
		RichardToken.cipherBinary=new CipherBinary();
		
		operatorBoolean = BB_Operators_Boolean.gI();
		operatorByte = BB_Operators_Byte.gI();
		operatorShort=BB_Operators_Short.gI();
		operatorInteger = BB_Operators_Integer.gI();
		operatorFloat = BB_Operators_Float.gI();
		operatorLong = BB_Operators_Long.gI();
		operatorDouble = BB_Operators_Double.gI();
		operatorsString = BB_Operators_String.gI();
		operatorsList=BB_Operators_List.gI();
		operatorsBinary=BB_Operators_Binary.gI();
		
		mgOK=new MessageSending((short) 0,CaseCheck.HOPLE);
		mgValueNull=new MessageSending((short) 0,CaseCheck.VALUE_NULL);
		mgNotEnought=new MessageSending((short) 0,CaseCheck.NOT_ENOUGHT);
		mgOutOfRange=new MessageSending((short) 0,CaseCheck.OUT_OF_RANGE);
		mgPasswordError=new MessageSending((short) 0,CaseCheck.PASSWORD_ERROR);
		mgDatabaseNotExist=new MessageSending((short) 0,CaseCheck.DATABASE_ERROR_FILE_NOT_FOUND);
		mgExpired=new MessageSending((short) 0,CaseCheck.EXPIRED);
		mgTokenError=new MessageSending((short) 0,CaseCheck.TOKEN_ERROR);
		mgSyntaxError=new MessageSending((short) 0,CaseCheck.SYNTAX_ERROR);
		mgTimeout=new MessageSending((short) 0,CaseCheck.TIMEOUT);
		mgVariableInvalid=new MessageSending((short) 0,CaseCheck.VARIABLE_INVALID);
		mgInvalid=new MessageSending((short) 0,CaseCheck.INVALID);
		mgException=new MessageSending((short) 0,CaseCheck.EXCEPTION);
		mgExist=new MessageSending((short) 0,CaseCheck.EXIST);
		mgNotExist=new MessageSending((short) 0,CaseCheck.NOT_EXIST);
		mgPlayerWrong=new MessageSending((short) 0,CaseCheck.PLAYER_WRONG);
		mgPlayerNotFound=new MessageSending((short) 0,CaseCheck.PLAYER_NOT_FOUND);
		mgPlayerInvalidData=new MessageSending((short) 0,CaseCheck.PLAYER_INVALIDATE_DATA);
		
		patternUsername=Pattern.compile("^[A-Za-z]\\w{5,29}$");
		patternEmail=Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$");
		////////////////////////////////////////////////////////////////////////////////////////////////////
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new Onehit_Table110_Create();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new Onehit_Table120_GetInfo();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new Onehit_Table151_Config_AccessKey_Set();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new Onehit_Table153_Config_ReadKey_Set();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new Onehit_Table155_Config_WriteKey_Set();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new Onehit_Table157_Config_TokenLifeTime();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new Onehit_Table158_Config_LoginRule();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new Onehit_Table159_Config_MailService();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new Onehit_Table160_Config_DescribeTables();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new Onehit_Table170_Logout_All_Client();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new Onehit_Table190_Delete();}});
		///////////////////////////////////
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_ParsingRow250_Querry_By_Credential();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_ParsingRow251_Querry_By_ListUserId();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_ParsingRow252_Querry_By_UserIdRange();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_ParsingRow253_Querry_By_LatestCreate();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_ParsingRow285_Update_UserData();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_TableRow290_Update_AccountStatus();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_TableRow299_Insert_Account();}});
		///////////////////////////////////
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable300_Create_Leaderboard();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable308_Create_Tile_PrimaryKey();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable305_Create_Tile_Binary();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable309_Create_TileRow_Without_PrimaryKey();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable350_Config_AccessKey();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable351_Config_ReadKey();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable352_Config_WriteKey();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable399_Delete();}});
		///////////////////////////////////
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Leaderboard400_Config();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Leaderboard420_Querry_Id();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Leaderboard421_FullQuerry_Index();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Leaderboard422_FullQuerry_Range();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Leaderboard423_FullQuerry_Latest();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Leaderboard460_UpdateData();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Leaderboard490_LoadConfig();}});
		///////////////////////////////////
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable500_Config_DescribeTables();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable520_SimpleQuerry_Index();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable521_SimpleQuerry_Range();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable522_SimpleQuerry_Latest();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable540_FullQuerry_Index();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable541_FullQuerry_Range();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable542_FullQuerry_Latest();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_SubTable580_Data_Update();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileBinary600_Config();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileBinary620_Querry_Index();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileBinary621_Querry_Range();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileBinary622_Querry_Latest();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileBinary650_Data_Insert();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileBinary680_Data_Update();}});
		
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileRow700_Config();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileRow720_Querry_Index();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileRow721_Querry_Range();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileRow722_Querry_Latest();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileRow750_Data_Insert();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Sub_TileRow780_Data_Update();}});
//		////////////////////////////////////////////////////////////////////////////////////////////////////
//		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitClient_Login_Without_Password();}});
//		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitClient_Login_With_Password();}});
//		
//		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitClient_SignUpAccount_CreateAccount();}});
//		
//		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitClient_Lobby_Get_UserData();}});
//		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitClient_UserData_Update_DataType();}});
//		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitClient_UserData_Update_Data();}});
//		////////////////////////////////////////////////////////////////////////////////////////////////////
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_Test_Table_TimeAvaiable();}});
		Server_BackendGame.serverOnehit.setProcess(new IActionOnehit() {@Override public OneHitProcessing create() {return new OnehitWeb_TableRow_Random_Account();}});
	}
	
	public long[] rangeToArray(long begin,long end) {
		if(begin>end) {
			int length = (int) (begin-end+1);
			long[] result=new long[length];
			for(int i=0;i<length;i++)
				result[i] = begin - i;
			return result;
		}else {
			int length = (int) (end-begin+1);
			long[] result=new long[length];
			for(int i=0;i<length;i++)
				result[i] = begin + i;
			return result;
		}
	}
	
	protected long findStringLocation(short tableId,String str) {
		if(str==null || str.equals(""))
			return -1;
		
		long result=0;
		DBString dbString=null;
		try {
			dbString = new DBString(tableId);
			result = dbString.findStringId(str.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(dbString!=null)
			dbString.close();
		return result;
	}
	
}
