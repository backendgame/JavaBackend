package richard;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

public abstract class ClientOneHit implements Runnable{
	private Socket socket;
	private OutputStream os;
	private InputStream is;
	
	public String ip;
	public int port;
	protected boolean isOnlySend;
	protected long timeBeginProcess;
	public ClientOneHit(String ipAdress,int PORT) {
		ip=ipAdress;
		port = PORT;
		isOnlySend=false;
		
	}
	public ClientOneHit(short regionId) {
//		ip="192.168.1.162";
//		ip="192.168.1.29";
		ip="region"+regionId+".binarybackend.com";
		port = 1989;
		isOnlySend=false;
	}
	
	protected boolean tryConnect(String _ip,int _port) {
		clearSocket();
		socket=new Socket();
		try {
			socket.connect(new InetSocketAddress(_ip, _port),2589);
		} catch (IOException e2) {
			try {socket.close();} catch (IOException e) {}
			return false;
		}
		try {
			is=socket.getInputStream();
			os=socket.getOutputStream();
			ip=_ip;
			port=_port;
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean doConnectOnlyOverrideForSpecial() {
		if(ip==null || port<1)
			return false;
		else
			return tryConnect(ip, port);
	}
	
	
	public void sendIdToGetData(int id,short _bufferReceive,int offset) throws IOException, InterruptedException {
		datatransfer[0]	=(byte)(id>>>24);
		datatransfer[1]	=(byte)(id>>>16);
		datatransfer[2]	=(byte)(id>>>8);
		datatransfer[3]	=(byte)id;
		
		datatransfer[4]	= (byte) (_bufferReceive>>>8);
		datatransfer[5]	= (byte) _bufferReceive;
		
		datatransfer[6]	=(byte)(offset>>>24);
		datatransfer[7]	=(byte)(offset>>>16);
		datatransfer[8]	=(byte)(offset>>>8);
		datatransfer[9]	=(byte)offset;
		
		os.write(datatransfer, 0, 10);
	}
	private byte readBigMessage() throws IOException, InterruptedException {
		if(waitForData(8)==false)
			return SOCKET_BIG_MESSAGE_1;
		int ch3,ch4;
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		is.read(datatransfer, 0, 8);//Đọc dataLength của BigMessage
		
		ch1 = datatransfer[0] & 0xFF;
		ch2 = datatransfer[1] & 0xFF;
		ch3 = datatransfer[2] & 0xFF;
		ch4 = datatransfer[3] & 0xFF;
		int dataLength = ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
		
		ch1 = datatransfer[4] & 0xFF;
		ch2 = datatransfer[5] & 0xFF;
		ch3 = datatransfer[6] & 0xFF;
		ch4 = datatransfer[7] & 0xFF;
		int socketSendBufferSize = ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(socketSendBufferSize>16384)
			socketSendBufferSize = 16384;
		int receiveBufferSize = socketSendBufferSize - 6;
		tmp=new byte[6];
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		byte[] data = new byte[dataLength];
		int offset = 0;
		while(offset+receiveBufferSize<dataLength) {
			sendIdToGetData(offset, (short) receiveBufferSize, offset);
			if(waitForData(6)==false)
				return SOCKET_BIG_MESSAGE_2;
			
			is.read(tmp, 0, 6);
			
			if(datatransfer[0]!=tmp[0] || datatransfer[1]!=tmp[1] || datatransfer[2]!=tmp[2] || datatransfer[3]!=tmp[3] || datatransfer[4]!=tmp[4] || datatransfer[5]!=tmp[5])
				return SOCKET_BIG_MESSAGE_3; 
			
			if(waitForData(receiveBufferSize)==false)
				return SOCKET_BIG_MESSAGE_4;
			
			is.read(data, offset, receiveBufferSize);
			
			offset+=receiveBufferSize;
		}
		sendIdToGetData(offset, (short) (dataLength - offset), offset);
		if(waitForData(6)==false)
			return SOCKET_BIG_MESSAGE_5;
		
		is.read(tmp, 0, 6);
		if(datatransfer[0]!=tmp[0] || datatransfer[1]!=tmp[1] || datatransfer[2]!=tmp[2] || datatransfer[3]!=tmp[3] || datatransfer[4]!=tmp[4] || datatransfer[5]!=tmp[5])
			return SOCKET_BIG_MESSAGE_6; 
		
		if(waitForData(dataLength - offset)==false)
			return SOCKET_BIG_MESSAGE_7;
		
		is.read(data, offset, dataLength - offset);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////Alert Close Socket
		sendIdToGetData(offset, (short) 0, offset);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		for(int i=0;i<dataLength;i++)
			data[i]^=validateCode;
		
		
		BigMessageReceiving messageReceiving = new BigMessageReceiving((short) 0, data, (short) dataLength);
		messageReceiving.DATALENGTH = dataLength;
		messageReceiving.buffer = data;
		onReceiveMessage(messageReceiving);
		Lib.logClientOnehit(this, messageSending, messageReceiving);
//		System.out.println(receiveBufferSize+"	dataLength("+dataLength+")-->" + (dataLength - offset)+"	--->"+offset+"		==>"+messageReceiving.buffer.length);
//		System.out.println(Arrays.toString(data));
		return 0;
	}
	
	private byte[] datatransfer;
	private byte validateCode=0;
	private short length;
	private int ch1,ch2;
	private byte[] tmp;
	private final byte processSocket(MessageSending messageSending) {
		datatransfer=new byte[11+messageSending.currentWriter];
		try {
			if(waitForData(8))
				if(is.available()>8)
					return SOCKET_ERROR_1;
				else				
					is.read(datatransfer, 0, 8);
			else
				return SOCKET_TIME_OUT_1;
		} catch (IOException | InterruptedException e) {
			return SOCKET_ERROR_1;
		}
		
		validateCode=datatransfer[3];
		datatransfer[0]=(byte) (datatransfer[0]^validateCode);
		datatransfer[1]=(byte) (datatransfer[1]^validateCode);
		datatransfer[2]=(byte) (datatransfer[2]^validateCode);
		datatransfer[3]=(byte) (datatransfer[4]^validateCode);
		datatransfer[4]=(byte) (datatransfer[5]^validateCode);
		datatransfer[5]=(byte) (datatransfer[6]^validateCode);
		datatransfer[6]=(byte) (datatransfer[7]^validateCode);
		
		length=messageSending.currentWriter;
		tmp=messageSending.data;
		for(short i=0;i<length;i++)
			datatransfer[i+11]=(byte) (tmp[i]^validateCode);
		
		length+=2;
		datatransfer[7]	= (byte) (length>>>8);
		datatransfer[8]	= (byte) length;
		
		length=messageSending.CMD;
		datatransfer[9]	= (byte) ((length>>>8)^validateCode);
		datatransfer[10]= (byte) (length^validateCode);

		try {os.write(datatransfer);} catch (IOException e) {return SOCKET_ERROR_2;}
		if (isOnlySend)return 0;

		try {
			if(waitForData(2))
				is.read(datatransfer, 0, 2);
			else
				return SOCKET_TIME_OUT_2;
		} catch (IOException | InterruptedException e) {return SOCKET_ERROR_2;}
		/////////////////////////////////////////////////////////////////////////////////BigMessage
		if(datatransfer[0]==-128 && datatransfer[1]==0)
			try {
				return readBigMessage();
			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
				return SOCKET_BIG_MESSAGE_9;
			}
		/////////////////////////////////////////////////////////////////////////////////
		ch1 = datatransfer[0] & 0xFF;
		ch2 = datatransfer[1] & 0xFF;
		length = (short)((ch1 << 8) + (ch2 << 0));
		
		if(length<0) {
			if(length==-7)
				Lib.logClientOnehitNotExist(this, messageSending);
			return ERRORCODE_LENGTH_SERVER_ERROR;
		}else if(length==0) {
			Lib.logClientOnehit(this, messageSending, null);
			return 0;
		}
		
		if(length>datatransfer.length)
			datatransfer=new byte[length];
		try {
			if(waitForData(length))
				is.read(datatransfer, 0, length);
			else
				return SOCKET_TIME_OUT_3;
		} catch (IOException | InterruptedException e) {return SOCKET_ERROR_3;}
		
		for(short i=0;i<length;i++)
			datatransfer[i]=(byte) (datatransfer[i]^validateCode);
		
		messageReceiving=new MessageReceiving((short) 0,datatransfer,length);
		onReceiveMessage(messageReceiving);
		Lib.logClientOnehit(this, messageSending, messageReceiving);
		return 0;
	}
	
	@Override public void run() {
		timeBeginProcess=System.currentTimeMillis();
		messageSending=doSendMessage();
		if(messageSending==null)
			onNetworkError(ERRORCODE_NO_MESSAGE_SENDING);
		else {
			if(doConnectOnlyOverrideForSpecial()) {
				byte errorCode=processSocket(messageSending);
				if(errorCode!=0)
					onNetworkError(errorCode);
			}else
				onNetworkError(ERRORCODE_CREATE_SOCKET);
		}
		clearSocket();
	}

	public void runThread() {new Thread(this).start();}
	
	protected final void clearSocket() {
		if(is!=null)try {is.close();} catch (IOException e) {}
		if(os!=null)try {os.close();} catch (IOException e) {}
		if(socket!=null){socket.isInputShutdown();socket.isOutputShutdown();try {socket.close();} catch (IOException e) {}}
	}
	
	
	public void onNetworkError(byte errorCode){}
	public abstract MessageSending doSendMessage();
	public abstract void onReceiveMessage(MessageReceiving messageReceiving);
	public final String convertTimeToString(long time) {
		int dd, mm, yyyy, hour, minus;
		Date date = new Date(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		dd = calendar.get(Calendar.DATE);
		mm = calendar.get(Calendar.MONTH);
		yyyy = calendar.get(Calendar.YEAR);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minus = calendar.get(Calendar.MINUTE);
		return dd + "/" + (mm + 1) + "/" + yyyy + " (" + hour + ":" + minus + ")";
	}
	public final String tS(String s,int space) {
		String p=s;
		for(int i=s.length()/8;i<space;i++)
			p=p+"	";
		return p;
	}
	public final String tSs(String s,int space) {
		String p=s;
		for(int i=s.length()/8;i<space;i++)
			p=p+"	";
		p=p+" : ";
		return p;
	}
	
	private MessageSending messageSending;
	private MessageReceiving messageReceiving;
	
	private boolean waitForData(int length) throws IOException, InterruptedException {
		if (is.available() < length)Thread.sleep(1);else return true;
		if (is.available() < length)Thread.sleep(1);else return true;
		if (is.available() < length)Thread.sleep(2);else return true;
		if (is.available() < length)Thread.sleep(2);else return true;
		if (is.available() < length)Thread.sleep(2);else return true;
		if (is.available() < length)Thread.sleep(3);else return true;
		if (is.available() < length)Thread.sleep(3);else return true;
		if (is.available() < length)Thread.sleep(3);else return true;
		if (is.available() < length)Thread.sleep(3);else return true;
		if (is.available() < length)Thread.sleep(5);else return true;
		if (is.available() < length)Thread.sleep(5);else return true;
		if (is.available() < length)Thread.sleep(5);else return true;
		if (is.available() < length)Thread.sleep(5);else return true;
		for(byte i=0;i<589;i++)
			if (is.available() < length)Thread.sleep(10);else return true;
		return false;
	}
	
	
//	private boolean wait1Second(int length) throws IOException, InterruptedException {
//		for(byte i=0;i<100;i++)
//			if (is.available() < length)Thread.sleep(10);else return true;
//		return false;
//	}
//	private boolean waitPing(int length) throws IOException, InterruptedException {
//		for(byte i=0;i<8;i++)
//			if(wait1Second(length))
//				return true;
//			else
//				os.write(0);
//		return false;
//	}
	
	public static final byte ERRORCODE_NO_MESSAGE_SENDING			=1;
	public static final byte ERRORCODE_CREATE_SOCKET				=2;
	public static final byte ERRORCODE_LENGTH_SERVER_ERROR			=3;
	
	public static final byte SOCKET_TIME_OUT_1						=100;
	public static final byte SOCKET_TIME_OUT_2						=101;
	public static final byte SOCKET_TIME_OUT_3						=102;
	
	public static final byte SOCKET_ERROR_1							=110;
	public static final byte SOCKET_ERROR_2							=111;
	public static final byte SOCKET_ERROR_3							=112;
	
	public static final byte SOCKET_BIG_MESSAGE_1					=81;
	public static final byte SOCKET_BIG_MESSAGE_2					=82;
	public static final byte SOCKET_BIG_MESSAGE_3					=83;
	public static final byte SOCKET_BIG_MESSAGE_4					=84;
	public static final byte SOCKET_BIG_MESSAGE_5					=85;
	public static final byte SOCKET_BIG_MESSAGE_6					=86;
	public static final byte SOCKET_BIG_MESSAGE_7					=87;
	public static final byte SOCKET_BIG_MESSAGE_8					=88;
	public static final byte SOCKET_BIG_MESSAGE_9					=89;
}