package gameonline.rest.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import gameonline.rest.database.model.DataType;
import gameonline.rest.database.model.DescribeTable;
import richard.MessageReceiving;

public class Leaderboard_FulQuerryDTO {
	public short NumberTop;
	@JsonProperty("DataType") public byte Data_Type;
	public String ColumnName;
	public DescribeTable[] listDescribeTables;
	public ArrayList<ArrayList<Object>> listUserId;
	
	public Leaderboard_FulQuerryDTO(MessageReceiving messageReceiving) {
		NumberTop = messageReceiving.readShort();
		Data_Type = messageReceiving.readByte();
		ColumnName = messageReceiving.readString();
		
		short numberDes = messageReceiving.readShort();
		listDescribeTables=new DescribeTable[numberDes];
		for(short i=0;i<numberDes;i++) {
			listDescribeTables[i]=new DescribeTable();
			listDescribeTables[i].readMessage(messageReceiving);
//			listDescribeTables[i].trace("");
		}
		
		ArrayList<Object> leaf;
		listUserId = new ArrayList<>();
		short numberUserId = messageReceiving.readShort();
		for(short i=0;i<numberUserId;i++) {
			leaf=read1User(messageReceiving);
			if(messageReceiving.readLong()==0)
				listUserId.add(null);
			else
				listUserId.add(leaf);
		}
	}
	
	public Object readValue(MessageReceiving messageReceiving) {
        switch (Data_Type) {
            case DataType.BYTE:     return messageReceiving.readByte();
            case DataType.SHORT:    return messageReceiving.readShort();
            case DataType.INTEGER:  return messageReceiving.readInt();
            case DataType.LONG:     return messageReceiving.readLong();
            
            case DataType.FLOAT:    return messageReceiving.readFloat();
            case DataType.DOUBLE:   return messageReceiving.readDouble();
			default:System.err.println("DataType error : " + Data_Type);return null;
        }
	}
	
	private ArrayList<Object> read1User(MessageReceiving messageReceiving){
		ArrayList<Object> leaf = new ArrayList<Object>();
		leaf.add(messageReceiving.readString());//Credential
		leaf.add(messageReceiving.readByte());//Databaseid
//		leaf.add(new DateTime(messageReceiving.readLong()).toString());//TimeCreate
		leaf.add(messageReceiving.readLong());//TimeCreate
		leaf.add(messageReceiving.readLong());//UserId
		
		leaf.add(readValue(messageReceiving));
		System.out.println("Đọc thêm 1 biến chỗ này : Leaderboard_FulQuerryDTO row : 65");
		
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
					leaf.add(messageReceiving.readArrayByte(arrLength));
					break;
				case DataType.LIST:
					short arrLength2 = messageReceiving.readShort();
					leaf.add(messageReceiving.readArrayByte(arrLength2));
					break;

				default:System.err.println("DataType not Exist");break;
			}
		}
		
//		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(leaf));
		
		return leaf;
	}
}
