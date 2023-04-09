package gameonline.rest.dto;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.GsonBuilder;

import backendgame.com.core.MessageReceiving;
import gameonline.rest.SystemConstant;
import gameonline.rest.database.model.DescribeTable;

public class DatabaseInfoDTO {
	public long AvaiableTime;
	protected long UserId;
	public byte Permission;
	protected byte LogoutId;
	public short DataLength;
	public long AccessToken;
	public long ReadToken;
	public long WriteToken;
	protected long TimeCreate;
	protected long Version;
	
	protected short SubTable;
	public long TokenLifeTime;
	
	public HashMap<String, Object> AllTime, Yesterday, ThisMonth, LoginRule;
	public ArrayList<HashMap<String, Object>> ListEmail;
	
	public DescribeTable[] DescribeTables;
	
	protected HashMap<String, Object> UseForTest;
	public long DatabaseSize;
	public long TotalUser;
	
	private long readLong(MessageReceiving messageReceiving) {
		long tmp = messageReceiving.readLong();
		DatabaseSize+=tmp;
		return tmp;
	}
	
	public DatabaseInfoDTO(MessageReceiving messageReceiving) {
		AvaiableTime = messageReceiving.readLong();
		UserId = messageReceiving.readLong();
		Permission = messageReceiving.readByte();
		LogoutId = messageReceiving.readByte();
		DataLength = messageReceiving.readShort();
		AccessToken = messageReceiving.readLong();
		ReadToken = messageReceiving.readLong();
		WriteToken = messageReceiving.readLong();
		TimeCreate = messageReceiving.readLong();
		Version = messageReceiving.readLong();
		
		SubTable = messageReceiving.readShort();
		TokenLifeTime = messageReceiving.readLong();
		
		AllTime = new HashMap<>();
		AllTime.put(SystemConstant.Request, messageReceiving.readLong());
		AllTime.put(SystemConstant.DataTransfer, messageReceiving.readLong());
		
		Yesterday = new HashMap<>();
		Yesterday.put(SystemConstant.NumberUser, messageReceiving.readInt());
		Yesterday.put(SystemConstant.Request, messageReceiving.readLong());
		Yesterday.put(SystemConstant.DataTransfer, messageReceiving.readLong());
		
		ThisMonth = new HashMap<>();
		ThisMonth.put(SystemConstant.NumberUser, messageReceiving.readInt());
		ThisMonth.put(SystemConstant.Request, messageReceiving.readLong());
		ThisMonth.put(SystemConstant.DataTransfer, messageReceiving.readLong());
		
		LoginRule = new HashMap<>();
		LoginRule.put(SystemConstant.Device, messageReceiving.readBoolean());
		LoginRule.put(SystemConstant.SystemAccount, messageReceiving.readBoolean());
		LoginRule.put(SystemConstant.Facebook, messageReceiving.readBoolean());
		LoginRule.put(SystemConstant.Google, messageReceiving.readBoolean());
		LoginRule.put(SystemConstant.AdsId, messageReceiving.readBoolean());
		LoginRule.put(SystemConstant.Apple, messageReceiving.readBoolean());
		LoginRule.put(SystemConstant.EmailCode, messageReceiving.readBoolean());

		ListEmail=new ArrayList<>();
		byte numberEmail = messageReceiving.readByte();
		for(byte i=0;i<numberEmail;i++) {
			HashMap<String, Object> mapEmail = new HashMap<>();
			mapEmail.put(SystemConstant.Email, messageReceiving.readString());
			mapEmail.put(SystemConstant.AppPassword, messageReceiving.readString());
			ListEmail.add(mapEmail);
		}
		
		short numberDescribeTables = messageReceiving.readShort();
		DescribeTables=new DescribeTable[numberDescribeTables];
		for(short i=0;i<numberDescribeTables;i++) {
			DescribeTables[i] = new DescribeTable();
			DescribeTables[i].readMessage(messageReceiving);
		}
		//////////////////////////////////////////////////////////////////////////////////////////
		UseForTest = new HashMap<>();
		TotalUser = (readLong(messageReceiving)-8192)/(9+DataLength) - 1;
		UseForTest.put("Userdata", DatabaseSize);
		
		UseForTest.put("Account", readLong(messageReceiving));
		UseForTest.put("Account.index", readLong(messageReceiving));
		UseForTest.put("String", readLong(messageReceiving));
		UseForTest.put("String.index", readLong(messageReceiving));
		
		ArrayList<HashMap<String, Long>> ListSub = new ArrayList<>();
		short numberSub = messageReceiving.readShort();
		for(short i=0;i<numberSub;i++) {
			byte numberFile = messageReceiving.readByte();
			HashMap<String, Long> folder = new HashMap<>();
			for(short k=0;k<numberFile;k++)
				folder.put(messageReceiving.readString(), readLong(messageReceiving));
			ListSub.add(folder);
		}
		UseForTest.put("ListSubTable", ListSub);
	}
	
	
	
	
	public String toPrettyString() {return new GsonBuilder().setPrettyPrinting().create().toJson(this);}
}
