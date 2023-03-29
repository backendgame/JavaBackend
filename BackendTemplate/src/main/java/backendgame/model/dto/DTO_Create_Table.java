package backendgame.model.dto;

import java.util.Random;

import backendgame.config.TIME;

public class DTO_Create_Table {
	public short tableId;
	public long timeAvaiable;
	
	public long accessKey;
	public long readKey;
	public long writeKey;
	public long token_LifeTime;
	
	public DTO_Create_Table(short _tableId,Random random) {
		tableId = _tableId;
		timeAvaiable = TIME.Day_30;
		
		while(accessKey<1)
			accessKey=random.nextLong();
		
		while(readKey<1)
			readKey=random.nextLong();
		
		while(writeKey<1)
			writeKey=random.nextLong();
		
		token_LifeTime = TIME.Hour_8;
	}
	
	public DTO_Create_Table() {}
}
