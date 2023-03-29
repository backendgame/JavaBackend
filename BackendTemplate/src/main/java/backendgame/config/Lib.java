package backendgame.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Random;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.swing.JOptionPane;

public class Lib {/*"src/main/resources/aio.boardgame.json"*/
	public static final long timeEdit=20210928;//20190905;
	public static final long versionCode=20210405;//20190829;//Đổi số này thì bắt buộc client update game trên store
//	public static final long A_DAY_MILLISECOND=86400000;
//	public static final long A_HOUR_MILLISECOND=3600000;
//	public static final long A_MINUTES_MILLISECOND=60000;
	/*IconDefault
	 * UIManager.getIcon("OptionPane.questionIcon")
	 * UIManager.getIcon("OptionPane.errorIcon")
	 * UIManager.getIcon("OptionPane.informationIcon")
	 * UIManager.getIcon("OptionPane.warningIcon")
	 * UIManager.getIcon("FileView.directoryIcon")
	 * UIManager.getIcon("FileView.fileIcon")
	 * UIManager.getIcon("FileView.computerIcon")
	 * UIManager.getIcon("FileView.hardDriveIcon")
	 * UIManager.getIcon("FileView.floppyDriveIcon")
	 * UIManager.getIcon("FileChooser.newFolderIcon")
	 * UIManager.getIcon("FileChooser.upFolderIcon")
	 * UIManager.getIcon("FileChooser.homeFolderIcon")
	 * UIManager.getIcon("FileChooser.detailsViewIcon")
	 * UIManager.getIcon("FileChooser.listViewIcon")
	 * UIManager.getIcon("Tree.expandedIcon")
	 * UIManager.getIcon("Tree.collapsedIcon")
	 * UIManager.getIcon("Tree.openIcon")
	 * UIManager.getIcon("Tree.leafIcon")
	 * UIManager.getIcon("Tree.closedIcon")
	 * */
	
	public static void copyFile(String source,String desc) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis=new FileInputStream(source);
			byte[] data=new byte[fis.available()];
			fis.read(data);
			fos=new FileOutputStream(desc);
			fos.write(data);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(fis!=null)
			try {fis.close();} catch (IOException e) {}
		if(fos!=null)
			try {fos.close();} catch (IOException e) {}
	}
	
	public static void windownClearFileLinuxPath(File file) {
		if(file.isDirectory()){
			File[] listfile = file.listFiles();
			for(File f:listfile)
				windownClearFileLinuxPath(f);
		}else {
			try {
				RandomAccessFile rf=new RandomAccessFile(file, "rw");
				rf.write(new byte[(int) rf.length()]);
				rf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteFolder(File file) {
		if(file.isDirectory()){
			File[] listfile = file.listFiles();
			for(File f:listfile)
				deleteFolder(f);
		}
		System.out.println("Delete : "+file.getPath());
		file.delete();
	}
	
	
	public static void showDialog(String message) {
		JOptionPane.showMessageDialog(null, message);
//		JOptionPane.showMessageDialog(null,"Content","Title",JOptionPane.ERROR_MESSAGE);
	}
	
	public static final String getStringException(Exception e) {
		String s = e.toString();
		StackTraceElement[] list = e.getStackTrace();
		if(list!=null)
			for(StackTraceElement st : list)
				s=s+"\n"+st;
		return s;
	}
	

	
//	"src/main/resources/aio.boardgame.json"
	public static final String getRunableJarPath() throws URISyntaxException {
		return new File(Lib.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
	}
	public static double getProcessCpuLoad() throws Exception {

	    MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();	
	    ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
	    AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

	    if (list.isEmpty())     return Double.NaN;

	    Attribute att = (Attribute)list.get(0);
	    Double value  = (Double)att.getValue();

	    // usually takes a couple of seconds before we get real values
	    if (value == -1.0)      return Double.NaN;
	    // returns a percentage value with 1 decimal point precision
	    return ((int)(value * 1000) / 10.0);
	}
	public static final void createFolder(String path) {
		File fff = new File(path);
		if(fff.exists()==false)
			fff.mkdirs();
	}
	

	
	
	public static final String getStringToLog(byte[] values) {
		String s=""+values[0];
		int n = values.length;
		for(int i=1;i<n;i++)
			s=s+","+values[i];
		return s;
	}
	
	
	public static final long USCLN(long a, long b) {
	    // Nếu a = 0 => ucln(a,b) = b
	    // Nếu b = 0 => ucln(a,b) = a
	    if (a == 0 || b == 0)
	        return a + b;
	    while (a != b)
	        if (a > b)
	            a -= b; // a = a - b
	        else
	            b -= a;
	    return a; // return a or b, bởi vì lúc này a và b bằng nhau
	}
	public static final long BSCNN(long a, long b) {
		if (a >= b)
			for (int i = 1; i < b; i++)
				if ((a * i) % b == 0)
					return a * i;
		else
			for (int k = 1; k < a; k++)
				if ((b * k) % a == 0)
					return b * k;
		return a*b;
	}
	public static final String getMemory(long l) {
		if(l<1024)
			return l+" b";
		if(l<1024*1024) {
			return new DecimalFormat("#.##").format(((double)l)/1024)+" K("+l+")";
		}
		if(l<1024*1024*1024) {
			return new DecimalFormat("#.##").format(((double)l)/(1024*1024))+" M("+l+")";
		}
		return new DecimalFormat("#.##").format(((double)l)/(1024*1024*1024))+" G("+l+")";
	}
//	public final String getMemory(long l) {
//		if(l<1024)
//			return l+" byte";
//		if(l<1024*1024) {
//			return ((float)l)/1024+" K";
//		}
//		if(l<1024*1024*1024) {
//			return ((float)l)/(1024*1024)+" M";
//		}
//		return ((float)l)/(1024*1024*1024)+" G";
//	}
	
	public static final void traceListMac() {
		ArrayList<String>_listMac=getListMacAdress();
		for(int i=0;i<_listMac.size();i++)
			System.out.println("MacAddress : "+_listMac.get(i));
	}
	public static final ArrayList<String> getListMacAdress() {
		ArrayList<String>_listMac=new ArrayList<>();
		StringBuilder sb=null;
		try {
	        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
	        while(networkInterfaces.hasMoreElements()){
	            NetworkInterface network = networkInterfaces.nextElement();
	            byte[] mac = network.getHardwareAddress();
	            if(mac == null){
	            }else{
	                sb = new StringBuilder();
	                for (int i = 0; i < mac.length; i++){
	                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));        
	                }
	                _listMac.add(sb.toString());
	            }
	        }
	    } catch (SocketException e){}
		return _listMac;
	}
//	private byte checkPurchaseStateGoogle(String packageName, String sku, String token) {
//		try {
//			HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//			JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//			GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("/bth_server/database_iap_android/json"+packageName+".json")).createScoped(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER));
//			AndroidPublisher publisher = new AndroidPublisher.Builder(httpTransport, jsonFactory, credential).setApplicationName(packageName).build();
//			ProductPurchase product = publisher.purchases().products().get(packageName, sku, token).execute();
//			Integer purchaseState = product.getPurchaseState();
//			if(purchaseState==0)
//				return 1;//Mua thành công
//			else
//				return 2;//Mua test
//		} catch (Exception ex) {
//			ex.printStackTrace();//Lỗi gì đó
//			return -1;
//		}
//	}
	
	public static long[] makeArrayLongs(long valueBegin,long valueEnd) {
		int length=(int) (valueEnd-valueBegin+1);
		long[] result=new long[length];
		for(int i=0;i<length;i++)
			result[i] = valueBegin+i;
		return result;
	}
	public static int[] makeArrayIntegers(int valueBegin,int valueEnd) {
		int length=valueEnd-valueBegin+1;
		int[] result=new int[length];
		for(int i=0;i<length;i++)
			result[i] = valueBegin+i;
		return result;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static long pow2(int n) {
		if(n<0) {
			System.err.println("ERROR Lib pow2");
			return -1;
		}
		long result=1;
		for(int i=0;i<n;i++)
			result=result*2;
		return result;
	}
	
	public static long bytesToLong(byte[] buffer) {
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
	
	public static final void traceIpAdress() {
		new Thread(new Runnable() {
			public void run() {
				ArrayList<String> listIp=getIp();
				for(String s:listIp)
					System.out.println(s);
			}
		}).start();
	}
	public static final ArrayList<String> getIp() {
		ArrayList<String> listIp=new ArrayList<String>();
		String s;
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements()){
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    Enumeration<InetAddress> ee = n.getInetAddresses();
			    while (ee.hasMoreElements()) {
			    	s=ee.nextElement().getHostAddress();
			    	if(s!=null && s.equals("127.0.0.1")==false && s.startsWith("fe80:0:0:0")==false && s.startsWith("0:0:0:0:0:0")==false)
			    		listIp.add(s);
			    }
			}
			return listIp;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return listIp;
	}
	
//	public static final String getName() {
//		try {
//			String s=null;
//			while(s==null || s.equals("null"))
//				s = Jsoup.connect("https://www.behindthename.com/random/random.php?number=4&sets=1&gender=both&surname=&usage_eng=1").get().getElementsByClass("random-result").get(0).text().replaceAll(" ","").replaceAll("'","").replaceAll("`","");
//			return s;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	
	
	public static final String traceUnsignArray(byte[] arr) {
		if(arr==null || arr.length==0)
			return "[]";
		String result="["+(arr[0]&0xFF);
		int numberRow = arr.length;
		for(int i=1;i<numberRow;i++)
			result=result+","+(arr[i]&0xFF);
		return result+"]";
	}
	public static final String traceArray(byte[] arr) {
		return Arrays.toString(arr);
	}
	public static byte[] readFile(String path) {
		byte[] data=null;
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(path);
			data=new byte[fis.available()];
			fis.read(data);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(fis!=null)
			try {fis.close();} catch (IOException e) {e.printStackTrace();}
		return data;
	}
	
	public static final String StringCharacter = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String randomString(int numberString) {
		Random randomCache = new Random();
		int lengCharacter = StringCharacter.length();
		String result = "";
		for(short i=0;i<numberString;i++)
			result=result+StringCharacter.charAt(randomCache.nextInt(lengCharacter));
		return result;
	}
	
	public static final byte[] dataMinShort=new byte[] {-128,0};
	public static final long POW2_0=1;
	public static final long POW2_1=2;
	public static final long POW2_2=4;
	public static final long POW2_3=8;
	public static final long POW2_4=16;
	public static final long POW2_5=32;
	public static final long POW2_6=64;
	public static final long POW2_7=128;
	public static final long POW2_8=256;
	public static final long POW2_9=512;
	public static final long POW2_10=1024;
	public static final long POW2_11=2048;
	public static final long POW2_12=4096;
	public static final long POW2_13=8192;
	public static final long POW2_14=16384;
	public static final long POW2_15=32768;
	public static final long POW2_16=65536;
	public static final long POW2_17=131072;
	public static final long POW2_18=262144;
	public static final long POW2_19=524288;
	public static final long POW2_20=1048576;
	public static final long POW2_21=2097152;
	public static final long POW2_22=4194304;
	public static final long POW2_23=8388608;
	public static final long POW2_24=16777216;
	public static final long POW2_25=33554432;
	public static final long POW2_26=67108864;
	public static final long POW2_27=134217728;
	public static final long POW2_28=268435456;
	public static final long POW2_29=536870912;
	public static final long POW2_30=1073741824;
	public static final long POW2_31=2147483648L;
	public static final long POW2_32=4294967296L;
	public static final long POW2_33=8589934592L;
	public static final long POW2_34=17179869184L;
	public static final long POW2_35=34359738368L;
	public static final long POW2_36=68719476736L;
	public static final long POW2_37=137438953472L;
	public static final long POW2_38=274877906944L;
	public static final long POW2_39=549755813888L;
	public static final long POW2_40=1099511627776L;
	public static final long POW2_41=2199023255552L;
	public static final long POW2_42=4398046511104L;
	public static final long POW2_43=8796093022208L;
	public static final long POW2_44=17592186044416L;
	public static final long POW2_45=35184372088832L;
	public static final long POW2_46=70368744177664L;
	public static final long POW2_47=140737488355328L;
	public static final long POW2_48=281474976710656L;
	public static final long POW2_49=562949953421312L;
	public static final long POW2_50=1125899906842624L;
	public static final long POW2_51=2251799813685248L;
	public static final long POW2_52=4503599627370496L;
	public static final long POW2_53=9007199254740992L;
	public static final long POW2_54=18014398509481984L;
	public static final long POW2_55=36028797018963968L;
	public static final long POW2_56=72057594037927936L;
	public static final long POW2_57=144115188075855872L;
	public static final long POW2_58=288230376151711744L;
	public static final long POW2_59=576460752303423488L;
	public static final long POW2_60=1152921504606846976L;
	public static final long POW2_61=2305843009213693952L;
	public static final long POW2_62=4611686018427387904L;
	public static final long POW2_63=-9223372036854775808L;
	public static final long[] POW=new long[] {1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,65536,131072,262144,524288,1048576,2097152,4194304,8388608,16777216,33554432,67108864,134217728,268435456,536870912,1073741824,2147483648L,4294967296L,8589934592L,17179869184L,34359738368L,68719476736L,137438953472L,274877906944L,549755813888L,1099511627776L,2199023255552L,4398046511104L,8796093022208L,17592186044416L,35184372088832L,70368744177664L,140737488355328L,281474976710656L,562949953421312L,1125899906842624L,2251799813685248L,4503599627370496L,9007199254740992L,18014398509481984L,36028797018963968L,72057594037927936L,144115188075855872L,288230376151711744L,576460752303423488L,1152921504606846976L,2305843009213693952L,4611686018427387904L,-9223372036854775808L};
}
