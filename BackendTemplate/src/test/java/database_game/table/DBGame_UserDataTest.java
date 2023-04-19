package database_game.table;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.database.DBDescribe;
import backendgame.com.database.operator.DBOperator_Byte;
import backendgame.com.database.operator.DBOperator_Short;
import server.config.PATH;

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
        describe.ColumnName = "Cột một";
        describe.loadDefaultData(true);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.BYTE;
        describe.Size = 1;
        describe.ColumnName = "Cộ Số 2";
        describe.loadDefaultData((byte)3);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.SHORT;
        describe.Size = 2;
        describe.ColumnName = "Column2";
        describe.loadDefaultData((short)12);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.FLOAT;
        describe.Size = 4;
        describe.ColumnName = "Column3";
        describe.loadDefaultData(12.34f);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.INTEGER;
        describe.Size = 4;
        describe.ColumnName = "Column4";
        describe.loadDefaultData(4567);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.DOUBLE;
        describe.Size = 8;
        describe.ColumnName = "Column5";
        describe.loadDefaultData(1234.5678d);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.LONG;
        describe.Size = 8;
        describe.ColumnName = "Column6";
        describe.loadDefaultData(9999l);
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.STRING;
        describe.ColumnName = "Column7";
        describe.loadDefaultData("Đức Trí");
        describe.fixSize();
        listRandom[count++]=describe;
        
        describe=new DBDescribe();
        describe.Type = DBDefine_DataType.ByteArray;
        describe.Size = 8;
        describe.ColumnName = "Column8";
        byte[] dataTmp = new byte[describe.Size];
        random.nextBytes(dataTmp);
        describe.loadDefaultData(dataTmp);
        describe.fixSize();
        listRandom[count++]=describe;
        
        
//        for(int i=0;i<listRandom.length;i++)
//        	listRandom[i].trace();
//        System.out.println("********************************************************************************");
        
        databaseUserData.setDescribe(listRandom);
        
        databaseUserData.des.trace();
        System.out.println("\n");
        for(int i=0;i<100;i++)
            databaseUserData.insertRow(i, random.nextLong());
        
        
        databaseUserData.writeData(15, 0, false);
//        databaseUserData.writeData(15, 1, (byte)99);
        databaseUserData.process(15, 1, DBOperator_Byte.Addition, DBDefine_DataType.BYTE, (byte)100);
        databaseUserData.writeData(15, 2, (short)32678);
        databaseUserData.writeData(15, 3, 88.99f);
        databaseUserData.writeData(15, 4, (int)1111);
        databaseUserData.writeData(15, 5, 11.22d);
        databaseUserData.writeData(15, 6, 123l);
        databaseUserData.writeData(15, 7, "backendgame");
        databaseUserData.writeData(15, 8, new byte[] {1,2,3,4,5});
        
        //////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////
        //Bắt đầu code Operators chỗ này
        databaseUserData.processShort(16, 2, DBOperator_Short.Addition, (short) 20);
        
        //////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////
        
        databaseUserData.traceTitleRow();
        for(int i=10;i<20;i++)
        	databaseUserData.traceUserId(i);
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