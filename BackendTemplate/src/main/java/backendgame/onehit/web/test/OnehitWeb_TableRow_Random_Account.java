package backendgame.onehit.web.test;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.amazonaws.util.StringUtils;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import backendgame.onehit.BaseOnehitWeb;
import backendgame.onehit.BinaryToken;
import database.DatabaseId;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;

public class OnehitWeb_TableRow_Random_Account extends BaseOnehitWeb {

	public OnehitWeb_TableRow_Random_Account() {
		super(CMD_ONEHIT.BBWeb_Row_Random_Account);
	}


	private void randomAcountWithoutPassword(byte databaseId,DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData) throws IOException {
		String credential = RandomStringUtils.randomAlphabetic(20);
		while(StringUtils.isNullOrEmpty(credential) || databaseAccount.getOffset(credential, databaseId)!=-1)
			credential = RandomStringUtils.randomAlphabetic(32);
		databaseAccount.insertRow(credential, databaseId, 0, databaseUserData);
	}

	private void randomAccountWithPassword(byte databaseId, long passwordOffset,DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData) throws IOException {
		String credential = RandomStringUtils.randomAlphabetic(20);
		while(StringUtils.isNullOrEmpty(credential) || databaseAccount.getOffset(credential, databaseId)!=-1)
			credential = RandomStringUtils.randomAlphabetic(32);
		databaseAccount.insertRow(credential, databaseId, passwordOffset, databaseUserData);
	}
	
	@Override public MessageSending onProcessDatabase(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, BinaryToken richardToken, MessageReceiving messageReceiving) throws Exception {
		short numberAccount = messageReceiving.readShort();
		byte[] listAccount = messageReceiving.readByteArray(7);
		String passwordDefault = messageReceiving.readString();
		/////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgInvalid;
		
		boolean flagValid = false;
		for(int i=0;i<7;i++)
			if(listAccount[i]!=0) {
				flagValid=true;
				break;
			}
		if(flagValid==false)
			return mgVariableInvalid;
		/////////////////////////////////////////////////////////////////////////
		long passwordOffset = dbString.getStringId(passwordDefault);
		byte databaseId;
		short count=0;
		try {
			while(count++!=numberAccount) {
				databaseId=(byte) random.nextInt(7);
				while(listAccount[databaseId]==0)
					databaseId=(byte) random.nextInt(7);
				switch (databaseId) {
					case DatabaseId.Device:
					case DatabaseId.Facebook:
					case DatabaseId.Google:
					case DatabaseId.AdsId:
					case DatabaseId.Apple:randomAcountWithoutPassword(databaseId,databaseAccount,databaseUserData);break;
					case DatabaseId.SystemAccount:
					case DatabaseId.EmailCode:randomAccountWithPassword(databaseId, passwordOffset,databaseAccount,databaseUserData);break;
					default:break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mgOK;
	}

	
}
