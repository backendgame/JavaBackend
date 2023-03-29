package dynamodb;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.Expected;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

public class TableDynamoDB_UserData {
    public static final String TABLE_NAME = "BackendGame_UserData";
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String HASH_KEY = "UserId";
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String ATTRIBUTE_usd = "USD";
    
    public static final String ATTRIBUTE_lastTimeLogin = "LastTime";
//    public static final String ATTRIBUTE_ipAddress = "Ip";
    public static final String ATTRIBUTE_verify = "Verify";
//    public static final String ATTRIBUTE_password = "password";

	public static final String Info_VerifyCode = "VerifyCode";
	public static final String Info_VerifyTime = "VerifyTime";
	
	public static final String ATTRIBUTE_TTL = "TimeRemove";

	public static final String ATTRIBUTE_Tables = "Tables";
	public static final String ATTRIBUTE_Tiles = "Tiles";
	public static final String ATTRIBUTE_Setting = "Setting";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String TABLE_SubTables = "SubTables";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Table table;
	public Expected requirementNotExist;
	public Expected requirementMustExist;
    private TableDynamoDB_UserData() {
        table = BaseDynamoDB.gI().dynamoDB.getTable(TABLE_NAME);
		requirementNotExist=new Expected(HASH_KEY).notExist();
		requirementMustExist=new Expected(HASH_KEY).exists();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean insertPlayer(long userId, long timeTTL, HashMap<String, Object> verify) {
    	try {
    		table.putItem(new Item().withPrimaryKey(HASH_KEY, userId)
				.withNumber(ATTRIBUTE_TTL, timeTTL)
				.withMap(ATTRIBUTE_Tables, new HashMap<>())
				.withMap(ATTRIBUTE_Tiles, new HashMap<>())
   				.withMap(ATTRIBUTE_verify, verify));
    		return true;
    	}catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

    public Item getItem(long userId) {
    	try {
			return table.getItem(new GetItemSpec().withPrimaryKey(HASH_KEY, userId));
		} catch (Exception e) {
			return null;
		}
    }
    
	public Item getAllDataForHomeScreen(long userId) {
        try {
            Item item = table.updateItem(new UpdateItemSpec().withPrimaryKey(HASH_KEY, userId)
    				.withUpdateExpression("SET "+ATTRIBUTE_lastTimeLogin+" = :t")
    				.withValueMap(new ValueMap().withNumber(":t", System.currentTimeMillis()))
//    				.withExpected(requirementMustExist)
    				.withReturnValues(ReturnValue.ALL_NEW)).getItem();
            
    		item.removeAttribute(TableDynamoDB_UserData.HASH_KEY);
    		Map<?,?> mapInfo = (Map<?, ?>) item.get(TableDynamoDB_UserData.ATTRIBUTE_verify);
    		if(mapInfo!=null) {
	    		mapInfo.remove(TableDynamoDB_UserData.Info_VerifyCode);
	    		mapInfo.remove(TableDynamoDB_UserData.Info_VerifyTime);
    		}
            return item;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

    public void completeRegisterWithVerifycode(long userId) {
    	table.updateItem(new PrimaryKey(HASH_KEY, userId)
    			,new AttributeUpdate(ATTRIBUTE_TTL).delete()
    			,new AttributeUpdate(ATTRIBUTE_verify).delete());    	
    }

//	public void updateTableDatabase(long userId, byte regionId, short tableId, String tableName) {
//		String TABLENODE = "R"+regionId+"T"+tableId;
//		table.updateItem(new UpdateItemSpec().withPrimaryKey(HASH_KEY, userId)
//				.withUpdateExpression("SET "+ATTRIBUTE_Tables+"."+TABLENODE+" = :tableName")
//				.withValueMap(new ValueMap().withString(":tableName", tableName))
//				.withReturnValues(ReturnValue.ALL_NEW));
//	}

//	public boolean updateTableDatabaseName(long userId, final String tableId, final String tableName) {
//		try {
//			Map TableDatabase = table.getItem(new GetItemSpec().withPrimaryKey(HASH_KEY, userId))
//					.getMap(ATTRIBUTE_DBS);
//			Map currentTableDb = TableDatabase != null ? (Map) TableDatabase.get(tableId) : null;
//			if(currentTableDb != null) {
//				currentTableDb.replace("Name", tableName);
//				AttributeUpdate updateVal = new AttributeUpdate(ATTRIBUTE_DBS);
//				updateVal.put(TableDatabase);
//				// Update command should be executed
//				table.updateItem(new PrimaryKey(HASH_KEY, userId), updateVal);
//				return true;
//			}
//			else {
//				return false;
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}



    //////////////////////////////////////////
    
    
    
    
    //////////////////////////////////////////
    private static TableDynamoDB_UserData instance;
    public static TableDynamoDB_UserData gI() {
        return instance == null ? instance = new TableDynamoDB_UserData() : instance;
    }
}
