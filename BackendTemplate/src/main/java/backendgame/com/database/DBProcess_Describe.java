package backendgame.com.database;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.database.entity.DB_ReadDatabase;
import backendgame.com.database.entity.DB_WriteDatabase;
import backendgame.com.database.operator.DBOperator_Boolean;
import backendgame.com.database.operator.DBOperator_Byte;
import backendgame.com.database.operator.DBOperator_ByteArrays;
import backendgame.com.database.operator.DBOperator_Double;
import backendgame.com.database.operator.DBOperator_Float;
import backendgame.com.database.operator.DBOperator_IPV6;
import backendgame.com.database.operator.DBOperator_Integer;
import backendgame.com.database.operator.DBOperator_List;
import backendgame.com.database.operator.DBOperator_Long;
import backendgame.com.database.operator.DBOperator_Short;
import backendgame.com.database.operator.DBOperator_String;



/*
 * 
 * Describes nằm riêng với dataDefault vì:
 * 1/ Dễ clone DataDefault
 * 2/ Dễ tìm kiếm và update Describe
 * 
 * */
public class DBProcess_Describe {
	public RandomAccessFile rfData;
	protected long offsetDescribe;
	
	public DBProcess_Describe(RandomAccessFile _rfData,long _offsetDescribe) {
		rfData=_rfData;
		offsetDescribe=_offsetDescribe;
	}
	

	public DBDescribe readDescribeByIndex(int index) throws IOException {
		DBDescribe describe=new DBDescribe();
		
		rfData.seek(offsetDescribe + 25 + index*100);
		describe.OffsetRow = rfData.readInt();
		describe.ViewId = rfData.readInt();
		describe.State = rfData.readByte();
		describe.Permission = rfData.readByte();
		describe.Size = rfData.readInt();
		describe.Type = rfData.readByte();
		describe.ColumnName = rfData.readUTF();
		
		if(describe.Size>0) {
			describe.DefaultData=new byte[describe.Size];
			rfData.seek(getOffset_DefaultData() + describe.OffsetRow);
			rfData.read(describe.DefaultData);
		}
		return describe;
	}
	public void writeDescribes(DBDescribe[] list, boolean autoIncrement) throws IOException {//describe.Offset của row chứ không phải offset của Describe
		if(list==null) {
			rfData.seek(offsetDescribe);
			rfData.writeLong(offsetDescribe+25);//offsetBeginData
			rfData.writeLong(offsetDescribe+25);//offsetDefaultData
			rfData.writeBoolean(autoIncrement);
			rfData.writeInt(0);//DataLength
			rfData.writeInt(0);//number Column
			rfData.setLength(offsetDescribe+25);
		}else {
			int numberColumn=list.length;
			rfData.seek(offsetDescribe+21);
			rfData.writeInt(numberColumn);
			int dataLength=0;
			/////////////////////////////////////////////////////
			//Write mô tả ///////////////////////////////////////
			/////////////////////////////////////////////////////
			DBDescribe describe;
			for(int i=0;i<numberColumn;i++) {
				describe=list[i];
				describe.OffsetRow = dataLength;//Vị trí đúng của offset trước khi cộng Size
				dataLength+=describe.Size;
				
				rfData.seek(offsetDescribe + 25 + i*100);
				rfData.writeInt(describe.OffsetRow);
				rfData.writeInt(describe.ViewId);
				rfData.writeByte(describe.State);
				rfData.writeByte(describe.Permission);
				rfData.writeInt(describe.Size);
				rfData.writeByte(describe.Type);
				rfData.writeUTF(describe.ColumnName);
			}
			
			/////////////////////////////////////////////////////
			//Write header///////////////////////////////////////
			/////////////////////////////////////////////////////
			long offsetDefaultData = offsetDescribe + 25 + numberColumn*100;
			long offsetBeginData = offsetDefaultData + dataLength;
			rfData.seek(offsetDescribe);
			rfData.writeLong(offsetBeginData);
			rfData.writeLong(offsetDefaultData);
			rfData.writeBoolean(autoIncrement);
			rfData.writeInt(dataLength);//DataLength
			
			/////////////////////////////////////////////////////
			//Write Default data/////////////////////////////////
			/////////////////////////////////////////////////////
			for(int i=0;i<numberColumn;i++) {
				describe=list[i];
				if(describe.DefaultData!=null) {
					rfData.seek(offsetDefaultData + describe.OffsetRow);//Vị trí của DefaultData
					rfData.write(describe.DefaultData);
				}
			}
			

		}
	}
	
	public long getOffset_BeginData() throws IOException {rfData.seek(offsetDescribe);return rfData.readLong();}
	public long getOffset_DefaultData() throws IOException {rfData.seek(offsetDescribe+8);return rfData.readLong();}
	public boolean isAutoIncrement() throws IOException {rfData.seek(offsetDescribe + 16);return rfData.readBoolean();}
	public int getDataLength() throws IOException {rfData.seek(offsetDescribe+17);return rfData.readInt();}
	public int getNumberDescribe() throws IOException {rfData.seek(offsetDescribe+21);return rfData.readInt();}
	public DBDescribe[] readDescribes() throws IOException {
		int numberColumn=getNumberDescribe();
		if(numberColumn==0)
			return null;
		
		DBDescribe[] list=new DBDescribe[numberColumn];
		for(int i=0;i<numberColumn;i++)
			list[i] = readDescribeByIndex(i);
		return list;
	}

	public byte[] getDefaultData() throws IOException {
		int dataLength = getDataLength();
		if (dataLength == 0)
			return null;
		byte[] data = new byte[dataLength];
		rfData.seek(getOffset_DefaultData());
		rfData.read(data);
		return data;
	}
	
	public String getColumnName(int indexDescribe) throws IOException {
	    rfData.seek(offsetDescribe + 25 + indexDescribe*100 + 15);
	    return rfData.readUTF();
    }
	
	public int findIndex_ByColumnName(String ColumnName) throws IOException {
		if(ColumnName==null)
			ColumnName="";
		int numberColumn = getNumberDescribe();
		for(int i=0;i<numberColumn;i++) {
			rfData.seek(offsetDescribe + 25 + i*100 + 15);
			if(ColumnName.equals(rfData.readUTF()))
				return i;
		}
		return -1;
	}
	
	public DBDescribe findDescribe_ByCoulmnId(String ColumnName) throws IOException {
		int index = findIndex_ByColumnName(ColumnName);
		if(index==-1)
			return null;
		return readDescribeByIndex(index);
	}

	public int get_OffsetRow_of_Describe_ByIndex(int index) throws IOException {rfData.seek(offsetDescribe + 25 + index*100);return rfData.readInt();}
	public void updateViewId(int index,int viewId) throws IOException {rfData.seek(offsetDescribe + 25 + index*100 + 4);rfData.writeInt(viewId);}
	public int get_DataSize_ByIndex(int index) throws IOException {rfData.seek(offsetDescribe + 25 + index*100 + 10);return rfData.readInt();}
	public byte get_DataType_ByIndex(int index) throws IOException {rfData.seek(offsetDescribe + 25 + index*100 + 14);return rfData.readByte();}
	
//	public boolean validateDataType_Boolean(int indexDescribe) throws IOException {
//		byte dataType = get_DataType_ByIndex(indexDescribe);
//		return 0<dataType && dataType<10;
//	}
//	public boolean validateDataType_Byte(int indexDescribe) throws IOException {
//		byte dataType = get_DataType_ByIndex(indexDescribe);
//		return 9<dataType && dataType<20;
//	}
//	public boolean validateDataType_Short(int indexDescribe) throws IOException {
//		byte dataType = get_DataType_ByIndex(indexDescribe);
//		return 19<dataType && dataType<40;
//	}
//	public boolean validateDataType_Integer(int indexDescribe) throws IOException {
//		byte dataType = get_DataType_ByIndex(indexDescribe);
//		return 39<dataType && dataType<80;
//	}
//	public boolean validateDataType_Long(int indexDescribe) throws IOException {
//		byte dataType = get_DataType_ByIndex(indexDescribe);
//		return 79<dataType && dataType<100;
//	}
//	public boolean validateDataType_ByteArray(int indexDescribe) throws IOException {
//		byte dataType = get_DataType_ByIndex(indexDescribe);
//		return 99<dataType;
//	}

	
	
	
	public boolean validateDescribe(int indexDescribe,byte Type) throws IOException {
		return Type == get_DataType_ByIndex(indexDescribe);
	}
	public boolean validateDescribe(String ColumnName,byte Type) throws IOException {
		int index = findIndex_ByColumnName(ColumnName);
		if(index==-1)
			return false;
		return Type == get_DataType_ByIndex(index);
	}

	
	
//	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	public boolean validateSize(DB_WriteDatabase dataOperator) throws IOException {
//	    int size = get_DataSize_ByIndex(dataOperator.indexDescribe);
//	    if(size==dataOperator.size)
//	        return true;
//	    else if(dataOperator.size>size) {
//	        System.err.println("Error size("+dataOperator.size+") → Describe is "+DBDefine_DataType.getTypeName(dataOperator.Type)+" : "+size);
//	        return false;
//	    }else {
//	        System.out.println("size("+dataOperator.size+") != Describe is "+DBDefine_DataType.getTypeName(dataOperator.Type)+" : "+size);
//	        return true;
//	    }
//    }
//	public boolean validateFix_DataOperator(DB_WriteDatabase dataOperator) throws IOException {
//        if(dataOperator.Type != get_DataType_ByIndex(dataOperator.indexDescribe)) {//Validate Type
//            int indexDescribe = findIndex_ByColumnName(dataOperator.columnName);
//            if(dataOperator.Type==get_DataType_ByIndex(indexDescribe))
//                dataOperator.indexDescribe=indexDescribe;//Update lai
//            else {
//                System.err.println("Error Type("+dataOperator.indexDescribe+") is not "+DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
//                return false;
//            }
//        }
//        
//        if(dataOperator.Type==DBDefine_DataType.STRING) {
//            return validateSize(dataOperator);
//        }else if(dataOperator.Type==DBDefine_DataType.ByteArray || dataOperator.Type==DBDefine_DataType.LIST) {
//            return validateSize(dataOperator);
//        }else if(dataOperator.Type==DBDefine_DataType.IPV6) {
//            if(dataOperator!=null)
//                return ((byte[])dataOperator.value).length==16;
//        }
//	    return true;
//    }
	
//	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void readData(long offset,DB_ReadDatabase data) throws IOException {
        rfData.seek(offset);
        switch (data.Type) {
            case DBDefine_DataType.BOOLEAN:data.value=rfData.readBoolean();break;

            case DBDefine_DataType.BYTE:
            case DBDefine_DataType.STATUS:
            case DBDefine_DataType.PERMISSION:
            case DBDefine_DataType.AVARTAR:data.value=rfData.readByte();break;

            case DBDefine_DataType.SHORT:data.value=rfData.readShort();break;

            case DBDefine_DataType.FLOAT:data.value=rfData.readFloat();break;
            case DBDefine_DataType.IPV4:
            case DBDefine_DataType.INTEGER:data.value=rfData.readInt();break;

            case DBDefine_DataType.DOUBLE:data.value=rfData.readDouble();break;

            case DBDefine_DataType.USER_ID:
            case DBDefine_DataType.TIMEMILI:
            case DBDefine_DataType.LONG:data.value=rfData.readLong();break;

            case DBDefine_DataType.ByteArray:
            case DBDefine_DataType.LIST:
                int size = rfData.readInt();
                if(size>0) {
                    data.value=new byte[size];
                    rfData.read((byte[])data.value);
                }else
                    data.value=null;
                break;
            case DBDefine_DataType.STRING:data.value=rfData.readUTF();break;
            case DBDefine_DataType.IPV6:
                data.value = new byte[16];
                rfData.read((byte[])data.value);
                break;

            default:
                throw new IOException("Database error DBProcess_Describe → readData(long offset,DB_ReadDatabase data) " + DBDefine_DataType.getTypeName(data.Type));
        }
    }
    public Object readValueData(long offsetData,int indexDescribe) throws IOException {
        byte Type = get_DataType_ByIndex(indexDescribe);
        rfData.seek(offsetData);
        switch (Type) {
            case DBDefine_DataType.BOOLEAN:return rfData.readBoolean();

            case DBDefine_DataType.BYTE:
            case DBDefine_DataType.STATUS:
            case DBDefine_DataType.PERMISSION:
            case DBDefine_DataType.AVARTAR:return rfData.readByte();

            case DBDefine_DataType.SHORT:return rfData.readShort();

            case DBDefine_DataType.FLOAT:return rfData.readFloat();
            
            case DBDefine_DataType.IPV4:
            case DBDefine_DataType.INTEGER:return rfData.readInt();

            case DBDefine_DataType.DOUBLE:return rfData.readDouble();

            case DBDefine_DataType.USER_ID:
            case DBDefine_DataType.TIMEMILI:
            case DBDefine_DataType.LONG:return rfData.readLong();

            case DBDefine_DataType.STRING:return rfData.readUTF();
            
            case DBDefine_DataType.ByteArray:
            case DBDefine_DataType.LIST:
                int size = rfData.readInt();
                if(size>0) {
                    byte[] result = new byte[size];
                    rfData.read(result);
                    return result;
                }else
                    return null;

            case DBDefine_DataType.IPV6:
                byte[] ipv6 = new byte[16];
                rfData.read(ipv6);
                return ipv6;
            default:
                throw new IOException("Database error DBProcess_Describe → readValueData(long offsetData,int indexDescribe) " + DBDefine_DataType.getTypeName(get_DataType_ByIndex(indexDescribe)));
        }
    }
	
    public void writeData(long offset,byte[] _data) throws IOException {
    	rfData.seek(offset);
    	rfData.write(_data);
    }
    public void writeData(long offset, int indexDescribe, Object value) throws IOException {
    	byte Type = get_DataType_ByIndex(indexDescribe);
    	rfData.seek(offset);
        switch (Type) {
            case DBDefine_DataType.BOOLEAN:rfData.writeBoolean((boolean) value);break;

            case DBDefine_DataType.BYTE:
            case DBDefine_DataType.STATUS:
            case DBDefine_DataType.PERMISSION:
            case DBDefine_DataType.AVARTAR:
                rfData.writeByte((byte) value);break;

            case DBDefine_DataType.SHORT:
                rfData.writeShort((short) value);break;

            case DBDefine_DataType.FLOAT:
                rfData.writeFloat((float) value);break;
                
            case DBDefine_DataType.IPV4:
            case DBDefine_DataType.INTEGER:
                rfData.writeInt((int) value);break;

            case DBDefine_DataType.DOUBLE:
                rfData.writeDouble((double) value);break;

            case DBDefine_DataType.USER_ID:
            case DBDefine_DataType.TIMEMILI:
            case DBDefine_DataType.LONG:
                rfData.writeLong((long) value);break;

            case DBDefine_DataType.ByteArray:
            case DBDefine_DataType.LIST:
                byte[] _data = (byte[]) value;
                if(_data==null)
                    rfData.writeInt(0);
                else {
                    rfData.writeInt(_data.length);
                    rfData.write(_data);
                }
                break;
                
            case DBDefine_DataType.STRING:
                rfData.writeUTF((String) value);break;
                
            case DBDefine_DataType.IPV6:
                if(value!=null && ((byte[])value).length==16)
                    rfData.write((byte[])value);break;

            default:
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, int indexDescribe, Object value) " + DBDefine_DataType.getTypeName(Type) + " : " + value);
        }
    	
    	
    }
    public void writeData(long offset,DB_ReadDatabase dataOperator) throws IOException {
        rfData.seek(offset);
        switch (dataOperator.Type) {
            case DBDefine_DataType.BOOLEAN:rfData.writeBoolean((boolean) dataOperator.value);break;

            case DBDefine_DataType.BYTE:
            case DBDefine_DataType.STATUS:
            case DBDefine_DataType.PERMISSION:
            case DBDefine_DataType.AVARTAR:
                rfData.writeByte((byte) dataOperator.value);break;

            case DBDefine_DataType.SHORT:
                rfData.writeShort((short) dataOperator.value);break;

            case DBDefine_DataType.FLOAT:
                rfData.writeFloat((float) dataOperator.value);break;
                
            case DBDefine_DataType.IPV4:
            case DBDefine_DataType.INTEGER:
                rfData.writeInt((int) dataOperator.value);break;

            case DBDefine_DataType.DOUBLE:
                rfData.writeDouble((double) dataOperator.value);break;

            case DBDefine_DataType.USER_ID:
            case DBDefine_DataType.TIMEMILI:
            case DBDefine_DataType.LONG:
                rfData.writeLong((long) dataOperator.value);break;

            case DBDefine_DataType.ByteArray:
            case DBDefine_DataType.LIST:
                byte[] _data = (byte[]) dataOperator.value;
                if(_data==null)
                    rfData.writeInt(0);
                else {
                    rfData.writeInt(_data.length);
                    rfData.write(_data);
                }
                break;
                
            case DBDefine_DataType.STRING:
                rfData.writeUTF((String) dataOperator.value);break;
                
            case DBDefine_DataType.IPV6:
                if(dataOperator.value!=null && ((byte[])dataOperator.value).length==16)
                    rfData.write((byte[])dataOperator.value);break;

            default:
                throw new IOException("Database error DBProcess_Describe → writeData(long offset,DB_ReadDatabase dataOperator) " + DBDefine_DataType.getTypeName(dataOperator.Type));
        }
    }
	
	
	public Object process(long offsetData, byte operator, byte Type, Object object) throws IOException {
		switch (Type) {
			case DBDefine_DataType.BOOLEAN:
				return new DBOperator_Boolean(rfData).process(offsetData, operator, (boolean) object);

			case DBDefine_DataType.BYTE:
			case DBDefine_DataType.STATUS:
			case DBDefine_DataType.PERMISSION:
			case DBDefine_DataType.AVARTAR:
				return new DBOperator_Byte(rfData).process(offsetData, operator, (byte) object);

			case DBDefine_DataType.SHORT:
				return new DBOperator_Short(rfData).process(offsetData, operator, (short) object);

			case DBDefine_DataType.FLOAT:
				return new DBOperator_Float(rfData).process(offsetData, operator, (float) object);
			case DBDefine_DataType.IPV4:
			case DBDefine_DataType.INTEGER:
				return new DBOperator_Integer(rfData).process(offsetData, operator, (int) object);

			case DBDefine_DataType.DOUBLE:
				return new DBOperator_Double(rfData).process(offsetData, operator, (double) object);

			case DBDefine_DataType.USER_ID:
			case DBDefine_DataType.TIMEMILI:
			case DBDefine_DataType.LONG:
				return new DBOperator_Long(rfData).process(offsetData, operator, (long) object);

			case DBDefine_DataType.ByteArray:
				return new DBOperator_ByteArrays(rfData).process(offsetData, operator, (byte[]) object);
			case DBDefine_DataType.LIST:
				return new DBOperator_List(rfData).process(offsetData, operator, (byte[]) object);
			case DBDefine_DataType.STRING:
				return new DBOperator_String(rfData).process(offsetData, operator, (String) object);
			case DBDefine_DataType.IPV6:
				return new DBOperator_IPV6(rfData).process(offsetData, operator, (byte[]) object);

			default:
				throw new IOException("Database error DBProcess_Describe → process(long offsetData, byte pperator, byte Type, Object object) " + DBDefine_DataType.getTypeName(Type));
		}
	}

	public Object processValidateType(long offsetData, DB_WriteDatabase writer) throws IOException {
		if (writer.Type != get_DataType_ByIndex(writer.indexDescribe) || getColumnName(writer.indexDescribe).equals(writer.columnName)==false) {
			int indexDescribe = findIndex_ByColumnName(writer.columnName);
			if (indexDescribe!=-1 && writer.Type == get_DataType_ByIndex(indexDescribe))
				writer.indexDescribe = indexDescribe;
			else
				throw new IOException("Database error DBProcess_Describe → writeData(long offset, DB_WriteDatabase writer) " + DBDefine_DataType.getTypeName(writer.Type));
		}
		return process(offsetData, writer.operator, writer.Type, writer.value);
	}
	
	
	protected void trace() throws IOException {
		System.out.println("***************************************************************");
		System.out.println("File size : "+rfData.length());
		System.out.println("OffsetDescribe : "+offsetDescribe);
		System.out.println("DatalLength : "+getDataLength());
		System.out.println("OffsetBeginData : "+getOffset_BeginData());
		System.out.println("OffsetDefaultData : "+getOffset_DefaultData());
		System.out.println("***************************************************************");
	}
	
	
	
	
	
	
	
	
	
	
	
}
