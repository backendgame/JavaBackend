package richard;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Lib {
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void deleteFolder(File file) {
		if(file.isDirectory()){
			File[] listfile = file.listFiles();
			for(File f:listfile)
				deleteFolder(f);
		}
		file.delete();
	}
	
	public static final void logClientOnehit(ClientOneHit client,MessageSending messageSending,MessageReceiving messageReceiving) {
		short _cmd=messageSending.CMD;
//		if(_cmd==CMD_SERVER.SERVERSQL_GAMEMANAGER100_NGOCMANH_BANNER)return;
//		if(_cmd==CMD_SERVER.SERVERSQL_GAMEMANAGER101_NGOCMANH_KEYADS)return;
//		if(_cmd==CMD_SERVER.SERVERSQL_GAMEMANAGER102_NGOCMANH_UPDATE)return;
		
		
		if(_cmd==CMD_ONEHIT.GameTowerDefense_Get_Data) return;
		if(_cmd==CMD_ONEHIT.GameTowerDefense_Update_Data) return;
		
		String _cmdSend=CMD_ONEHIT.getCmdName(_cmd);
		if(_cmdSend==null)
			_cmdSend="CMD("+_cmd+")";
		
		if(messageReceiving==null)
			System.out.println("ClientSend "+_cmdSend+" : "+messageSending.currentWriter+" byte ⟶  0 byte	("+(System.currentTimeMillis()-client.timeBeginProcess)+") "+TimeManager.gI().getStringTime()+"	"+client.ip+":"+client.port);
		else
			if(messageReceiving.validate())
				System.out.println("ClientSend "+_cmdSend+" : "+messageSending.currentWriter+" byte ⟶  "+getMemory(messageReceiving.buffer.length)+"	("+(System.currentTimeMillis()-client.timeBeginProcess)+" ms) "+TimeManager.gI().getStringTime()+"	"+client.ip+":"+client.port);
			else
				System.out.println("***ERROR clientOnehit read("+messageReceiving.currentReading+") avaiable("+messageReceiving.avaiable()+" byte)****  ClientSend "+_cmdSend+" : "+messageSending.currentWriter+" byte ⟶  "+getMemory(messageReceiving.buffer.length)+"	("+(System.currentTimeMillis()-client.timeBeginProcess)+" ms) "+TimeManager.gI().getStringTime()+"	"+client.ip+":"+client.port);
	}
	public static final void logClientOnehitNotExist(ClientOneHit client,MessageSending messageSending) {
		String _cmdSend=CMD_ONEHIT.getCmdName(messageSending.CMD);
		if(_cmdSend==null)
			_cmdSend="CMD("+messageSending.CMD+")";
		System.out.println("***ERROR CMD not found*** ⟶ "+_cmdSend+" "+messageSending.currentWriter+"  byte	("+(System.currentTimeMillis()-client.timeBeginProcess)+") "+TimeManager.gI().getStringTime()+"	"+client.ip+":"+client.port);
	}
	
	public static final String getStringException(Exception e) {
		String s = e.toString();
		StackTraceElement[] list = e.getStackTrace();
		if(list!=null)
			for(StackTraceElement st : list)
				s=s+"\n"+st;
		return s;
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
	
	
	public static final void sendEmail(final String gmail, final String appPassword, final String emailTo, final String subject, final String content) {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(gmail, appPassword);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(gmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
			message.setSubject(subject);
			message.setText(content);
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static final void sendHtmlMail(final String gmail,final String appPassword,final String emailTo,final String subject,final String html) {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(gmail, appPassword);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(gmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
			message.setSubject(subject);
			message.setDataHandler(new DataHandler(new DataSource() {
				@Override public InputStream getInputStream() throws IOException {
		            if (html == null) throw new IOException("html message is null!");
		            return new ByteArrayInputStream(html.getBytes());
		        }

		        @Override public OutputStream getOutputStream() throws IOException {
		            throw new IOException("This DataHandler cannot write HTML");
		        }

		        @Override public String getContentType() {
		            return "text/html";
		        }

		        @Override public String getName() {
		            return "HTMLDataSource";
		        }
			}));
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
