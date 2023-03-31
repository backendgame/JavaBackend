package backendgame.onehit.web.table.sub.leaderboard;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import database.BaseTableData;
import database.DataType;
import database.table.DBString;
import database.table.sub.DBGameTable_Leaderboard;

public class OnehitWeb_Leaderboard400_Config extends BaseOnehit_Leaderboard {

	public OnehitWeb_Leaderboard400_Config() {super(CMD_ONEHIT.BBWeb_Leaderboard_Config);}

	@Override
	public MessageSending onProcess(DBGameTable_Leaderboard leaderboard, MessageReceiving messageReceiving, DBString dbString) throws Exception {
		short numberRow=messageReceiving.readShort();
		byte dataType = messageReceiving.readByte();
		String columnName = messageReceiving.readString();
		////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgInvalid;
		
		byte size = 0;
		switch (dataType) {
			case DataType.BYTE:		size=1;break;
			case DataType.SHORT:	size=2;break;
			case DataType.INTEGER:	size=4;break;
			case DataType.LONG:		size=8;break;
			
			case DataType.FLOAT:	size=4;break;
			case DataType.DOUBLE:	size=8;break;
			default:return mgVariableInvalid;
		}
		////////////////////////////////////////////////////////
		if(leaderboard.dataType==dataType) {
			leaderboard.rf.seek(DBGameTable_Leaderboard.OFFSET_ColumnName);
			leaderboard.rf.writeLong(dbString.getStringId(columnName));
		}else {//Renew
			leaderboard.rf.seek(DBGameTable_Leaderboard.OFFSET_DataType);
			leaderboard.rf.writeByte(dataType);
			leaderboard.rf.seek(DBGameTable_Leaderboard.OFFSET_ColumnName);
			leaderboard.rf.writeLong(dbString.getStringId(columnName));
			leaderboard.rf.writeShort(0);//70 IndexMin
			
			leaderboard.rf.setLength(DBGameTable_Leaderboard.OFFSET_StartLeaderboard);
		}
		leaderboard.rf.seek(BaseTableData.Offset_DataLength);
		leaderboard.rf.writeShort(numberRow);
		
		leaderboard.rf.setLength(DBGameTable_Leaderboard.OFFSET_StartLeaderboard+numberRow*(8 + size));//LengthFile = SubTable.HEADER_LENGTH + 1b(SubType) + numberTop*(userId+Value);
		
		leaderboard.rf.seek(BaseTableData.Offset_Version);
		leaderboard.rf.writeLong(currentTimeMillis);
		return mgOK;
	}



}
