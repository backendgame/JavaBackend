package backendgame.com.database;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

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
				
				if(describe.Size<describe.DefaultData.length)
					describe.Size = describe.DefaultData.length;
				
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
			
			if(rfData.length() < offsetBeginData)
				rfData.setLength(offsetBeginData);//Trường hợp String và Array init không đủ số byte → countRow bị sai
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
	
	
	
	
//	public boolean validateDescribe(int indexDescribe,byte Type) throws IOException {
//		return Type == get_DataType_ByIndex(indexDescribe);
//	}
//	public boolean validateDescribe(String ColumnName,byte Type) throws IOException {
//		int index = findIndex_ByColumnName(ColumnName);
//		if(index==-1)
//			return false;
//		return Type == get_DataType_ByIndex(index);
//	}

	
	
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
    	data.value = readData(offset, data.Type);
    }
    
    public Object readData(long offset, byte Type) throws IOException {
    	rfData.seek(offset);
		if(0<Type && Type<10)
			return rfData.readBoolean();
		else if(9<Type && Type<20)
			return rfData.readByte();
		else if(19<Type && Type<40)
			return rfData.readShort();
		else if(39<Type && Type<60)
			return rfData.readInt();
		else if(59<Type && Type<80)
			return rfData.readFloat();
		else if(79<Type && Type<90)
			return rfData.readLong();
		else if(89<Type && Type<100)
			return rfData.readDouble();
		else if(Type==DBDefine_DataType.ByteArray || Type==DBDefine_DataType.LIST) {
            int size = rfData.readInt();
            if(size>0) {
                byte[] result = new byte[size];
                rfData.read(result);
                return result;
            }else
                return null;
		}else if(Type==DBDefine_DataType.STRING)
			return rfData.readUTF();
		else if(Type==DBDefine_DataType.IPV6) {
			 byte[] ipv6 = new byte[16];
             rfData.read(ipv6);
             return ipv6;
		}else
			throw new IOException("Database error DBProcess_Describe → readData(long offset, byte Type) " + DBDefine_DataType.getTypeName(Type));
    }
    
    public void writeData(long offset, byte Type, Object value) throws IOException {
    	rfData.seek(offset);
		if(0<Type && Type<10)
			rfData.writeBoolean((boolean) value);
		else if(9<Type && Type<20)
			rfData.writeByte((byte) value);
		else if(19<Type && Type<40)
			rfData.writeShort((short) value);
		else if(39<Type && Type<60)
			rfData.writeInt((int) value);
		else if(59<Type && Type<80)
			rfData.writeFloat((float) value);
		else if(79<Type && Type<90)
			rfData.writeLong((long) value);
		else if(89<Type && Type<100)
			rfData.writeDouble((double) value);
		else if(Type==DBDefine_DataType.ByteArray || Type==DBDefine_DataType.LIST) {
            byte[] _data = (byte[]) value;
            if(_data==null)
                rfData.writeInt(0);
            else {
                rfData.writeInt(_data.length);
                rfData.write(_data);
            }
		}else if(Type==DBDefine_DataType.STRING)
			rfData.writeUTF((String) value);
		else if(Type==DBDefine_DataType.IPV6) {
            if(value!=null && ((byte[])value).length==16)
                rfData.write((byte[])value);
		}else
			throw new IOException("Database error DBProcess_Describe → writeData(long offset, int indexDescribe, Object value) " + DBDefine_DataType.getTypeName(Type) + " : " + value);
    }
	
	public boolean processBoolean(long offsetData, byte operator,boolean value) throws IOException {
		return new DBOperator_Boolean(rfData).process(offsetData, operator, value);
	}
	public byte processByte(long offsetData, byte operator,byte value) throws IOException {
		return new DBOperator_Byte(rfData).process(offsetData, operator, value);
	}
	public short processShort(long offsetData, byte operator,short value) throws IOException {
		return new DBOperator_Short(rfData).process(offsetData, operator, value);
	}
	public float processFloat(long offsetData, byte operator,float value) throws IOException {
		return new DBOperator_Float(rfData).process(offsetData, operator, value);
	}
	public int processInt(long offsetData, byte operator,int value) throws IOException {
		return new DBOperator_Integer(rfData).process(offsetData, operator, value);
	}
	public double processDouble(long offsetData, byte operator,double value) throws IOException {
		return new DBOperator_Double(rfData).process(offsetData, operator, value);
	}
	public long processLong(long offsetData, byte operator,long value) throws IOException {
		return new DBOperator_Long(rfData).process(offsetData, operator, value);
	}
	public byte[] processByteArray(long offsetData, byte operator,byte[] value) throws IOException {
		return new DBOperator_ByteArrays(rfData).process(offsetData, operator, value);
	}
	public byte[] processList(long offsetData, byte operator,byte[] value) throws IOException {
		return new DBOperator_List(rfData).process(offsetData, operator, value);
	}
	public String processString(long offsetData, byte operator,String value) throws IOException {
		return new DBOperator_String(rfData).process(offsetData, operator, value);
	}
	public byte[] processIpV6(long offsetData, byte operator,byte[] value) throws IOException {
		return new DBOperator_IPV6(rfData).process(offsetData, operator, value);
	}
	
	public Object process(long offsetData, byte operator, byte Type, Object object) throws IOException {
		rfData.seek(offsetData);
		if(0<Type && Type<10)
			return new DBOperator_Boolean(rfData).process(offsetData, operator, (boolean) object);
		else if(9<Type && Type<20)
			return new DBOperator_Byte(rfData).process(offsetData, operator, (byte) object);
		else if(19<Type && Type<40)
			return new DBOperator_Short(rfData).process(offsetData, operator, (short) object);
		else if(39<Type && Type<60)
			return new DBOperator_Integer(rfData).process(offsetData, operator, (int) object);
		else if(59<Type && Type<80)
			return new DBOperator_Float(rfData).process(offsetData, operator, (float) object);
		else if(79<Type && Type<90)
			return new DBOperator_Long(rfData).process(offsetData, operator, (long) object);
		else if(89<Type && Type<100)
			return new DBOperator_Double(rfData).process(offsetData, operator, (double) object);
		else if(Type==DBDefine_DataType.ByteArray)
			return new DBOperator_ByteArrays(rfData).process(offsetData, operator, (byte[]) object);
		else if(Type==DBDefine_DataType.LIST)
			return new DBOperator_List(rfData).process(offsetData, operator, (byte[]) object);
		else if(Type==DBDefine_DataType.STRING)
			return new DBOperator_String(rfData).process(offsetData, operator, (String) object);
		else if(Type==DBDefine_DataType.IPV6)
			return new DBOperator_IPV6(rfData).process(offsetData, operator, (byte[]) object);
		else
			throw new IOException("Database error DBProcess_Describe → process(long offsetData, byte pperator, byte Type, Object object) " + DBDefine_DataType.getTypeName(Type));
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
	
	
	public void trace() throws IOException {
		System.out.println("File size : "+rfData.length());
		System.out.println("OffsetDescribe : "+offsetDescribe);
		System.out.println("DatalLength : "+getDataLength());
		System.out.println("OffsetBeginData : "+getOffset_BeginData());
		System.out.println("OffsetDefaultData : "+getOffset_DefaultData());
		System.out.println("-----------------------------------------------------------------");
		DBDescribe[] list = readDescribes();
		int maxSpace = 0;
		for(DBDescribe describe:list)
			if(describe.ColumnName.length()>maxSpace)
				maxSpace = describe.ColumnName.length();
		if(maxSpace>20)
			maxSpace=20;
		System.out.printf("%"+(maxSpace+20)+"."+(maxSpace+20)+"s%12.12s%8.8s%12.12s   DefaultValue\n","Type","OffsetRow","ViewId","Permission");
		
		String strType;
		for(DBDescribe describe:list) {
			if(describe.ColumnName.length()>maxSpace) {
				System.out.print(describe.ColumnName.substring(0, maxSpace-3)+"...");
			}else {
				System.out.print(describe.ColumnName);
				for(int i=describe.ColumnName.length();i<maxSpace;i++)
					System.out.print(" ");
			}
			
			strType = DBDefine_DataType.getTypeName(describe.Type);
			if(describe.Type==DBDefine_DataType.ByteArray || describe.Type==DBDefine_DataType.LIST || describe.Type==DBDefine_DataType.STRING)
				strType = strType+"("+describe.Size+")";
			
			if(describe.DefaultData==null)
				System.out.printf("%20.20s%12.12s%8.8s%12.12s   Null\n",strType,describe.OffsetRow,describe.ViewId,describe.Permission);
			else
				if(describe.Type==DBDefine_DataType.ByteArray || describe.Type==DBDefine_DataType.LIST || describe.Type==DBDefine_DataType.IPV6)
					System.out.printf("%20.20s%12.12s%8.8s%12.12s   "+Arrays.toString((byte[])describe.getDefaultData())+"\n",strType,describe.OffsetRow,describe.ViewId,describe.Permission);
				else
					System.out.printf("%20.20s%12.12s%8.8s%12.12s   "+describe.getDefaultData()+"\n",strType,describe.OffsetRow,describe.ViewId,describe.Permission);
		}
	}
}
