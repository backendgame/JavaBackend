package backendgame.onehit.web.table.sub;

import java.io.File;
import java.io.IOException;

import backendgame.config.CaseCheck;
import backendgame.config.PATH;
import backendgame.onehit.BaseOnehit_AiO;
import backendgame.onehit.BinaryToken;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import bgcore.core.server.BaseBackEnd_Session;
import database.BaseTableData;
import database.SubTable;

public abstract class BaseOnehit_SubTable extends BaseOnehit_AiO {

	public BaseOnehit_SubTable(short command_id) {
		super(command_id);
	}

	protected abstract MessageSending onProcessSubTable(BaseTableData databaseUserData, MessageReceiving messageReceiving) throws IOException;

	@Override public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
		BinaryToken binaryToken = new BinaryToken();
		if (binaryToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (currentTimeMillis > binaryToken.timeOut)
			return mgExpired;
		short tableId = messageReceiving.readShort();
		short subId = messageReceiving.readShort();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(tableId<0 || subId<0 || binaryToken.adminId<1)// Validate TableId và UserId
			return mgInvalid;
		if(new File(PATH.DATABASE_FOLDER+"/"+tableId).exists()==false)
			return mgNotExist;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		MessageSending mgResult;
		BaseTableData databaseUserData=null;
		try {
			databaseUserData=new BaseTableData(tableId,SubTable.getSubFile(tableId, subId)) {@Override public long countRow() throws IOException {return 0;}};
			if(System.currentTimeMillis() > databaseUserData.timeAvaiable)
				mgResult = mgExpired;
			else if(binaryToken.adminId != databaseUserData.adminId)
				mgResult = mgPlayerWrong;
			else 
				mgResult = onProcessSubTable(databaseUserData, messageReceiving);
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);//Tại vì có write thêm data
			mgResult.writeString(getStringException(e));
		}
		if (databaseUserData != null)
			databaseUserData.close();

		return mgResult;
	}
}
