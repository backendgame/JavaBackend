package gameonline.rest.controller_user.Table.sub.leaderboard;

import java.util.Date;
import java.util.HashMap;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.client.ClientOneHit;
import gameonline.config.CMD_ONEHIT;
import gameonline.config.CaseCheck;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;

public class Service_Leaderboard490_LoadConfig extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull @PositiveOrZero public short subTableID;
	
	@Override public MyRespone respone() {
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck == CaseCheck.HOPLE) {
					HashMap<String, Object> root = new HashMap<String, Object>();
					
					root.put(SystemConstant.NumberRow, messageReceiving.readShort());
					
					root.put(SystemConstant.AccessToken, messageReceiving.readLong());
					root.put(SystemConstant.ReadToken, messageReceiving.readLong());
					root.put(SystemConstant.WriteToken, messageReceiving.readLong());
					root.put(SystemConstant.TIMECREATE, new Date(messageReceiving.readLong()).toString());
					root.put(SystemConstant.Version, messageReceiving.readLong());
					
					root.put(SystemConstant.DATA_TYPE, messageReceiving.readByte());
					root.put(SystemConstant.ColumnName, messageReceiving.readString());
					
					myRespone.setStatus(MyRespone.STATUS_Success).setData(root);
				}else if(caseCheck==CaseCheck.TIMEOUT)
					myRespone.setStatus(MyRespone.STATUS_OutOfService).setMessage("Database chưa được thanh toán nên bị tạm ngưng");
				else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));

			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Leaderboard_LoadConfig);
				messageSending.writeString(token);
				messageSending.writeShort(tableId);
				messageSending.writeShort(subTableID);
				return messageSending;
			}
		}.run();
		return myRespone;
	}
	

}
