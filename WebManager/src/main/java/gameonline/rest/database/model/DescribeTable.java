package gameonline.rest.database.model;

import richard.MessageReceiving;
import richard.MessageSending;

public class DescribeTable {//==>29
    public static final short BEGIN_OFFSET= 8;
    public static final short BEGIN_PASSWORD= 16;
    public static final short LENGTH_DESCRIBE = 21;//FieldName + Offset + Password + Type + Size → Value nằm ở UserId=0
    
    public String ColumnName;	//8
//    public long Offset;		//8 : biến local này giúp xác định vị trí của DefaultValue trên Database. Không dùng để gởi Onehit
    public short ColumnId;		//2
    public byte Type;           //1
    public short Size;          //2
    public Object DefaultValue; //8
    
    public DescribeTable() {}
    
    
    public boolean isSame(DescribeTable describeTable) {
        return ColumnId==describeTable.ColumnId && Type==describeTable.Type;
    }
    
    public void writeMessage(MessageSending messageSending) {
        messageSending.writeString(ColumnName);
        messageSending.writeshort(ColumnId);
        messageSending.writeByte(Type);
        
        switch (Type) {
            case DataType.BOOLEAN:
                messageSending.writeBoolean(Boolean.parseBoolean(DefaultValue+""));
                break;
            case DataType.BYTE:
            case DataType.STATUS:
            case DataType.PERMISSION:
            case DataType.AVARTAR:
           		messageSending.writeByte(Byte.parseByte(DefaultValue+""));
                break;
    
            case DataType.SHORT:
                messageSending.writeshort(Short.parseShort(DefaultValue+""));
                break;
                
            case DataType.INTEGER:
                messageSending.writeInt(Integer.parseInt(DefaultValue+""));
                break;
            case DataType.FLOAT:
                messageSending.writeFloat(Float.parseFloat(DefaultValue+""));
                break;
                
            case DataType.LONG:
            	messageSending.writeLong(Long.parseLong(DefaultValue+""));
                break;
            case DataType.DOUBLE:
                messageSending.writeDouble(Double.parseDouble(DefaultValue+""));
                break;
            case DataType.STRING:
                messageSending.writeString(DefaultValue.toString());
                break;
            case DataType.TIMEMILI:
            	messageSending.writeLong(Long.parseLong(DefaultValue+""));
                break;
            case DataType.USER_ID:
            	messageSending.writeLong(Long.parseLong(DefaultValue+""));
                break;
                
            case DataType.LIST:
            case DataType.BINARY:
                messageSending.writeshort(Size);
                messageSending.writeCopyData((byte[]) DefaultValue);
                break;
            default:break;
        }
    }
    
    public void readMessage(MessageReceiving messageReceiving) {
        ColumnName = messageReceiving.readString();
        ColumnId=messageReceiving.readShort();
        Type=messageReceiving.readByte();
        
        switch (Type) {
            case DataType.BOOLEAN:
                Size=1;
                DefaultValue = messageReceiving.readBoolean();
                break;
            case DataType.BYTE:
            case DataType.STATUS:
            case DataType.PERMISSION:
            case DataType.AVARTAR:
                Size=1;
                DefaultValue = messageReceiving.readByte();
                break;
    
            case DataType.SHORT:
                Size=2;
                DefaultValue = messageReceiving.readShort();
                break;
                
            case DataType.INTEGER:
                Size=4;
                DefaultValue = messageReceiving.readInt();
                break;
            case DataType.FLOAT:
                Size=4;
                DefaultValue = messageReceiving.readFloat();
                break;
                
            case DataType.LONG:
                Size=8;
                DefaultValue = messageReceiving.readLong();
                break;
            case DataType.DOUBLE:
                Size=8;
                DefaultValue = messageReceiving.readDouble();
                break;
            case DataType.STRING:
                Size=8;
                DefaultValue = messageReceiving.readString();
                break;
            case DataType.TIMEMILI:
                Size=8;
                DefaultValue = messageReceiving.readLong();
                break;
            case DataType.USER_ID:
                Size=8;
                DefaultValue = messageReceiving.readLong();
                break;
                
            case DataType.LIST:
            case DataType.BINARY:
                Size=messageReceiving.readShort();
                DefaultValue=messageReceiving.readArrayByte(Size);
                break;
            default:break;
        }
    }
    
//    public void trace(String s) {System.out.println(s+FieldName+"   "+Password+"    "+Type+"    "+Size+"    "+Offset+"  "+DefaultValue);}
    public void trace() {trace("");}
    public void trace(String s) {
//        switch (Type) {
//            case DataType.BOOLEAN:System.out.println(s + "BOOLEAN("+Size+") " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//            case DataType.BYTE:System.out.println(s + "BYTE("+Size+")   " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//            case DataType.STATUS:System.out.println(s + "STATUS("+Size+")   " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//            case DataType.PERMISSION:System.out.println(s + "PERMISSION("+Size+")   " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//            case DataType.AVARTAR:System.out.println(s + "AVARTAR("+Size+") " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//    
//            case DataType.SHORT:System.out.println(s + "SHORT("+Size+") " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//                
//            case DataType.INTEGER:System.out.println(s + "INTEGER("+Size+") " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//            case DataType.FLOAT:System.out.println(s + "FLOAT("+Size+") " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//                
//            case DataType.LONG:System.out.println(s + "LONG("+Size+")   " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//            case DataType.DOUBLE:System.out.println(s + "DOUBLE("+Size+")   " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//            case DataType.STRING:System.out.println(s + "STRING("+Size+")   " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//            case DataType.TIMEMILI:System.out.println(s + "TIMEMILI("+Size+")   " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//            case DataType.USER_ID:System.out.println(s + "USER_ID("+Size+") " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//                
//            case DataType.LIST:System.out.println(s + "LIST("+Size+")   " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//            case DataType.BINARY:System.out.println(s + "BINARY("+Size+")   " + FieldName + "   " + Password + "    " + Offset + "  " + DefaultValue);break;
//            default:break;
//        }
        switch (Type) {
        	case DataType.BOOLEAN:System.out.println(s + "BOOLEAN("+Size+") " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	case DataType.BYTE:System.out.println(s + "BYTE("+Size+")   " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	case DataType.STATUS:System.out.println(s + "STATUS("+Size+")   " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	case DataType.PERMISSION:System.out.println(s + "PERMISSION("+Size+")   " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	case DataType.AVARTAR:System.out.println(s + "AVARTAR("+Size+") " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	
        	case DataType.SHORT:System.out.println(s + "SHORT("+Size+") " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	
        	case DataType.INTEGER:System.out.println(s + "INTEGER("+Size+") " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	case DataType.FLOAT:System.out.println(s + "FLOAT("+Size+") " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	
        	case DataType.LONG:System.out.println(s + "LONG("+Size+")   " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	case DataType.DOUBLE:System.out.println(s + "DOUBLE("+Size+")   " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	case DataType.STRING:System.out.println(s + "STRING("+Size+")   " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	case DataType.TIMEMILI:System.out.println(s + "TIMEMILI("+Size+")   " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	case DataType.USER_ID:System.out.println(s + "USER_ID("+Size+") " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	
        	case DataType.LIST:System.out.println(s + "LIST("+Size+")   " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	case DataType.BINARY:System.out.println(s + "BINARY("+Size+")   " + ColumnName + "   " + ColumnId + "    " +  DefaultValue);break;
        	default:break;
        }
    }   
}