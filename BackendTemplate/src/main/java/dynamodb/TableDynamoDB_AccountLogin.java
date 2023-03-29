package dynamodb;

import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

public class TableDynamoDB_AccountLogin {
    public static final String TABLE_NAME = "AccountLogin";
    public static final String INDEX_NAME_UserId = "UserId-index";
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String HASH_KEY = "Email";
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String ATTRIBUTE_password = "Password";
    ///////////////////////
    public static final String ATTRIBUTE_ipAddress = "Ip";
    public static final String ATTRIBUTE_infomation = "Info";
    
    public static final String ATTRIBUTE_TTL = "TimeRemove";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String ATTRIBUTE_userId = "UserId";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Table table;
    private TableDynamoDB_AccountLogin() {
        table = BaseDynamoDB.gI().dynamoDB.getTable(TABLE_NAME);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Item getItem(String email) {
    	try {
			return table.getItem(new GetItemSpec().withPrimaryKey(HASH_KEY, email));
		} catch (Exception e) {
			return null;
		}
	}
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean create(String email,long timeTTL,String ipAddress,long userId) {
		try {
			table.putItem(new Item().withPrimaryKey(HASH_KEY, email)
					.withNumber(ATTRIBUTE_TTL, timeTTL)
					.withString(ATTRIBUTE_ipAddress, ipAddress)
					.withNumber(ATTRIBUTE_userId, userId));
			return true;
		}catch (Exception e) {
			return false;
		}
	}


	public void completeRegisterWithVerifycode(long userId, String password) {
		ItemCollection<QueryOutcome> items = table.getIndex(INDEX_NAME_UserId).query(new QuerySpec().withKeyConditionExpression(ATTRIBUTE_userId+" = :uid").withValueMap(new ValueMap().withNumber(":uid",userId)));
		Iterator<Item> iter = items.iterator(); 
		Item item;
		while (iter.hasNext()) {
			item = iter.next();
			table.updateItem(new PrimaryKey(HASH_KEY, item.get(HASH_KEY))
					,new AttributeUpdate(ATTRIBUTE_password).put(password)
					,new AttributeUpdate(ATTRIBUTE_TTL).delete());
		}
	}

    //////////////////////////////////////////
    private static TableDynamoDB_AccountLogin instance;

    public static TableDynamoDB_AccountLogin gI() {
        return instance == null ? instance = new TableDynamoDB_AccountLogin() : instance;
    }
}
