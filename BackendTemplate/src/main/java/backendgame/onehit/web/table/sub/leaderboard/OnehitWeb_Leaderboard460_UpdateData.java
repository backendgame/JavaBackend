package backendgame.onehit.web.table.sub.leaderboard;

import java.io.IOException;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.DataType;
import database.table.DBString;
import database.table.sub.DBGameTable_Leaderboard;

public class OnehitWeb_Leaderboard460_UpdateData extends BaseOnehit_Leaderboard {

	public OnehitWeb_Leaderboard460_UpdateData() {super(CMD_ONEHIT.BBWeb_Leaderboard_UpdateData);}

	@Override public MessageSending onProcess(DBGameTable_Leaderboard leaderboard, MessageReceiving messageReceiving, DBString dbString) throws Exception {
		byte dataType = messageReceiving.readByte();
		short numberRow = messageReceiving.readShort();

		short[] listIndex = new short[numberRow];long[] listUserId = new long[numberRow];Object[] listValue = new Object[numberRow];
		for(short i=0;i<numberRow;i++) {
			listIndex[i]=messageReceiving.readShort();
			listUserId[i]=messageReceiving.readLong();
			
			switch (dataType) {
				case DataType.BYTE:		listValue[i]=messageReceiving.readByte();break;
				case DataType.SHORT:	listValue[i]=messageReceiving.readShort();break;
				case DataType.INTEGER:	listValue[i]=messageReceiving.readInt();break;
				case DataType.LONG:		listValue[i]=messageReceiving.readLong();break;
				
				case DataType.FLOAT:	listValue[i]=messageReceiving.readFloat();break;
				case DataType.DOUBLE:	listValue[i]=messageReceiving.readDouble();break;
				default:throw new IOException("DataType error : "+dataType);
			}
		}
		////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgInvalid;
		
		if(dataType!=leaderboard.dataType)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////
		for(short i=0;i<numberRow;i++)
			leaderboard.update(listIndex[i], listUserId[i], listValue[i]);
		return mgOK;
	}



}
