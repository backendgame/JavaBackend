package gameonline.rest.controller_user.Table.sub.tile_binary;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;

public class Service_SubTableScreen40_List_Config extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull @Positive public short subTableID;

	@NotNull @Positive public short nodeLimited;

	@Override
	protected MyRespone respone() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public MessageSending onMakeMessage() {
//		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTileBinary_Config);
//		messageSending.writeString(token);
//		messageSending.writeshort(tableId);
//		messageSending.writeshort(subTableID);
//		
//		messageSending.writeshort(nodeLimited);
//		
//		return messageSending;
//	}
}
