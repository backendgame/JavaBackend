package richard;

public class MessageReceiving {
	public short dataLength;
	public short currentReading;
	public short CMD;
	public byte[] buffer;
	public boolean isMessageCorrect;
	public MessageReceiving(short cmd,byte[] DATA,short _dataLength) {/*Phần này của realtime*/
		CMD=cmd;
		dataLength=_dataLength;
		currentReading=0;
		buffer=DATA;
		isMessageCorrect=true;
	}
	public MessageReceiving(byte[] DATA,short _dataLength) {/*Phần này của realtime*/
//		CMD=cmd;
		dataLength=_dataLength;
		currentReading=0;
		buffer=DATA;
		isMessageCorrect=true;
	}
	
	public int avaiable() {return dataLength-currentReading;}
	public byte[] getEndByte() {
		int lengClone=dataLength-currentReading;
		if(lengClone<1)
			return null;
		byte[] dataClone=new byte[lengClone];
		for(int i=0;i<lengClone;i++)
			dataClone[i]=buffer[i+currentReading];
		return dataClone;
	}
	public MessageSending cloneSending(short _cmd_) {
		MessageSending mgClone=new MessageSending(_cmd_);
		mgClone.writeCopyData(buffer,0,dataLength);
		return mgClone;
	}
	
	public void startBeginRead() {currentReading = 0;}
	public boolean validate() {return isMessageCorrect && (dataLength-currentReading)==0;}
	public boolean isCorrect() {return isMessageCorrect;}
	public void reSet() {currentReading=0;isMessageCorrect=true;}
	public byte discoveryByte() {return buffer[currentReading];}
	public byte discoveryByte(int _distance) {return buffer[currentReading+_distance];}
	public void skip(int length) {currentReading+=length;}
	public boolean readBoolean() {
		if(currentReading+1>dataLength){
			isMessageCorrect=false;
			return false;
		}else
			return buffer[currentReading++]!=0;
	}

	public byte readByte() {
		if(currentReading+1>dataLength){
			isMessageCorrect=false;
			return 0;
		}else
			return buffer[currentReading++];
	}
	
	public short readShort() {
		if(currentReading+2>dataLength){
			isMessageCorrect=false;
			return 0;
		}else{
			int ch1 = buffer[currentReading] & 0xFF;
			int ch2 = buffer[currentReading+1] & 0xFF;
			currentReading+=2;
			return (short)((ch1 << 8) + (ch2 << 0));
		}
	}
	
	
	public int readInt() {
		if(currentReading+4>dataLength){
			isMessageCorrect=false;
			return 0;
		}else{
			int ch1 = buffer[currentReading] & 0xFF;
			int ch2 = buffer[currentReading+1] & 0xFF;
			int ch3 = buffer[currentReading+2] & 0xFF;
			int ch4 = buffer[currentReading+3] & 0xFF;
			currentReading+=4;
			return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
		}
	}
	
	public long readLong() {
		if(currentReading+8>dataLength){
			isMessageCorrect=false;
			return 0;
		}else{
			long l0 = buffer[currentReading] & 0xFF;
			long l1 = buffer[currentReading+1] & 0xFF;
			long l2 = buffer[currentReading+2] & 0xFF;
			long l3 = buffer[currentReading+3] & 0xFF;
			long l4 = buffer[currentReading+4] & 0xFF;
			long l5 = buffer[currentReading+5] & 0xFF;
			long l6 = buffer[currentReading+6] & 0xFF;
			long l7 = buffer[currentReading+7] & 0xFF;
			currentReading+=8;
			
			long r0 = l0 << 56;
			long r1 = l1 << 48;
			long r2 = l2 << 40;
			long r3 = l3 << 32;
			long r4 = l4 << 24;
			long r5 = l5 << 16;
			long r6 = l6 << 8;
			long r7 = l7;
			return r0 + r1 + r2 + r3 + r4 + r5 + r6 + r7;
		}
	}
	public void readBytes(byte[] data) {
		int numberByte=data.length;
		if(currentReading+numberByte>dataLength)
			return;
		for(int i=0;i<numberByte;i++)
			data[i]=buffer[currentReading+i];
		currentReading+=numberByte;
	}
	public byte[] readMiniByte() {
		byte numberByte = buffer[currentReading++];
		if(numberByte==0)
			return null;
		byte[] data=new byte[numberByte];
		for(int i=0;i<numberByte;i++)
			data[i]=buffer[currentReading+i];
		currentReading+=numberByte;
		return data;
	}
	
	public float readFloat() {return Float.intBitsToFloat(readInt());}
	public double readDouble() {return Double.longBitsToDouble(readLong());}
	public byte[] readArrayByte(int numberByte) {
		if(numberByte<1)
			return null;
		else if(currentReading+numberByte>dataLength){
			isMessageCorrect=false;
			return null;
		}else{
			byte[] result=new byte[numberByte];
			for(int i=0;i<numberByte;i++)
				result[i]=buffer[currentReading+i];
			currentReading+=numberByte;
			return result;
		}
	}
	public String readString(){
		if(dataLength-currentReading<2){
			isMessageCorrect=false;
			return "";
		}
		int utflen = (((buffer[currentReading] & 0xff) << 8) | (buffer[currentReading+1] & 0xff));
		if(dataLength-currentReading<utflen+2){
			isMessageCorrect=false;
			return "";
		}
		byte[] bytearr = null;
		char[] chararr = null;
//		if(data.length<utflen){
			bytearr = new byte[utflen*2];
			chararr = new char[utflen*2];
//		}
		
		int c, char2, char3;
		int count = 0;
		int chararr_count=0;
		
		for(int i=0;i<utflen;i++)
			bytearr[i]=buffer[i+currentReading+2];
		
//		int lengthTemp=data.length-(utflen+2);
//		byte[] temp=new byte[lengthTemp];
//		for(int i=0;i<lengthTemp;i++)
//			temp[i]=data[utflen+2+i];
//		data=temp;
		currentReading=(short) (currentReading+utflen+2);
		
		while (count < utflen) {
			c = (int) bytearr[count] & 0xff;
			if (c > 127) break;
			count++;
			chararr[chararr_count++]=(char)c;
		}
		
		while (count < utflen) {
			c = (int) bytearr[count] & 0xff;
			switch (c >> 4) {
				case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
					/* 0xxxxxxx*/
					count++;
					chararr[chararr_count++]=(char)c;
					break;
				case 12: case 13:
					/* 110x xxxx   10xx xxxx*/
					count += 2;
					if (count > utflen){
//						throw new UTFDataFormatException("malformed input: partial character at end");
						return "";
					}
					char2 = (int) bytearr[count-1];
					if ((char2 & 0xC0) != 0x80){
//						throw new UTFDataFormatException("malformed input around byte " + count);
						return "";
					}
					chararr[chararr_count++]=(char)(((c & 0x1F) << 6) |
							(char2 & 0x3F));
					break;
				case 14:
					/* 1110 xxxx  10xx xxxx  10xx xxxx */
					count += 3;
					if (count > utflen){
//						throw new UTFDataFormatException("malformed input: partial character at end");
						return "";
					}
					char2 = (int) bytearr[count-2];
					char3 = (int) bytearr[count-1];
					if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80)){
//						throw new UTFDataFormatException("malformed input around byte " + (count-1));
						return "";
					}
					chararr[chararr_count++]=(char)(((c     & 0x0F) << 12) |
							((char2 & 0x3F) << 6)  |
							((char3 & 0x3F) << 0));
					break;
				default:
					/* 10xx xxxx,  1111 xxxx */
//					throw new UTFDataFormatException("malformed input around byte " + count);
					return "";
			}
		}
		// The number of chars produced may be less than utflen
		return new String(chararr, 0, chararr_count);
	}
}