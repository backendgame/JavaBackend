package begame.onehit.web.dto;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;

public class DTO_Describe {
	public String ColumnName;
	public long ColumnId;
	public int ViewId;
	public byte Type;
	public int Size;
	public byte[] DefaultData;
	
	public DTO_Describe() {
	}
	
	public void writeMessage(MessageSending messageSending) {
		messageSending.writeString(ColumnName);
		messageSending.writeLong(ColumnId);
		messageSending.writeInt(ViewId);
		messageSending.writeByte(Type);
		messageSending.writeInt(Size);
		messageSending.writeByteArray(DefaultData);
	}
	
	public void readMessage(MessageReceiving messageReceiving) {
		ColumnName = messageReceiving.readString();
		ViewId=messageReceiving.readInt();
		Type=messageReceiving.readByte();
		Size=messageReceiving.readInt();
		DefaultData=messageReceiving.readByteArray();
	}
	
}
