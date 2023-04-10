package database_game.table;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import backendgame.com.database.entity.DBDefine_DataType;
import backendgame.com.database.entity.DBDescribe;
import begame.config.PATH;

class DBGame_UserDataTest {
    private long timeBegin;
    private short tableId=2002;
    public DBGame_UserData databaseUserData;
    public Random random;

    
    
    @Test void test() throws IOException {
        timeBegin=System.currentTimeMillis();
        if(new File(PATH.DATABASE_FOLDER+"/"+tableId).exists()==false)
            new File(PATH.DATABASE_FOLDER+"/"+tableId).mkdirs();
        random=new Random();
        databaseUserData=new DBGame_UserData(tableId);
        
        int count=0;
        DBDescribe describe;
        DBDescribe[] listRandom = new DBDescribe[9];
        /////////////////////////////////////////////////////////////////////////////////////////
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.BOOLEAN;
        describe.Size = 1;
        describe.DefaultData = new byte[describe.Size];
        describe.ColumnName = randomString(8 + random.nextInt(3));
        random.nextBytes(describe.DefaultData);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.BYTE;
        describe.Size = 1;
        describe.DefaultData = new byte[describe.Size];
        describe.ColumnName = randomString(8 + random.nextInt(3));
        random.nextBytes(describe.DefaultData);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.SHORT;
        describe.Size = 1;
        describe.DefaultData = new byte[describe.Size];
        describe.ColumnName = randomString(8 + random.nextInt(3));
        random.nextBytes(describe.DefaultData);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.FLOAT;
        describe.Size = 1;
        describe.DefaultData = new byte[describe.Size];
        describe.ColumnName = randomString(8 + random.nextInt(3));
        random.nextBytes(describe.DefaultData);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.INTEGER;
        describe.Size = 1;
        describe.DefaultData = new byte[describe.Size];
        describe.ColumnName = randomString(8 + random.nextInt(3));
        random.nextBytes(describe.DefaultData);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.DOUBLE;
        describe.Size = 1;
        describe.DefaultData = new byte[describe.Size];
        describe.ColumnName = randomString(8 + random.nextInt(3));
        random.nextBytes(describe.DefaultData);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.LONG;
        describe.Size = 1;
        describe.DefaultData = new byte[describe.Size];
        describe.ColumnName = randomString(8 + random.nextInt(3));
        random.nextBytes(describe.DefaultData);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.STRING;
        describe.Size = 1;
        describe.DefaultData = new byte[describe.Size];
        describe.ColumnName = randomString(8 + random.nextInt(3));
        random.nextBytes(describe.DefaultData);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.ByteArray;
        describe.Size = 1;
        describe.DefaultData = new byte[describe.Size];
        describe.ColumnName = randomString(8 + random.nextInt(3));
        random.nextBytes(describe.DefaultData);
        listRandom[count++]=describe;
        
        databaseUserData.setDescribe(listRandom);
   
        databaseUserData.traceDescribe();
    }
    
    public DBDescribe randomDescribe() {
        DBDescribe describe = new DBDescribe();
        describe.Type = -1;
        switch (random.nextInt(16)) {
            case 0:describe.Type = DBDefine_DataType.BOOLEAN;
                describe.Size = 1;
                describe.DefaultData = new byte[describe.Size];
                describe.ColumnName = randomString(8 + random.nextInt(3));
                random.nextBytes(describe.DefaultData);
                break;

            case 1:describe.Type = DBDefine_DataType.BYTE;
            case 2:if (describe.Type == -1)describe.Type = DBDefine_DataType.STATUS;
            case 3:if (describe.Type == -1)describe.Type = DBDefine_DataType.PERMISSION;
            case 4:if (describe.Type == -1)describe.Type = DBDefine_DataType.AVARTAR;
                describe.Size = 1;
                describe.DefaultData = new byte[describe.Size];
                describe.ColumnName = randomString(8 + random.nextInt(3));
                random.nextBytes(describe.DefaultData);
                break;

            case 5:describe.Type = DBDefine_DataType.SHORT;
                describe.Size = 2;
                describe.DefaultData = new byte[describe.Size];
                describe.ColumnName = randomString(8 + random.nextInt(3));
                random.nextBytes(describe.DefaultData);
                break;

            case 6:describe.Type = DBDefine_DataType.FLOAT;
                describe.Size = 4;
                describe.DefaultData = new byte[describe.Size];
                describe.ColumnName = randomString(8 + random.nextInt(3));
                random.nextBytes(describe.DefaultData);
                break;

            case 7:describe.Type = DBDefine_DataType.IPV4;
            case DBDefine_DataType.INTEGER:if (describe.Type == -1)describe.Type = DBDefine_DataType.INTEGER;
                describe.Size = 4;
                describe.DefaultData = new byte[describe.Size];
                describe.ColumnName = randomString(8 + random.nextInt(3));
                random.nextBytes(describe.DefaultData);
                break;

            case 8:describe.Type = DBDefine_DataType.DOUBLE;
                describe.Size = 8;
                describe.DefaultData = new byte[describe.Size];
                describe.ColumnName = randomString(8 + random.nextInt(3));
                random.nextBytes(describe.DefaultData);
                break;

            case 9:describe.Type = DBDefine_DataType.USER_ID;
            case 10:if (describe.Type == -1)describe.Type = DBDefine_DataType.TIMEMILI;
            case 11:if (describe.Type == -1)describe.Type = DBDefine_DataType.LONG;
                describe.Size = 8;
                describe.DefaultData = new byte[describe.Size];
                describe.ColumnName = randomString(8 + random.nextInt(3));
                random.nextBytes(describe.DefaultData);
                break;

            case 12:describe.Type = DBDefine_DataType.STRING;
                describe.Size = 5 + random.nextInt(5);
                describe.DefaultData = new byte[describe.Size];
                describe.ColumnName = randomString(8 + random.nextInt(3));
                random.nextBytes(describe.DefaultData);
                break;

            case 13:describe.Type = DBDefine_DataType.ByteArray;
            case 14:if (describe.Type == -1)describe.Type = DBDefine_DataType.LIST;
                describe.Size = 5 + random.nextInt(5);
                describe.DefaultData = new byte[describe.Size];
                describe.ColumnName = randomString(8 + random.nextInt(3));
                random.nextBytes(describe.DefaultData);
                break;

            case 15:describe.Type = DBDefine_DataType.IPV6;
                describe.Size = 16;
                describe.DefaultData = new byte[describe.Size];
                describe.ColumnName = randomString(8 + random.nextInt(3));
                random.nextBytes(describe.DefaultData);
                break;
            default:System.err.println("**********************************************************************===>");
                return null;
        }
        return describe;
    }
    
    
    public String randomString(int length) {
        byte[] data=new byte[length];
        random.nextBytes(data);
        return new String(data, StandardCharsets.UTF_8);
    }
    
    @AfterEach void tearDown() throws Exception {
        databaseUserData.close();
        databaseUserData.deleteFile();
        System.out.println("Finish : "+(System.currentTimeMillis()-timeBegin));
    }
}