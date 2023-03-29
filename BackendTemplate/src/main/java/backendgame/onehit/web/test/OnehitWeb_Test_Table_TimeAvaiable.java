package backendgame.onehit.web.test;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.config.CMD_ONEHIT;
import backendgame.config.CaseCheck;
import backendgame.config.PATH;
import backendgame.config.TIME;
import backendgame.onehit.BinaryToken;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import bgcore.core.OneHitProcessing;
import bgcore.core.server.BaseBackEnd_Session;
import database.BaseTableData;
import database.table.DBGameTable_UserData;

public class OnehitWeb_Test_Table_TimeAvaiable extends backendgame.onehit.BaseOnehit_AiO {

	public OnehitWeb_Test_Table_TimeAvaiable() {
		super(CMD_ONEHIT.BBWeb_Test_Time_Avaiable);
	}
	
	@Override
	public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
		BinaryToken richardToken = new BinaryToken();
		if (richardToken.decode(messageReceiving.readString()) == false)
			return null;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (OneHitProcessing.currentTimeMillis > richardToken.timeOut)
			return new MessageSending(cmd, CaseCheck.EXPIRED);
		short tableId = messageReceiving.readShort();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(tableId<0 || richardToken.adminId<1)// Validate TableId và UserId
			return mgInvalid;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		MessageSending mgResult;
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(PATH.DATABASE_FOLDER+"/"+tableId+"/"+PATH.UserData, "rw");
			if(rf.length() < DBGameTable_UserData.LENGTH_HEADER)
				mgResult = mgNotExist;
			else {
				// Validate
				rf.seek(BaseTableData.Offset_AdminId);
				if (rf.readLong() != richardToken.adminId) {
					mgResult = mgPlayerWrong;
				} else {
					rf.seek(0);
					rf.writeLong(System.currentTimeMillis() + TIME.Day_30);
					mgResult = mgOK;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);//Tại vì có write thêm data
			mgResult.writeString(getStringException(e));
		}
		if (rf != null)
			try {
				rf.close();
			} catch (IOException e) {
			}

		return mgResult;
	}
}
