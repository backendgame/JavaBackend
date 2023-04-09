package gameonline.rest.controller_user.Table.sub.leaderboard;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.client.ClientOneHit;
import gameonline.config.CMD_ONEHIT;
import gameonline.config.CaseCheck;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.database.model.DataType;

public class Service_Leaderboard460_UpdateData extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull @PositiveOrZero public short subTableID;
	
	@NotNull @PositiveOrZero public short RowIndex;
	@JsonProperty("DataType") public byte Data_Type;
	@NotNull @Positive public long UserId;
	@NotNull public Object value;
	
	@Override public MyRespone respone() {
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck == CaseCheck.HOPLE) {
//					short leaderboardId = messageReceiving.readShort();
//					System.err.println("Tạo thành công leaderboardId("+leaderboardId+")");
					myRespone.setStatus(MyRespone.STATUS_Success);
				}else if(caseCheck==CaseCheck.TIMEOUT)
					myRespone.setStatus(MyRespone.STATUS_OutOfService).setMessage("Database chưa được thanh toán nên bị tạm ngưng");
				else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));

			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Leaderboard_UpdateData);
				messageSending.writeString(token);
				messageSending.writeShort(tableId);
				messageSending.writeShort(subTableID);
				
				messageSending.writeByte(Data_Type);
				messageSending.writeShort((short) 1);
				messageSending.writeShort(RowIndex);
				messageSending.writeLong(UserId);
				switch (Data_Type) {
					case DataType.BYTE:messageSending.writeByte(Byte.parseByte(value+""));break;
					case DataType.SHORT:messageSending.writeShort(Short.parseShort(value+""));break;
					case DataType.INTEGER:messageSending.writeInt(Integer.parseInt(value+""));break;
					case DataType.LONG:messageSending.writeLong(Long.parseLong(value+""));break;
					case DataType.FLOAT:messageSending.writeFloat(Float.parseFloat(value+""));break;
					case DataType.DOUBLE:messageSending.writeDouble(Double.parseDouble(value+""));break;
					default:break;
				}
				return messageSending;
			}
		}.run();
		return myRespone;
	}
	

}
