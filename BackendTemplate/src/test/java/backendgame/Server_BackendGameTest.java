package backendgame;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

class Server_BackendGameTest {

	public static String stringToArray(String str) {
		byte[] data = str.getBytes();
		String result = ""+data[0];
		for(int i=1;i<data.length;i++)
			result = result + "," + data[i];
		return "new String(new byte[] {"+result+"});";
//		return "new String(new byte[] {108,105,99,101,110,99,101,46,98,97,99,107,101,110,100,103,97,109,101,46,99,111,109});";
	}
	
	public static final String getName() {
		try {
			String s = null;
			while (s == null || s.equals("null"))
				s = Jsoup.connect("https://www.behindthename.com/random/random.php?number=4&sets=1&gender=both&surname=&usage_eng=1").get().getElementsByClass("random-results").get(0).text().replaceAll(" ", "").replaceAll("'", "").replaceAll("`", "");
			return s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Test void test() {
		String s = "licence.backendgame.com";
//		byte[] data = s.getBytes();
//		
//		System.out.print("[");
//		for(int i=0;i<data.length;i++)
//			System.out.print(data[i]+",");
//		System.out.println("]");
//		
//		String str = new String(new byte[] {108,105,99,101,110,99,101,46,98,97,99,107,101,110,100,103,97,109,101,46,99,111,109});
//		System.out.println(str);
		
//		System.out.println(stringToArray(s));
//		String aaa = new String(new byte[] {108,105,99,101,110,99,101,46,98,97,99,107,101,110,100,103,97,109,101,46,99,111,109});
//		System.out.println(aaa);
		
		for(int i=0;i<100;i++)
			System.out.println(getName());
		
	}

}
