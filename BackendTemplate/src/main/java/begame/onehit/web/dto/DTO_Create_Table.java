package begame.onehit.web.dto;

import java.util.Random;

import backendgame.com.core.TimeManager;

public class DTO_Create_Table {
	public short tableId;
	public long timeAvaiable;
	
	public long accessKey;
	public long readKey;
	public long writeKey;
	public long token_LifeTime;
	
	public DTO_Create_Table(short _tableId,Random random) {
		tableId = _tableId;
		timeAvaiable = TimeManager.A_MONTH_MILLISECOND;
		
		while(accessKey<1)
			accessKey=random.nextLong();
		
		while(readKey<1)
			readKey=random.nextLong();
		
		while(writeKey<1)
			writeKey=random.nextLong();
		
		token_LifeTime = TimeManager.A_HOUR_MILLISECOND*8;
	}
	
	public DTO_Create_Table() {}
}
