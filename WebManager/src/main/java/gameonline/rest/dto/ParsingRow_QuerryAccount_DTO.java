package gameonline.rest.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import backendgame.com.core.MessageReceiving;
import gameonline.rest.database.model.DataType;
import gameonline.rest.database.model.DescribeTable;

public class ParsingRow_QuerryAccount_DTO {
	public DescribeTable[] listDescribeTables;
	public ArrayNode listUserId;
	
	public ParsingRow_QuerryAccount_DTO() {}
	public ParsingRow_QuerryAccount_DTO(MessageReceiving messageReceiving) {
		short numberDes = messageReceiving.readShort();
		listDescribeTables=new DescribeTable[numberDes];
		for(short i=0;i<numberDes;i++) {
			listDescribeTables[i]=new DescribeTable();
			listDescribeTables[i].readMessage(messageReceiving);
//			listDescribeTables[i].trace("");
		}
		
		listUserId = new ObjectMapper().createArrayNode();
		short numberUserId = messageReceiving.readShort();
		for(short i=0;i<numberUserId;i++)
			listUserId.add(read1User(messageReceiving));
	}
	
	private ArrayNode read1User(MessageReceiving messageReceiving){
		ArrayNode leaf = new ObjectMapper().createArrayNode();
		leaf.add(messageReceiving.readString());//Credential
		leaf.add(messageReceiving.readByte());//Databaseid
//		leaf.add(new DateTime(messageReceiving.readLong()).toString());//TimeCreate
		leaf.add(messageReceiving.readLong());//TimeCreate
		leaf.add(messageReceiving.readLong());//UserId
		leaf.add(messageReceiving.readByte());//Permission
		
		for(short i=0;i<listDescribeTables.length;i++) {
			switch (listDescribeTables[i].Type) {//TYPE
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
					short arrLength = messageReceiving.readShort();
					leaf.add(messageReceiving.readByteArray(arrLength));
					break;
				case DataType.LIST:
					short arrLength2 = messageReceiving.readShort();
					leaf.add(messageReceiving.readByteArray(arrLength2));
					break;

				default:System.err.println("DataType not Exist");break;
			}
		}
//		System.out.println(leaf.toString());
		return leaf;
	}
}
