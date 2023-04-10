package begame;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import backendgame.com.core.OneHitProcessing;
import backendgame.com.core.ThreadPool;
import backendgame.com.core.TimeManager;
import backendgame.com.core.log.ILogOnehit_CMD_NotExist;
import backendgame.com.core.log.ILogOnehit_OnMessage;
import backendgame.com.core.server.ServerBackendGame_EncryptingOnehit;
import backendgame.com.core.server.ServerBackendGame_Http;
import begame.config.CMD_ONEHIT;
import begame.onehit.BaseOnehit_AiO;

public class Server_BackendGame {
	public static ServerBackendGame_EncryptingOnehit serverOnehit;
	
	
	public static void main(String[] args) {long l = System.currentTimeMillis();
		System.out.println("Begin Server at "+TimeManager.gI().getStringTime());
		ThreadPool.gI().setNumberThread(128);
		ThreadPool.gI().setScheduleBuffer(64);
		
		serverOnehit = new ServerBackendGame_EncryptingOnehit("Onehit", "GameServer", 1989);
		BaseOnehit_AiO.setup();
		serverOnehit.set_CMD_NotFound(new ILogOnehit_CMD_NotExist() {
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
		
		new ServerBackendGame_Http("aaa", "bbbb", 80).setDescrip("onehit", serverOnehit).start();
		
		traceIpAdress();
		System.out.println("Finish : "+(System.currentTimeMillis()-l));
	}
	
    public static final void traceListMac() {
        ArrayList<String> _listMac = getListMacAdress();
        for (int i = 0; i < _listMac.size(); i++)
            System.out.println("MacAddress : " + _listMac.get(i));
    }

    public static final ArrayList<String> getListMacAdress() {
        ArrayList<String> _listMac = new ArrayList<>();
        StringBuilder sb = null;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface network = networkInterfaces.nextElement();
                byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                } else {
                    sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
                    }
                    _listMac.add(sb.toString());
                }
            }
        } catch (SocketException e) {
        }
        return _listMac;
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
}
