package gameonline.rest.controller_user.Table.account;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;

public class Service_ParsingRowScreen285_Update_UserData extends BaseAuthorization{

	@Override
	protected MyRespone respone() {
		// TODO Auto-generated method stub
		return null;
	}
//	@NotNull @Positive public short regionId;
//	@NotNull @Positive public short tableId;
//	@NotNull public BB_Operator[] listUpdate;
//	
//	public MyRespone respone() {
//		final MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_ParsingRow_Update_UserData);
//		messageSending.writeString(token);
//		messageSending.writeShort(tableId);
//		
//		short numberUpdate = (short) listUpdate.length;
//		messageSending.writeShort(numberUpdate);
//		for(short i=0;i<numberUpdate;i++)
//			listUpdate[i].writeMessage(messageSending);
//		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		final MyRespone myRespone=new MyRespone();
//
//		new ClientOneHit(regionId) {
//			@Override public void onReceiveMessage(MessageReceiving messageReceiving) {
//				if(messageReceiving.discoveryByte()==CaseCheck.HOPLE)
//					myRespone.setStatus(MyRespone.STATUS_Success);
//				else if(messageReceiving.discoveryByte()==CaseCheck.EXCEPTION)
//					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(messageReceiving.readByte()) + " - " + messageReceiving.readString());
//				else
//					myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage(CaseCheck.getString(messageReceiving.readByte()));
//			}
//			
//			public void onNetworkError(byte errorCode) {myRespone.setStatus(MyRespone.STATUS_DatabaseError).setMessage("ErrorCode : "+errorCode);}
//			
//			@Override public MessageSending doSendMessage() {return messageSending;}
//		}.run();
//		
//		return myRespone;
//	}
//	
//	
//	public static BB_Operator makeOperator_Set(long userId, int index, int password, Object value) {
//		BB_Operator operator = new BB_Operator();
//		operator.userId=userId;
//		operator.ColumnIndex=(short) index;
//		operator.ColumnId=(short) password;
//		operator.Operator = 1;//Set = 1
//		operator.Value = value;
//		if(value instanceof Boolean)
//			operator.Type = DataType.BOOLEAN;
//		else if(value instanceof Byte)
//			operator.Type = DataType.BYTE;
//		else if(value instanceof Short)
//			operator.Type = DataType.SHORT;
//		else if(value instanceof Integer)
//			operator.Type = DataType.INTEGER;
//		else if(value instanceof Float)
//			operator.Type = DataType.FLOAT;
//		else if(value instanceof Long)
//			operator.Type = DataType.LONG;
//		else if(value instanceof Double)
//			operator.Type = DataType.DOUBLE;
//		else if(value instanceof String)
//			operator.Type = DataType.STRING;
//		else if(value instanceof byte[])
//			operator.Type = DataType.BINARY;
//		return operator;
//	}
}
