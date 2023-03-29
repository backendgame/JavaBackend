package gameonline.rest.database.model;

public class DatabaseId {
	public static final byte Device						=0;//DeviceId
	public static final byte SystemAccount				=1;//Username
	public static final byte Facebook					=2;//Token for business
	public static final byte Google						=3;//Google Mail
	public static final byte AdsId						=4;//Adsid
	public static final byte Apple						=5;//User identifier
	public static final byte EmailCode					=6;//Email
	
	public static final String getSubjectAccount(byte databaseId) {
		switch (databaseId) {
			case Device:return "DeviceId";
			case SystemAccount:return "Username";
			case Facebook:return "Token for business";
			case Google:return "Google Mail";
			case AdsId:return "Adsid";
			case Apple:return "User identifier";
			case EmailCode:return "Email";
			default:return null;
		}
	}
}
