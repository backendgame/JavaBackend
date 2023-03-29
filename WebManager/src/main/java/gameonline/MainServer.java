package gameonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

import dynamodb.TableDynamoDB_AccountLogin;
import gameonline.config.Config;
import gameonline.config.Swagger2Config;
import gameonline.rest.BaseVariable;
import gameonline.rest.controller_user.Controller001_LoginScreen;
import richard.Lib;
import richard.threadpool.ThreadPool;

@SpringBootApplication
public class MainServer {


	public static void main(String[] args) {
//		Controller100_LoginScreen_Device.PATH = Swagger2Config.getPath(Controller100_LoginScreen_Device.class);
//
		Controller001_LoginScreen.PATH = Swagger2Config.getPath(Controller001_LoginScreen.class);
//		Controller002_HomeScreen.PATH = Swagger2Config.getPath(Controller002_HomeScreen.class);
//		Controller003_TableScreen.PATH = Swagger2Config.getPath(Controller003_TableScreen.class);
//		Controller004_TableRowScreen.PATH = Swagger2Config.getPath(Controller004_TableRowScreen.class);
//		Controller008_TileScreen.PATH = Swagger2Config.getPath(Controller008_TileScreen.class);
//		Controller009_RealtimeMultiplayerScreen.PATH = Swagger2Config.getPath(Controller009_RealtimeMultiplayerScreen.class);
//		Controller010_BillingScreen.PATH = Swagger2Config.getPath(Controller010_BillingScreen.class);

		ThreadPool.gI().setNumberThread(128);
		ThreadPool.gI().setScheduleBuffer(32);

		BaseVariable.setupGlobalService();

		
		String email = "richard.gamestudio@gmail.com";
		TableDynamoDB_AccountLogin.gI().changePassword(email, "22222444444");
//		TableDynamoDB_AccountLogin databaseAccount = TableDynamoDB_AccountLogin.gI();
////		Item item = databaseAccount.getItem(email);
//		
////		Item item = databaseAccount.table.updateItem(
////				new UpdateItemSpec().withPrimaryKey(TableDynamoDB_AccountLogin.HASH_KEY, email)
////				
////				.withUpdateExpression("SET " + TableDynamoDB_AccountLogin.ATTRIBUTE_TTL + " = :ttl")
////				.withValueMap(new ValueMap().withNumber(":ttl", (System.currentTimeMillis()+TimeManager.A_MONTH_MILLISECOND)/1000))
////				.withExpected(new Expected(email).exists())
//////				.withConditionExpression("attribute_exists("+TableDynamoDB_AccountLogin.ATTRIBUTE_TTL+")")
////				.withReturnValues(ReturnValue.ALL_NEW)
////				).getItem();
//		
//		Item item = null;
//		
//		try {
//			item = databaseAccount.table.updateItem(new UpdateItemSpec()
//					.withPrimaryKey(TableDynamoDB_AccountLogin.HASH_KEY, email)
//					.withAttributeUpdate(new AttributeUpdate(TableDynamoDB_AccountLogin.ATTRIBUTE_TTL).put((System.currentTimeMillis()+TimeManager.A_MONTH_MILLISECOND)/1000))
//					.withExpected(new Expected(TableDynamoDB_AccountLogin.HASH_KEY).exists())
//					.withReturnValues(ReturnValue.ALL_NEW)
//					).getItem();
//			
//			System.out.println(item.toJSONPretty());
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("*******************************************************************************************************************************************************************");
//		System.out.println("*******************************************************************************************************************************************************************");
//		System.out.println("*******************************************************************************************************************************************************************");
//		System.exit(0);
		
		
		SpringApplication.run(MainServer.class, args);
		String ip = Lib.getIp().get(0);
		System.out.println("***********************************************************->"+ip);
		System.out.println("Your API :	http://localhost/swagger-ui.html");
		System.out.println("Your API :	http://"+ip+":" + Config.PORT_Cloud + "/swagger-ui.html");
		System.out.println("Your login page :	http://"+ip+":" + Config.PORT_Cloud + "/login");
		System.out.println("Your register page :	http://"+ip+":" + Config.PORT_Cloud + "/register");
		System.out.println("***********************************************************");

		System.out.println("Danh sách đường dẫn gọi API : Cộng thêm String trong class API để lấy full đường dẫn cho vào thymeleaf");
//		System.out.println("Ví dụ : " + Controller004_TableRowScreen.PATH + "/" + API.TABLERow_QuerryAccountData_ByLatest);
//		System.out.println("TableInfo : " + Controller003_TableScreen.PATH + "/" + API.TABLE_GetInfo_Table);
	}

	@Configuration
	public class CustomContainer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
		public void customize(ConfigurableServletWebServerFactory factory) {
			factory.setPort(Config.PORT_Cloud);
		}
	}
}
