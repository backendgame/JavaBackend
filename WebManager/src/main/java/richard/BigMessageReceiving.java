package richard;

public class BigMessageReceiving extends MessageReceiving {
	public int DATALENGTH;
	public int CURRENTREADING;
//	public short CMD;
//	public byte[] buffer;
//	public boolean isMessageCorrect;
	public BigMessageReceiving(short cmd, byte[] DATA, short _dataLength) {
		super(cmd, DATA, _dataLength);
	}

	public BigMessageReceiving(byte[] DATA, short _dataLength) {
		super(DATA, _dataLength);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override public int avaiable() {return DATALENGTH-CURRENTREADING;}

	@Override
	public byte[] getEndByte() {
		int lengClone=DATALENGTH-CURRENTREADING;
		if(lengClone<1)
			return null;
		byte[] dataClone=new byte[lengClone];
		for(int i=0;i<lengClone;i++)
			dataClone[i]=buffer[i+CURRENTREADING];
		return dataClone;
	}

	@Override
	public MessageSending cloneSending(short _cmd_) {
		MessageSending mgClone=new MessageSending(_cmd_);
		mgClone.writeCopyData(buffer,0,DATALENGTH);
		return mgClone;
	}

	@Override public void startBeginRead() {CURRENTREADING = 0;}
	@Override public boolean validate() {return isMessageCorrect && (DATALENGTH-CURRENTREADING)==0;}
	@Override public boolean isCorrect() {return isMessageCorrect;}
	@Override public void reSet() {CURRENTREADING=0;isMessageCorrect=true;}
	@Override public byte discoveryByte() {return buffer[CURRENTREADING];}
	@Override public byte discoveryByte(int _distance) {return buffer[CURRENTREADING+_distance];}
	@Override public void skip(int length) {CURRENTREADING+=length;}

	@Override public boolean readBoolean() {
		if(CURRENTREADING+1>DATALENGTH){
			isMessageCorrect=false;
			return false;
		}else
			return buffer[CURRENTREADING++]!=0;
	}

	@Override public byte readByte() {
		if(CURRENTREADING+1>DATALENGTH){
			isMessageCorrect=false;
			return 0;
		}else
			return buffer[CURRENTREADING++];
	}

	@Override public short readShort() {
		if(CURRENTREADING+2>DATALENGTH){
			isMessageCorrect=false;
			return 0;
		}else{
			int ch1 = buffer[CURRENTREADING] & 0xFF;
			int ch2 = buffer[CURRENTREADING+1] & 0xFF;
			CURRENTREADING+=2;
			return (short)((ch1 << 8) + (ch2 << 0));
		}
	}

	@Override public int readInt() {
		if(CURRENTREADING+4>DATALENGTH){
			isMessageCorrect=false;
			return 0;
		}else{
			int ch1 = buffer[CURRENTREADING] & 0xFF;
			int ch2 = buffer[CURRENTREADING+1] & 0xFF;
			int ch3 = buffer[CURRENTREADING+2] & 0xFF;
			int ch4 = buffer[CURRENTREADING+3] & 0xFF;
			CURRENTREADING+=4;
			return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
		}
	}

	@Override public long readLong() {
		if(CURRENTREADING+8>DATALENGTH){
			isMessageCorrect=false;
			return 0;
		}else{
			long l0 = buffer[CURRENTREADING] & 0xFF;
			long l1 = buffer[CURRENTREADING+1] & 0xFF;
			long l2 = buffer[CURRENTREADING+2] & 0xFF;
			long l3 = buffer[CURRENTREADING+3] & 0xFF;
			long l4 = buffer[CURRENTREADING+4] & 0xFF;
			long l5 = buffer[CURRENTREADING+5] & 0xFF;
			long l6 = buffer[CURRENTREADING+6] & 0xFF;
			long l7 = buffer[CURRENTREADING+7] & 0xFF;
			CURRENTREADING+=8;
			
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

	@Override public void readBytes(byte[] data) {
		int numberByte=data.length;
		if(CURRENTREADING+numberByte>DATALENGTH)
			return;
		for(int i=0;i<numberByte;i++)
			data[i]=buffer[CURRENTREADING+i];
		CURRENTREADING+=numberByte;
	}

	@Override public float readFloat() {return Float.intBitsToFloat(readInt());}
	@Override public double readDouble() {return Double.longBitsToDouble(readLong());}

	@Override public byte[] readArrayByte(int numberByte) {
		if(numberByte<1)
			return null;
		else if(CURRENTREADING+numberByte>DATALENGTH){
			isMessageCorrect=false;
			return null;
		}else{
			byte[] result=new byte[numberByte];
			for(int i=0;i<numberByte;i++)
				result[i]=buffer[CURRENTREADING+i];
			CURRENTREADING+=numberByte;
			return result;
		}
	}

	@Override public String readString() {
		if(DATALENGTH-CURRENTREADING<2){
			isMessageCorrect=false;
			return "";
		}
		int utflen = (((buffer[CURRENTREADING] & 0xff) << 8) | (buffer[CURRENTREADING+1] & 0xff));
		if(DATALENGTH-CURRENTREADING<utflen+2){
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
			bytearr[i]=buffer[i+CURRENTREADING+2];
		
//		int lengthTemp=data.length-(utflen+2);
//		byte[] temp=new byte[lengthTemp];
//		for(int i=0;i<lengthTemp;i++)
//			temp[i]=data[utflen+2+i];
//		data=temp;
//		CURRENTREADING=(short) (CURRENTREADING+utflen+2);
		CURRENTREADING=CURRENTREADING+utflen+2;
		
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
