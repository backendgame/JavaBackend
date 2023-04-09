package gameonline.rest.controller_user.Table.sub.tile_binary;

import java.util.Base64;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.client.ClientOneHit;
import gameonline.config.CMD_ONEHIT;
import gameonline.config.CaseCheck;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.database.model.DataType;

public class Service_SubTableScreen47_List_DataUpdate extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull @Positive public short subTableID;
	
	@NotNull @Positive public long nodeIndex;
	@NotNull @Positive public byte dataType;
	@NotNull @Positive public short size;
	@NotEmpty String value;
	
	@Override public MyRespone respone() {
		final MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTileBinary_Data_Update);
		messageSending.writeString(token);
		messageSending.writeShort(tableId);
		messageSending.writeShort(subTableID);
		
		messageSending.writeLong(nodeIndex);
		messageSending.writeByte(dataType);
		switch (dataType) {
			case DataType.BOOLEAN:
			case DataType.BYTE:
			case DataType.STATUS:
			case DataType.PERMISSION:
			case DataType.AVARTAR:
				messageSending.writeByte(Byte.parseByte(value));
				break;

			case DataType.SHORT:
				messageSending.writeShort(Short.parseShort(value));
				break;
				
			case DataType.INTEGER:
				messageSending.writeInt(Integer.parseInt(value));
				break;
			case DataType.FLOAT:
				messageSending.writeFloat(Float.parseFloat(value));
				break;
				
			case DataType.LONG:
				messageSending.writeLong(Long.parseLong(value));
				break;
			case DataType.DOUBLE:
				messageSending.writeDouble(Double.parseDouble(value));
				break;
			case DataType.STRING:
				messageSending.writeString(value);
				break;
			case DataType.TIMEMILI:
				messageSending.writeLong(System.currentTimeMillis() + Long.parseLong(value));
				break;
			case DataType.USER_ID:
				messageSending.writeLong(Long.parseLong(value));
				break;
				
			case DataType.LIST:
			case DataType.BINARY:
				messageSending.writeShort(size);
				messageSending.writeSpecialArray_WithoutLength(Base64.getDecoder().decode(value));
				break;
			default:return resInvalid;
		}
		
		
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck == CaseCheck.HOPLE) {
					myRespone.setStatus(MyRespone.STATUS_Success);
				}else if(caseCheck==CaseCheck.TIMEOUT)
					myRespone.setStatus(MyRespone.STATUS_OutOfService).setMessage("Database chưa được thanh toán nên bị tạm ngưng");
				else
					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));

			}
			
			@Override public MessageSending doSendMessage() {
				return messageSending;
			}
		}.run();
		return myRespone;
	}

}
