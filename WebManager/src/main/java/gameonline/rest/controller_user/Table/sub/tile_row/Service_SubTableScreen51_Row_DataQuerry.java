package gameonline.rest.controller_user.Table.sub.tile_row;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.controller_user.Table.sub.BaseService_SubTable_Data;
import gameonline.rest.database.model.DataType;
import richard.MessageSending;

public class Service_SubTableScreen51_Row_DataQuerry extends BaseService_SubTable_Data{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull @Positive public short subTableID;
	
	@NotEmpty public String jsonRowStructure;
	@NotNull public DataType[] rowStructure;
	@NotNull @Positive public short rowLimited;
	
	@Override
	public MessageSending onMessageData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
