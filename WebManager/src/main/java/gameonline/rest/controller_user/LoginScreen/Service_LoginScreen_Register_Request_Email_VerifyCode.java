package gameonline.rest.controller_user.LoginScreen;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dynamodb.TableDynamoDB_AccountLogin;
import dynamodb.TableDynamoDB_UserData;
import gameonline.config.Config;
import gameonline.rest.BaseVariable;
import gameonline.rest.MyRespone;
import gameonline.rest.SystemConstant;

public class Service_LoginScreen_Register_Request_Email_VerifyCode extends BaseVariable {
	@Email
	@NotEmpty
	public String email;
	
	public MyRespone respone() {
		long verifyCode = randomCache.getVerifyCode();
		
		Item item = databaseAccount.getItem(email); 
		if(item==null){
			long userId = randomCache.randomTimeId();
			long timeTTL = System.currentTimeMillis()/1000 + 28800; 
			
			if (databaseAccount.create(email, timeTTL, getIp(), userId) == false)
				return new MyRespone(MyRespone.STATUS_InternalServerError, TableDynamoDB_AccountLogin.TABLE_NAME+" exist "+email);
			
			HashMap<String, Object> infoVerifyCode = new HashMap<String, Object>();
			infoVerifyCode.put(TableDynamoDB_UserData.Info_VerifyCode, verifyCode);
			infoVerifyCode.put(TableDynamoDB_UserData.Info_VerifyTime, System.currentTimeMillis()+28800000);
			if(databaseUserData.insertPlayer(userId, timeTTL, infoVerifyCode)==false)
				return new MyRespone(MyRespone.STATUS_InternalServerError, "insert UserData error");
			
			threadPool.runThread(new Runnable() {
				public void run() {
					sendEmail(Config.Gmail_Send, Config.Gmail_AppPassword, email, "VerifyCode (avaiable 60s)", "VerifyCode : "+verifyCode);		
				}
			});
			
			ObjectNode node = new ObjectMapper().createObjectNode();
			node.put(SystemConstant.USERID, userId);
			
			return new MyRespone(MyRespone.STATUS_Success).setData(node);
		}else {
			if(item.hasAttribute(TableDynamoDB_AccountLogin.ATTRIBUTE_TTL))
				return new MyRespone(MyRespone.STATUS_Warning).setMessage("a VerifyCode sent to " + email);
			else				
				return resExisted;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public final void sendEmail(final String gmail, final String appPassword, final String emailTo, final String subject, final String content) {
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
	
	public final void sendHtmlMail(final String gmail,final String appPassword,final String emailTo,final String subject,final String html) {
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
