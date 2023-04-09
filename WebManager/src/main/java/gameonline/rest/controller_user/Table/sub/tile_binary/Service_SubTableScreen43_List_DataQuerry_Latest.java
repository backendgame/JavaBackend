package gameonline.rest.controller_user.Table.sub.tile_binary;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import backendgame.com.core.MessageSending;
import gameonline.config.CMD_ONEHIT;
import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;

public class Service_SubTableScreen43_List_DataQuerry_Latest extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull @Positive public short subTableID;
	
	@NotNull @Positive public short numberNode;
	
	@Override public MyRespone respone() {return new BaseClientOnehit_TileCustomQuerry(regionId) {@Override public MessageSending doSendMessage() {
		
		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTileBinary_Querry_Latest);
		messageSending.writeString(token);
		messageSending.writeShort(tableId);
		messageSending.writeShort(subTableID);
		
		messageSending.writeShort(numberNode);
		return messageSending;
	}}.process();}

}
