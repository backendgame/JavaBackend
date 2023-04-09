package gameonline.rest.controller_user.Table.sub.tile_binary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.client.ClientOneHit;
import gameonline.config.CaseCheck;
import gameonline.rest.MyRespone;
import gameonline.rest.database.model.DataType;

public abstract class BaseClientOnehit_TileCustomQuerry extends ClientOneHit {
	private MyRespone myRespone;
	public BaseClientOnehit_TileCustomQuerry(short regionId) {
		super(regionId);
		myRespone = new MyRespone();
	}

	public MyRespone process() {
		run();
		return myRespone;
	}

	public void onNetworkError(byte errorCode) {
		myRespone.setStatus(MyRespone.STATUS_DatabaseError);
	}
	

	@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
		byte caseCheck = messageReceiving.readByte();
		if(caseCheck == CaseCheck.HOPLE) {
			ArrayNode leaf = new ObjectMapper().createArrayNode();
			
			short numberNode = messageReceiving.readShort();
			for(short i=0;i<numberNode;i++)
				switch (messageReceiving.readByte()) {
					case DataType.BOOLEAN:leaf.add(messageReceiving.readBoolean());break;
					
					case DataType.STATUS:leaf.add(messageReceiving.readByte());break;
					case DataType.PERMISSION:leaf.add(messageReceiving.readByte());break;
					case DataType.AVARTAR:leaf.add(messageReceiving.readByte());break;
					case DataType.BYTE:leaf.add(messageReceiving.readByte());break;
					
					case DataType.SHORT:leaf.add(messageReceiving.readShort());break;
					
					case DataType.INTEGER:leaf.add(messageReceiving.readInt());break;
					case DataType.FLOAT:leaf.add(messageReceiving.readFloat());break;
					
					case DataType.LONG:leaf.add(messageReceiving.readLong());break;
					case DataType.DOUBLE:leaf.add(messageReceiving.readDouble());break;
					case DataType.STRING:leaf.add(messageReceiving.readString());break;
					case DataType.TIMEMILI:leaf.add(messageReceiving.readLong());break;
					case DataType.USER_ID:leaf.add(messageReceiving.readLong());break;
					
					case DataType.BINARY:
					case DataType.LIST:
						short dataLength = messageReceiving.readShort();
						leaf.add(messageReceiving.readByteArray(dataLength));
						break;
						
					default:myRespone.setStatus(MyRespone.STATUS_Invalid).setMessage("DatabaseCode error : BBWeb_SubTable_TileCustom_Data_Querry");return;
				}
			
			myRespone.setStatus(MyRespone.STATUS_Success).setData(leaf);
		}else if(caseCheck==CaseCheck.TIMEOUT)
			myRespone.setStatus(MyRespone.STATUS_OutOfService).setMessage("Database chưa được thanh toán nên bị tạm ngưng");
		else
			myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(caseCheck));

	}

}
