package richard;

public class MessageSending {
	public short CMD;
	public byte[] data;
	public short dataLength;
	public short currentWriter;

//	public MessageSending() {
//		currentWriter=0;
//		dataLength=0;
//		data = null;
//	}
	public MessageSending(short CMD_REALTIME) {
		CMD=CMD_REALTIME;
		currentWriter=0;
		dataLength=0;
		data = null;
	}
	public MessageSending(short _CMD_,int buffer) {
		CMD=_CMD_;
		currentWriter=0;
		dataLength=(short) buffer;
		data = new byte[dataLength];
	}
	
	public MessageSending(short _CMD_,byte byteWrite) {
		CMD=_CMD_;
		currentWriter=1;
		dataLength=1;
		data = new byte[1];
		data[0]=byteWrite;
	}
	public MessageSending(short CMD_REALTIME,byte[] _data) {
		CMD=CMD_REALTIME;
		currentWriter=(short) _data.length;
		dataLength=(short) _data.length;
		data = _data;
	}
	
	public final void reNew() {currentWriter=0;}
	public final byte[] getDangerData() {return data;}
	public final short avaiable() {return currentWriter;}
	public final void seekEnd() {currentWriter=dataLength;}
	
	
	public final void editBYTE(int location,byte value) {
		data[location]=value;
	}
	public final void editSHORT(int location,short value) {
		data[location]		= (byte) (value>>>8);
		data[location+1]	= (byte) value;
	}
	public final void editINT(int location,int value) {
		data[location]  =(byte)(value>>>24);
		data[location+1]=(byte)(value>>>16);
		data[location+2]=(byte)(value>>>8);
		data[location+3]=(byte)value;
	}
	
	
	public void editDeltaPastTimeToCurrentTimeMillis(int location) {
		long l0 = data[location] & 0xFF;
		long l1 = data[location+1] & 0xFF;
		long l2 = data[location+2] & 0xFF;
		long l3 = data[location+3] & 0xFF;
		long l4 = data[location+4] & 0xFF;
		long l5 = data[location+5] & 0xFF;
		long l6 = data[location+6] & 0xFF;
		long l7 = data[location+7] & 0xFF;
		
		long r0 = l0 << 56;
		long r1 = l1 << 48;
		long r2 = l2 << 40;
		long r3 = l3 << 32;
		long r4 = l4 << 24;
		long r5 = l5 << 16;
		long r6 = l6 << 8;
		long r7 = l7;
		editLONG(location, System.currentTimeMillis()-(r0 + r1 + r2 + r3 + r4 + r5 + r6 + r7));
	}
	public void editDeltaCurrentTimeMillisToFutureTime(int location) {
		long l0 = data[location] & 0xFF;
		long l1 = data[location+1] & 0xFF;
		long l2 = data[location+2] & 0xFF;
		long l3 = data[location+3] & 0xFF;
		long l4 = data[location+4] & 0xFF;
		long l5 = data[location+5] & 0xFF;
		long l6 = data[location+6] & 0xFF;
		long l7 = data[location+7] & 0xFF;
		
		long r0 = l0 << 56;
		long r1 = l1 << 48;
		long r2 = l2 << 40;
		long r3 = l3 << 32;
		long r4 = l4 << 24;
		long r5 = l5 << 16;
		long r6 = l6 << 8;
		long r7 = l7;
		editLONG(location, r0 + r1 + r2 + r3 + r4 + r5 + r6 + r7 - System.currentTimeMillis());
	}
	
	public final void editLONG(int location,long value) {
		data[location]   = (byte)(value >>> 56);
		data[location+1] = (byte)(value >>> 48);
		data[location+2] = (byte)(value >>> 40);
		data[location+3] = (byte)(value >>> 32);
		data[location+4] = (byte)(value >>> 24);
		data[location+5] = (byte)(value >>> 16);
		data[location+6] = (byte)(value >>>  8);
		data[location+7] = (byte)(value >>>  0);
	}
	public final void skip(int numberByte) {if (currentWriter + numberByte > dataLength) {dataLength = (short) (currentWriter + numberByte);byte[] temp = new byte[dataLength];for (int i = 0; i < currentWriter; i++)temp[i] = data[i];data = temp;currentWriter=dataLength;}else currentWriter += numberByte;}
	public final void initBuffer(int length) {if (currentWriter + length > dataLength) {dataLength = (short) (currentWriter + length);byte[] temp = new byte[dataLength];for (int i = 0; i < currentWriter; i++)temp[i] = data[i];data = temp;}}

	public final void writeBoolean(boolean value) {
		if (currentWriter +1 > dataLength) {
			dataLength = (short) (currentWriter+1);
			byte[] temp = new byte[dataLength];
			for (int i = 0; i < currentWriter; i++)
				temp[i] = data[i];
			if (value)
				temp[currentWriter] = 1;
			else
				temp[currentWriter] = 0;
			currentWriter=dataLength;
			data = temp;
		} else {
			if (value)
				data[currentWriter] = 1;
			else
				data[currentWriter] = 0;
			currentWriter++;
		}
	}
	
	public final void writeByte(byte value) {
		if(currentWriter+1>dataLength){
			dataLength = (short) (currentWriter+1);
			byte[] temp = new byte[dataLength];
			for(int i=0;i<currentWriter;i++)
				temp[i]=data[i];
			temp[currentWriter]=value;
			currentWriter=dataLength;
			data=temp;
		}else{
			data[currentWriter]=value;
			currentWriter++;
		}
	}
	public final void writeshort(short value) {
		if(currentWriter+2>dataLength){
			dataLength=(short) (currentWriter+2);
			byte[] temp=new byte[dataLength];
			for(int i=0;i<currentWriter;i++)
				temp[i]=data[i];
			temp[currentWriter]		= (byte) (value>>>8);
			temp[currentWriter+1]	= (byte) value;
			currentWriter=dataLength;
			data=temp;
		}else{
			data[currentWriter]		= (byte) (value>>>8);
			data[currentWriter+1]	= (byte) value;
			currentWriter+=2;
		}
	}
	public final void writeInt(int value) {
		if(currentWriter+4>dataLength){
			dataLength=(short) (currentWriter+4);
			byte[] temp=new byte[dataLength];
			for(int i=0;i<currentWriter;i++)
				temp[i]=data[i];
			temp[currentWriter]  =(byte)(value>>>24);
			temp[currentWriter+1]=(byte)(value>>>16);
			temp[currentWriter+2]=(byte)(value>>>8);
			temp[currentWriter+3]=(byte)value;
			currentWriter=dataLength;
			data=temp;
		}else{
			data[currentWriter]  =(byte)(value>>>24);
			data[currentWriter+1]=(byte)(value>>>16);
			data[currentWriter+2]=(byte)(value>>>8);
			data[currentWriter+3]=(byte)value;
			currentWriter+=4;
		}
	}
	public final void writeLong(long v) {
		if(currentWriter+8>dataLength){
			dataLength=(short) (currentWriter+8);
			byte[] temp=new byte[dataLength];
			for(int i=0;i<currentWriter;i++)
				temp[i]=data[i];
			temp[currentWriter]   = (byte)(v >>> 56);
			temp[currentWriter+1] = (byte)(v >>> 48);
			temp[currentWriter+2] = (byte)(v >>> 40);
			temp[currentWriter+3] = (byte)(v >>> 32);
			temp[currentWriter+4] = (byte)(v >>> 24);
			temp[currentWriter+5] = (byte)(v >>> 16);
			temp[currentWriter+6] = (byte)(v >>>  8);
			temp[currentWriter+7] = (byte)(v >>>  0);
			currentWriter=dataLength;
			data=temp;
		}else{
			data[currentWriter]   = (byte)(v >>> 56);
			data[currentWriter+1] = (byte)(v >>> 48);
			data[currentWriter+2] = (byte)(v >>> 40);
			data[currentWriter+3] = (byte)(v >>> 32);
			data[currentWriter+4] = (byte)(v >>> 24);
			data[currentWriter+5] = (byte)(v >>> 16);
			data[currentWriter+6] = (byte)(v >>>  8);
			data[currentWriter+7] = (byte)(v >>>  0);
			currentWriter+=8;
		}
	}
	
	public final void writeFloat(float value) {writeInt(Float.floatToIntBits(value));}
	public final void writeDouble(double v) {writeLong(Double.doubleToLongBits(v));}
	
	public final void writeByteArrayWithLength(byte[] arr) {
		if(arr==null || arr.length==0)
			writeInt(0);
		else{
			int lengthArr=arr.length;
			if(currentWriter+lengthArr+4>dataLength){
				dataLength=(short) (currentWriter+lengthArr+4);
				byte[] temp=new byte[dataLength];
				for(int i=0;i<currentWriter;i++)
					temp[i]=data[i];
				int value=lengthArr;
				temp[currentWriter]  =(byte)(value>>>24);
				temp[currentWriter+1]=(byte)(value>>>16);
				temp[currentWriter+2]=(byte)(value>>>8);
				temp[currentWriter+3]=(byte) value;
				currentWriter+=4;
				for(int i=0;i<lengthArr;i++)
					temp[i+currentWriter]=arr[i];
				currentWriter=dataLength;
				data=temp;
			}else{
				int value=lengthArr;
				data[currentWriter]  =(byte)(value>>>24);
				data[currentWriter+1]=(byte)(value>>>16);
				data[currentWriter+2]=(byte)(value>>>8);
				data[currentWriter+3]=(byte) value;
				currentWriter+=4;
				for(int i=0;i<lengthArr;i++)
					data[i+currentWriter]=arr[i];
				currentWriter+=lengthArr;
			}
		}
	}

//	public byte[] getWSData() {
//		short CMDName = CMD;
//		byte[] cacheWs = new byte[currentWriter+2];
//		cacheWs[0]	= (byte) (CMDName>>>8);
//		cacheWs[1]	= (byte) CMDName;
//		for(short i=0;i<currentWriter;i++)
//			cacheWs[i+2]=data[i];
//		return cacheWs;
//	}
	
	public byte[] cacheWs=null;
	public byte[] getWSData() {
		if(cacheWs==null) {
			short CMDName = CMD;
			cacheWs = new byte[currentWriter+2];
			cacheWs[0]	= (byte) (CMDName>>>8);
			cacheWs[1]	= (byte) CMDName;
			for(short i=0;i<currentWriter;i++)
				cacheWs[i+2]=data[i];
		}
		return cacheWs;
	}
	
	public final void writeCopyData(byte[] copyData) {
		int lengthCopy=copyData.length;
		if(currentWriter+lengthCopy>dataLength){
			dataLength = (short) (currentWriter+lengthCopy);
			byte[] temp=new byte[dataLength];
			for(int i=0;i<currentWriter;i++)
				temp[i]=data[i];
			for(int i=0;i<lengthCopy;i++)
				temp[i+currentWriter]=copyData[i];
			data=temp;
			currentWriter=dataLength;
		}else{
			for(int i=0;i<lengthCopy;i++)
				data[i+currentWriter]=copyData[i];
			currentWriter+=lengthCopy;
		}
	}
	
	public final void writeCopyData(byte[] copyData,int skip,int length) {
		if(currentWriter+length>dataLength){
			dataLength=(short) (currentWriter+length);
			byte[] temp=new byte[dataLength];
			for(int i=0;i<currentWriter;i++)
				temp[i]=data[i];
			for(int i=0;i<length;i++)
				temp[i+currentWriter]=copyData[skip+i];
			data=temp;
			currentWriter=dataLength;
		}else{
			for(int i=0;i<length;i++)
				data[i+currentWriter]=copyData[skip+i];
			currentWriter+=length;
		}
	}

	public final void writeMiniByte(byte[] bs) {
		if(bs==null) {
			writeByte((byte) 0);
		}else {
			writeByte((byte) bs.length);
			writeCopyData(bs);
		}
	}
	
	public final int writeString(String value) {
		if(value==null){
//			value="";
			writeshort((short) 0);
			return 0;
		}
		int stringLenth = value.length();
		int j = 0;
	    int k;
		for (int n = 0; n < stringLenth; n++) {
			k = value.charAt(n);
			if ((k >= 1) && (k <= 127)) {
				j++;
			} else if (k > 2047) {
				j += 3;
			} else {
				j += 2;
			}
		}
//		if (j > 65535) {
//			throw new UTFDataFormatException("encoded string too long: " + j + " bytes");
//		}
		byte[] arrayOfString = new byte[j * 2 + 2];
		arrayOfString[0] = ((byte)(j >>> 8 & 0xFF));
		arrayOfString[1] = ((byte)(j >>> 0 & 0xFF));
		int count=2;
		int i1 = 0;
		for (i1 = 0; i1 < stringLenth; i1++) {
			k = value.charAt(i1);
			if ((k < 1) || (k > 127)) {
				break;
			}
			arrayOfString[(count++)] = ((byte) k);
		}
		while (i1 < stringLenth) {
			k = value.charAt(i1);
			if ((k >= 1) && (k <= 127)) {
				arrayOfString[(count++)] = ((byte) k);
			} else if (k > 2047) {
				arrayOfString[(count++)] = ((byte) (0xE0 | k >> 12 & 0xF));
				arrayOfString[(count++)] = ((byte) (0x80 | k >> 6 & 0x3F));
				arrayOfString[(count++)] = ((byte) (0x80 | k >> 0 & 0x3F));
			} else {
				arrayOfString[(count++)] = ((byte) (0xC0 | k >> 6 & 0x1F));
				arrayOfString[(count++)] = ((byte) (0x80 | k >> 0 & 0x3F));
			}
			i1++;
		}
//	    paramDataOutput.write(arrayOfString, 0, j + 2);
		int lengString = j+2;
		if(currentWriter+lengString<=dataLength){
			for(int i=0;i<lengString;i++)
				data[i+currentWriter]=arrayOfString[i];
			currentWriter=(short) (currentWriter+lengString);
		}else{
			byte[] temp=new byte[currentWriter+lengString];
			for(int i=0;i<currentWriter;i++)
				temp[i]=data[i];
			for(int i=0;i<lengString;i++)
				temp[i+currentWriter]=arrayOfString[i];
			currentWriter=(short) (currentWriter+lengString);
			dataLength=currentWriter;
			data=temp;
		}
	    return j + 2;
	}
	
	public MessageSending cloneData() {
		MessageSending mgClone=new MessageSending(CMD,dataLength);
		while(mgClone.currentWriter<currentWriter) {
			mgClone.data[mgClone.currentWriter]=data[mgClone.currentWriter];
			mgClone.currentWriter++;
		}
		return mgClone;
	}
	
	public final void trace() {
		System.out.print("["+data[0]);
		for(short i=1;i<dataLength;i++)
			System.out.print(","+data[i]);
		System.out.println("]");
	}
}
