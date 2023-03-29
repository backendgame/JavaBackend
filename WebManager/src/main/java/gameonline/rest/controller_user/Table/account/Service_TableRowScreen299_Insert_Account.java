package gameonline.rest.controller_user.Table.account;

import java.util.HashMap;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.BinaryToken;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;
import gameonline.rest.database.model.DataType;
import gameonline.rest.database.model.DatabaseId;
import gameonline.rest.database.model.DescribeTable;
import richard.CMD_ONEHIT;
import richard.CaseCheck;
import richard.ClientOneHit;
import richard.MessageReceiving;
import richard.MessageSending;
import richard.TimeManager;

public class Service_TableRowScreen299_Insert_Account extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;

	@NotNull @PositiveOrZero public short databaseId;
	@NotNull public String credential;
	public String password;
	
	public MyRespone respone() {
		
//		byte databaseId = messageReceiving.readByte();
//		String credential = messageReceiving.readString();
//		long passwordLocation = getPasswordLocation(databaseId, databaseAccount.tableId, messageReceiving);
		
		
		final MyRespone myRespone=new MyRespone();

		new ClientOneHit(regionId) {
			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
				byte caseCheck = messageReceiving.readByte();
				myRespone.setMessage(CaseCheck.getString(caseCheck));
				switch (caseCheck) {
					case CaseCheck.HOPLE:
						long userId = messageReceiving.readLong();
						BinaryToken binTok = new BinaryToken();
						binTok.userId = userId;
						binTok.timeOut = System.currentTimeMillis() + TimeManager.A_DAY_MILLISECOND;
						
						HashMap<String, Object> map = new HashMap<>();
						map.put(SystemConstant.TOKEN,binTok.toHashString());
						map.put(SystemConstant.USERID,userId);
						
						short numberDes = messageReceiving.readShort();
						DescribeTable[] listDescribeTables=new DescribeTable[numberDes];
						for(short i=0;i<numberDes;i++) {
							listDescribeTables[i]=new DescribeTable();
							listDescribeTables[i].readMessage(messageReceiving);
						}
						
						map.put(SystemConstant.CREDENTIAL, messageReceiving.readString());
						map.put(SystemConstant.DATABASEID, messageReceiving.readByte());//Không write password
						map.put(SystemConstant.TIMECREATE, TimeManager.gI().getStringTime(messageReceiving.readLong()));
						map.put(SystemConstant.USERID,messageReceiving.readLong());
						map.put(SystemConstant.PERMISSION,messageReceiving.readByte());
						
						HashMap<String, Object> mapData = new HashMap<>();
						for(short i=0;i<numberDes;i++)
							switch (listDescribeTables[i].Type) {//TYPE
								case DataType.BOOLEAN:
									mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readBoolean());
									break;
								
								case DataType.STATUS:
								case DataType.PERMISSION:
								case DataType.AVARTAR:
								case DataType.BYTE:
									mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readByte());
									break;
								
								case DataType.SHORT:mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readShort());break;
								
								case DataType.INTEGER:mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readInt());break;
								case DataType.FLOAT:mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readFloat());break;
								
								case DataType.LONG:mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readLong());break;
								case DataType.DOUBLE:mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readDouble());break;
								case DataType.STRING:mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readString());break;
								case DataType.TIMEMILI:mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readLong());break;
								case DataType.USER_ID:mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readLong());break;
								
								case DataType.BINARY:
									short arrLength = messageReceiving.readShort();
									mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readArrayByte(arrLength));
									break;
								case DataType.LIST:
									short arrLength2 = messageReceiving.readShort();
									mapData.put(listDescribeTables[i].ColumnName, messageReceiving.readArrayByte(arrLength2));
									break;

								default:System.err.println("DataType not Exist");break;
							}
						
						map.put(SystemConstant.DATA_Value, mapData);
						myRespone.setStatus(MyRespone.STATUS_Success).setData(map);
						break;
					case CaseCheck.EXIST:
						ObjectNode nodeExist= new ObjectMapper().createObjectNode();
						nodeExist.put(SystemConstant.TOKEN,messageReceiving.readString());
						nodeExist.put(SystemConstant.USERID,messageReceiving.readLong());
						nodeExist.put("WaitForUpdate","WaitForUpdate");

						myRespone.setStatus(MyRespone.STATUS_Existed).setData(nodeExist);
						break;

					default:
						myRespone.setStatus(MyRespone.STATUS_DatabaseError);
						break;
				}
			}
			
			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage("ErrorCode : "+errorCode);}
			
			@Override public MessageSending doSendMessage() {
				MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_Row_Insert_Account);
				messageSending.writeString(token);
				messageSending.writeshort(tableId);
				
				messageSending.writeByte((byte) databaseId);
				messageSending.writeString(credential);
				if(databaseId==DatabaseId.SystemAccount || databaseId==DatabaseId.EmailCode)
					messageSending.writeString(password);
				return messageSending;
			}
		}.run();
		return myRespone;
	}
}
