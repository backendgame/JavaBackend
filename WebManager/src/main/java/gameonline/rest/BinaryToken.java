package gameonline.rest;

import java.util.Base64;

public class BinaryToken {
	public long userId;
	public long timeOut;
	
	public BinaryToken() {}
	public BinaryToken(long userId,long timeOut) {this.userId=userId;this.timeOut=timeOut;}
	
	
	public long tokenToLong(String encodeString) {
		byte[] buffer = Base64.getDecoder().decode(encodeString);
		if(buffer==null || buffer.length!=8)
			return -1;
		long l0 = buffer[0] & 0xFF;
		long l1 = buffer[1] & 0xFF;
		long l2 = buffer[2] & 0xFF;
		long l3 = buffer[3] & 0xFF;
		long l4 = buffer[4] & 0xFF;
		long l5 = buffer[5] & 0xFF;
		long l6 = buffer[6] & 0xFF;
		long l7 = buffer[7] & 0xFF;
		
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
	public String longToToken(long v) {
		byte[] data = new byte[8];
		data[0] = (byte)(v >>> 56);
		data[1] = (byte)(v >>> 48);
		data[2] = (byte)(v >>> 40);
		data[3] = (byte)(v >>> 32);
		data[4] = (byte)(v >>> 24);
		data[5] = (byte)(v >>> 16);
		data[6] = (byte)(v >>>  8);
		data[7] = (byte)(v >>>  0);
		return Base64.getEncoder().encodeToString(data);
	}
	
	public String toHashString() {
		long v = userId;
		byte[] data = new byte[16];
		data[0] = (byte)(v >>> 56);
		data[1] = (byte)(v >>> 48);
		data[2] = (byte)(v >>> 40);
		data[3] = (byte)(v >>> 32);
		data[4] = (byte)(v >>> 24);
		data[5] = (byte)(v >>> 16);
		data[6] = (byte)(v >>>  8);
		data[7] = (byte)(v >>>  0);
		
		v=timeOut;
		data[8]  = (byte)(v >>> 56);
		data[9]  = (byte)(v >>> 48);
		data[10] = (byte)(v >>> 40);
		data[11] = (byte)(v >>> 32);
		data[12] = (byte)(v >>> 24);
		data[13] = (byte)(v >>> 16);
		data[14] = (byte)(v >>>  8);
		data[15] = (byte)(v >>>  0);
		return Base64.getEncoder().encodeToString(data);
	}
	
	public boolean decode(String encodeString) {
		byte[] buffer = Base64.getDecoder().decode(encodeString);
		if(buffer==null || buffer.length!=16)
			return false;
		
		long l0 = buffer[0] & 0xFF;
		long l1 = buffer[1] & 0xFF;
		long l2 = buffer[2] & 0xFF;
		long l3 = buffer[3] & 0xFF;
		long l4 = buffer[4] & 0xFF;
		long l5 = buffer[5] & 0xFF;
		long l6 = buffer[6] & 0xFF;
		long l7 = buffer[7] & 0xFF;
		
		long r0 = l0 << 56;
		long r1 = l1 << 48;
		long r2 = l2 << 40;
		long r3 = l3 << 32;
		long r4 = l4 << 24;
		long r5 = l5 << 16;
		long r6 = l6 << 8;
		long r7 = l7;
		userId = r0 + r1 + r2 + r3 + r4 + r5 + r6 + r7;
		
		l0 = buffer[8] & 0xFF;
		l1 = buffer[9] & 0xFF;
		l2 = buffer[10] & 0xFF;
		l3 = buffer[11] & 0xFF;
		l4 = buffer[12] & 0xFF;
		l5 = buffer[13] & 0xFF;
		l6 = buffer[14] & 0xFF;
		l7 = buffer[15] & 0xFF;
		
		r0 = l0 << 56;
		r1 = l1 << 48;
		r2 = l2 << 40;
		r3 = l3 << 32;
		r4 = l4 << 24;
		r5 = l5 << 16;
		r6 = l6 << 8;
		r7 = l7;
		timeOut = r0 + r1 + r2 + r3 + r4 + r5 + r6 + r7;
		
		return userId!=0 && timeOut!=0;
	}
}
