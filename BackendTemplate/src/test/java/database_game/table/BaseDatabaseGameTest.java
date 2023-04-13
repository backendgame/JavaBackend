package database_game.table;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.database.DBDescribe;
import begame.config.PATH;
import database_game.DatabaseId;

class BaseDatabaseGameTest {
	private long timeBegin;
	private short tableId=1989;
	public DBGame_AccountLogin databaseAccount;
	public DBGame_UserData databaseUserData;
	public Random random;

	@Test void test() throws IOException {
		timeBegin=System.currentTimeMillis();
		if(new File(PATH.DATABASE_FOLDER+"/"+tableId).exists()==false)
			new File(PATH.DATABASE_FOLDER+"/"+tableId).mkdirs();
		random=new Random();
		databaseAccount=new DBGame_AccountLogin(tableId);
		databaseUserData=new DBGame_UserData(tableId);
		
		DBDescribe[] listRandom = new DBDescribe[10];
		for(int i=0;i<listRandom.length;i++)
			listRandom[i]=randomDescribe();
		databaseUserData.setDescribe(listRandom);
		databaseUserData.traceDescribe();///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		randomAccount(500 + random.nextInt(100));
		databaseAccount.insertAccount("Dương Đức Trí", DatabaseId.Device, randomString(4), databaseUserData);
		randomAccount(500 + random.nextInt(100));
		databaseAccount.insertAccount("Dương Đức Tiến", DatabaseId.EmailCode, randomString(4), databaseUserData);
		randomAccount(500 + random.nextInt(100));
		databaseAccount.insertAccount("Dương Đức Trí", DatabaseId.SystemAccount, randomString(4), databaseUserData);
		randomAccount(500 + random.nextInt(100));
		databaseAccount.insertAccount("Nguyễn Thùy Dung", DatabaseId.EmailCode, randomString(4), databaseUserData);
		randomAccount(500 + random.nextInt(100));
		databaseAccount.insertAccount("Dương Đức Trí", DatabaseId.Facebook, randomString(4), databaseUserData);
		randomAccount(500 + random.nextInt(100));
		databaseAccount.insertAccount("Dương Đức Trí", DatabaseId.Google, randomString(4), databaseUserData);
		randomAccount(500 + random.nextInt(100));
		databaseAccount.insertAccount("Dương Đức Trí", DatabaseId.AdsId, randomString(4), databaseUserData);
		randomAccount(500 + random.nextInt(100));
		databaseAccount.insertAccount("Dương Đức Trí", DatabaseId.Apple, randomString(4), databaseUserData);
		randomAccount(500 + random.nextInt(100));
		databaseAccount.insertAccount("Dương Đức Trí", DatabaseId.EmailCode, randomString(4), databaseUserData);
		randomAccount(500 + random.nextInt(100));
		
		System.out.println("NumberColumn : "+databaseUserData.countRow());
//		databaseAccount.traceInfo(0, databaseUserData);
//		databaseAccount.traceInfo(1, databaseUserData);
//		databaseAccount.traceInfo(2, databaseUserData);
		System.out.println("*******************************************************************************");
		databaseAccount.traceInfo("Dương Đức Trí", databaseUserData);
		System.out.println("*******************************************************************************");
//		databaseAccount.traceInfo("Dương Đức Trí", databaseUserData, listRandom);
	}

    public DBDescribe randomDescribe() {
        DBDescribe describe = new DBDescribe();
        describe.Type = -1;
        switch (random.nextInt(17)) {
            case 0:describe.Type = DBDefine_DataType.BOOLEAN;
            case 1:if (describe.Type == -1)describe.Type = DBDefine_DataType.BYTE;
            case 2:if (describe.Type == -1)describe.Type = DBDefine_DataType.STATUS;
            case 3:if (describe.Type == -1)describe.Type = DBDefine_DataType.PERMISSION;
            case 4:if (describe.Type == -1)describe.Type = DBDefine_DataType.AVARTAR;
                describe.Size = 1;
                describe.ColumnName = randomString(8 + random.nextInt(3));
                describe.loadDefaultData((byte)random.nextInt());
                break;

            case 5:describe.Type = DBDefine_DataType.SHORT;
                describe.Size = 2;
                describe.ColumnName = randomString(8 + random.nextInt(3));
                describe.loadDefaultData((short)random.nextInt());
                break;

            case 6:describe.Type = DBDefine_DataType.FLOAT;
                describe.Size = 4;
                describe.ColumnName = randomString(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextFloat());
                break;

            case 7:describe.Type = DBDefine_DataType.IPV4;
            case 8:if (describe.Type == -1)describe.Type = DBDefine_DataType.INTEGER;
                describe.Size = 4;
                describe.ColumnName = randomString(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextInt());
                break;

            case 9:describe.Type = DBDefine_DataType.DOUBLE;
                describe.Size = 8;
                describe.ColumnName = randomString(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextDouble());
                break;

            case 10:describe.Type = DBDefine_DataType.USER_ID;
            case 11:if (describe.Type == -1)describe.Type = DBDefine_DataType.TIMEMILI;
            case 12:if (describe.Type == -1)describe.Type = DBDefine_DataType.LONG;
                describe.Size = 8;
                describe.ColumnName = randomString(8 + random.nextInt(3));
                describe.loadDefaultData(random.nextLong());
                break;

            case 13:describe.Type = DBDefine_DataType.STRING;
                describe.Size = 8 + random.nextInt(5);
                describe.ColumnName = randomString(8 + random.nextInt(3));
                describe.loadDefaultData(randomString(describe.Size-2));
                break;

            case 14:describe.Type = DBDefine_DataType.ByteArray;
            case 15:if (describe.Type == -1)describe.Type = DBDefine_DataType.LIST;
                describe.Size = 5 + random.nextInt(5);
                describe.ColumnName = randomString(8 + random.nextInt(3));
                byte[] tmp = new byte[describe.Size-4];
                random.nextBytes(tmp);
                describe.loadDefaultData(tmp);
                break;

            case 16:describe.Type = DBDefine_DataType.IPV6;
                describe.Size = 16;
                describe.ColumnName = randomString(8 + random.nextInt(3));
                byte[] ipv6 = new byte[describe.Size];
                random.nextBytes(ipv6);
                describe.loadDefaultData(ipv6);
                break;
                
            default:System.err.println("**********************************************************************===>");
                return null;
        }
        return describe;
    }
	
	
	public void randomAccount(int length) throws IOException {
		for(int i=0;i<length;i++)
			databaseAccount.insertAccount(randomString(10+random.nextInt(10)), (byte) random.nextInt(7), randomString(10+random.nextInt(10)), databaseUserData);
	}
	
	public String randomString(int length) {
		byte[] data=new byte[length];
		random.nextBytes(data);
		return new String(data, StandardCharsets.UTF_8);
	}
	
	@AfterEach void tearDown() throws Exception {
		databaseAccount.close();
		databaseUserData.close();
		databaseAccount.deleteFile();
		databaseUserData.deleteFile();
		System.out.println("Finish : "+(System.currentTimeMillis()-timeBegin));
	}
}