package gameonline.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

public class Config {
	public static final String IP_Cloud = "192.168.1.1";
	public static final int PORT_Cloud = 80;
	
    public static final String Domain = "backendgame.com";
	public static final String Gmail_Send = "richard@aioboardgame.com";
    public static final String Gmail_AppPassword = "ykazbturfdvztgrq";
    
    
    
    
    
    
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
