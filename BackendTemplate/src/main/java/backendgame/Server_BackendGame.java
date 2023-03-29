package backendgame;

import java.lang.reflect.Field;
import java.net.Socket;

import backendgame.config.CMD_ONEHIT;
import backendgame.onehit.BaseOnehit_AiO;
import bgcore.core.OneHitProcessing;
import bgcore.core.ThreadPool;
import bgcore.core.TimeManager;
import bgcore.core.log.ILogOnehit_CMD_NotExist;
import bgcore.core.log.ILogOnehit_OnMessage;
import bgcore.core.server.ServerBackendGame_EncryptingOnehit;

public class Server_BackendGame {
	public static ServerBackendGame_EncryptingOnehit serverOnehit;
	
	
	public static void main(String[] args) {long l = System.currentTimeMillis();
		System.out.println("Begin Server at "+TimeManager.gI().getStringTime());
		ThreadPool.gI().setNumberThread(128);
		ThreadPool.gI().setScheduleBuffer(64);
		
		serverOnehit = new ServerBackendGame_EncryptingOnehit("Onehit", "GameServer", 1989);
		BaseOnehit_AiO.setup();
		serverOnehit.set_OnehitProcessing_NotFound(new ILogOnehit_CMD_NotExist() {
			@Override public void showLog(Socket _socket, short _CMD, int lengthReceive) {
				String strCMD = "CMD("+_CMD+")";
				Field[] fields=CMD_ONEHIT.class.getFields();
				for(Field f:fields)
					try {
						if(_CMD==f.getShort(null)) {
							strCMD = f.getName();
							break;
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				System.err.println(_socket.getInetAddress().getHostAddress() + " ➝ " + serverOnehit.getServerName() + strCMD + " not exist ➝ " + lengthReceive + " byte");				
			}
		});
		
		serverOnehit.setOnMessage(new ILogOnehit_OnMessage() {
			@Override public void showLog(Socket _socket, OneHitProcessing process, int lengthClient, int lengthServer) {
				String strCMD = "CMD("+process.cmd+")";
				Field[] fields = CMD_ONEHIT.class.getFields();
				for(Field f:fields)
					try {
						if(process.cmd==f.getShort(null)) {
							strCMD = f.getName();
							break;
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				System.out.println("Onehit(" + process.getClass().getSimpleName() + ")	"+strCMD+" : request(" + lengthClient + "b) - respone(" + lengthServer + "b) ➝ " + _socket.getInetAddress().getHostAddress() + "➝" + serverOnehit.getServerName());				
			}
		});
		serverOnehit.start();
		
		
		
		System.out.println("Finish : "+(System.currentTimeMillis()-l));
	}

}
