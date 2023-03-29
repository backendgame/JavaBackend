package backendgame.onehit.web.table.account_data;

import backendgame.config.CMD_ONEHIT;
import backendgame.onehit.BaseOnehitWeb;
import backendgame.onehit.BinaryToken;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.DataType;
import database.DescribeTable;
import database.operators.BB_Operator;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;

public class OnehitWeb_ParsingRow285_Update_UserData extends BaseOnehitWeb {

	public OnehitWeb_ParsingRow285_Update_UserData() {
		super(CMD_ONEHIT.BBWeb_ParsingRow_Update_UserData);
	}

	@Override public MessageSending onProcessDatabase(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, BinaryToken richardToken, MessageReceiving messageReceiving) throws Exception {
		BB_Operator operator;
		short numberDes = databaseUserData.countNumberDescribeTables();
		
		short numberProcess = messageReceiving.readShort();
		BB_Operator[] listOperators=new BB_Operator[numberProcess];
		for(short i=0;i<numberProcess;i++) {
			operator=new BB_Operator();
			operator.readMessageOperator(messageReceiving);
			listOperators[i]=operator;
			
			if(operator.validate()==false || operator.index>numberDes)
				return mgInvalid;
			
			
		}
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////////////////////////////////////////////
		long offset;
		DescribeTable[] listCache = new DescribeTable[numberDes];
		for(short i=0;i<numberProcess;i++) 
			if(listOperators[i].userId<databaseUserData.countRow()){//Chỉ xử lý userId đã tạo
				operator=listOperators[i];
				
				if(listCache[operator.index]==null)
					listCache[operator.index] = databaseUserData.get1DescribeTable(operator.index, dbString);
				
				offset = operator.verifyToGetOffset(listCache[operator.index]);
				if(offset<0) {
					return mgVariableInvalid;
				}else
					offset = DBGameTable_UserData.LENGTH_HEADER + operator.userId*(databaseUserData.dataLength+9) + 1 + offset;
				
				switch (operator.Type) {
					case DataType.BOOLEAN:operatorBoolean.process(databaseUserData.rf, offset, operator);break;
					case DataType.BYTE:
					case DataType.STATUS:
					case DataType.PERMISSION:
					case DataType.AVARTAR:operatorByte.process(databaseUserData.rf, offset, operator);break;
			
					case DataType.SHORT:operatorShort.process(databaseUserData.rf, offset, operator);break;
						
					case DataType.INTEGER:operatorInteger.process(databaseUserData.rf, offset, operator);break;
					case DataType.FLOAT:operatorFloat.process(databaseUserData.rf, offset, operator);break;
					
					case DataType.USER_ID:
					case DataType.TIMEMILI:
					case DataType.LONG:operatorLong.process(databaseUserData.rf, offset, operator);break;
					case DataType.DOUBLE:operatorDouble.process(databaseUserData.rf, offset, operator);break;
					
					case DataType.STRING:operatorsString.process(databaseUserData.rf, offset, operator, dbString);break;
						
					case DataType.LIST:operatorsList.process(null, offset, operator);break;
					case DataType.BINARY:operatorsBinary.process(null, offset, operator);break;
					default:break;
				}
			}
		return mgOK;
	}

	
	
}
