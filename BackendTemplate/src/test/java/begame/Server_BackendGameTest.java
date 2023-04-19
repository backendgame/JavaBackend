package begame;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

import backendgame.com.core.BGUtility;
import database_game.AdminLoginManager;

class Server_BackendGameTest {

	@Test
	void test() throws IOException {
		BGUtility.traceListMac();
		BGUtility.traceIpAdress();
		
		try {
			System.out.println(BGUtility.getMacAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		AdminLoginManager.gI().setAccount("aaa", "123");
//		AdminLoginManager.gI().deleteAccount("aaa");
		System.out.println(AdminLoginManager.gI().readAdminFile());
	}

	

}
