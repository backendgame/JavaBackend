package gameonline.rest.controller_user.Table.account;

import javax.validation.constraints.NotNull;

import gameonline.rest.MyRespone;
import richard.CMD_ONEHIT;
import richard.MessageSending;

public class Service_ParsingRowScreen251_Querry_By_ListUserId extends BaseService_ParsingRow{
	@NotNull public long[] listUserId;
	
	public MyRespone respone() {
		short numberIndex = (short) listUserId.length;
		
		MessageSending messageSending=new MessageSending(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_ListUserId);
		messageSending.writeString(token);
		messageSending.writeshort(tableId);
		messageSending.writeshort(numberIndex);
		for(short i=0;i<numberIndex;i++)
			messageSending.writeLong(listUserId[i]);
		return onOnehitFull_AccountUserData(messageSending);
	}
}
