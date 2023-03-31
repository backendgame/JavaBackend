package backendgame;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.client.ClientOneHit;

class Server_BackendGameTest {

	public static String stringToArray(String str) {
		byte[] data = str.getBytes();
		String result = ""+data[0];
		for(int i=1;i<data.length;i++)
			result = result + "," + data[i];
		return "new String(new byte[] {"+result+"});";
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
		long l = System.currentTimeMillis();
		new ClientOneHit("192.168.1.14",1989) {
			
			@Override
			public void onReceiveMessage(MessageReceiving arg0) {
				for(int i=0;i<1000;i++)
					System.out.println(arg0.readString());
				System.out.println(arg0.readLong());
			}
			
			@Override
			public MessageSending doSendMessage() {
				MessageSending messageSending=new MessageSending((short) 1122);
				messageSending.writeInt(123789);
				messageSending.writeString("Dương Đức Trí");
				return messageSending;
			}
		}.run();
		System.out.println("Finish : "+(System.currentTimeMillis()-l));
	}

}
