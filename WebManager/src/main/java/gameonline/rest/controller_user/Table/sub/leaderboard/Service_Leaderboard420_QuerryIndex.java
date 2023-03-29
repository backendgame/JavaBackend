package gameonline.rest.controller_user.Table.sub.leaderboard;

import java.util.HashMap;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;
import gameonline.rest.database.model.DataType;
import richard.CMD_ONEHIT;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;

public class Service_Leaderboard420_QuerryIndex extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	@NotNull @PositiveOrZero public short subTableID;
	
//	@NotNull @Positive public short numberTop;
//	@NotNull @Positive public byte dataType;
	
	@Override protected MyRespone respone() {
		final MyRespone myRespone = new MyRespone();
		new ClientOneHit(regionId) {
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError);}
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				if(caseCheck == CaseCheck.HOPLE) {
					HashMap<String, Object> map = new HashMap<>();
					HashMap<Long, Object> mapValue = new HashMap<>();
					
					short numberTop=messageReceiving.readShort();
					byte dataType = messageReceiving.readByte();
					String columnName = messageReceiving.readString();
					
					map.put(SystemConstant.DATA_TYPE, dataType);
					map.put(SystemConstant.ColumnName, columnName);
					map.put(SystemConstant.NumberRow, numberTop);
					map.put(SystemConstant.DATA, mapValue);
					
					switch (dataType) {
						case DataType.BYTE:
							for (short i = 0; i < numberTop; i++)
								mapValue.put(messageReceiving.readLong(), messageReceiving.readByte());
							break;
						case DataType.SHORT:
							for(short i=0;i<numberTop;i++)
								mapValue.put(messageReceiving.readLong(), messageReceiving.readShort());
							break;
						case DataType.INTEGER:
							for(short i=0;i<numberTop;i++)
								mapValue.put(messageReceiving.readLong(), messageReceiving.readInt());
							break;
						case DataType.LONG:
							for(short i=0;i<numberTop;i++)
								mapValue.put(messageReceiving.readLong(), messageReceiving.readLong());
							break;
						case DataType.FLOAT:
							for(short i=0;i<numberTop;i++)
								mapValue.put(messageReceiving.readLong(), messageReceiving.readFloat());
							break;
						case DataType.DOUBLE:
							for(short i=0;i<numberTop;i++)
								mapValue.put(messageReceiving.readLong(), messageReceiving.readDouble());
							break;
						default:
							break;
					}
					
					myRespone.setStatus(MyRespone.STATUS_Success).setData(map);
				}else
					myRespone.setStatus(MyRespone.STATUS_InternalServerError).setMessage(CaseCheck.getString(caseCheck));
			}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Leaderboard_Querry_Id);
				messageSending.writeString(token);
				messageSending.writeshort(tableId);
				messageSending.writeshort(subTableID);
				
				return messageSending;
			}
		}.run();
		return myRespone;
	}
}

//	@NotNull public long[] listIndex;
//	
//	public MyRespone respone() {
//		short numberIndex = (short) listIndex.length;
//		
//		MessageSending messageSending = makeMessage_With_DataType(CMD_ONEHIT.BBWeb_Leaderboard_Querry_Id);
//		messageSending.writeshort(numberIndex);
//		for(short i=0;i<numberIndex;i++)
//			messageSending.writeLong(listIndex[i]);
//		return onOnehitFull_AccountUserData(messageSending);
//	}
//}
