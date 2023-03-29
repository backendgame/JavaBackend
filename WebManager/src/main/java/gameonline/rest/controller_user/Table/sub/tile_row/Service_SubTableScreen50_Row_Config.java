package gameonline.rest.controller_user.Table.sub.tile_row;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import gameonline.rest.BaseAuthorization;
import gameonline.rest.MyRespone;
import gameonline.rest.database.model.DataType;

public class Service_SubTableScreen50_Row_Config extends BaseAuthorization{
	@NotNull @Positive public short regionId;
	@NotNull @Positive public short tableId;
	
	@NotNull @Positive public short subTableID;
	
	@NotEmpty public String jsonRowStructure;
	@NotNull public DataType[] rowStructure;
	@NotNull @Positive public short rowLimited;
	
	@Override
	protected MyRespone respone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
//	@Override
//	public MessageSending onMakeMessage() {
//		DataType dataType;
//		short numberColumn = (short) rowStructure.length;
//		
//		MessageSending messageSending = new MessageSending(CMD_ONEHIT.BBWeb_SubTileRow_Config);
//		messageSending.writeString(token);
//		messageSending.writeshort(tableId);
//		messageSending.writeshort(subTableID);
//		
//		messageSending.writeshort(rowLimited);
//		messageSending.writeshort(numberColumn);
//		for(short i=0;i<numberColumn;i++) {
//			dataType=rowStructure[i];
//			messageSending.writeByte(dataType.type);
//			if(dataType.type>99)
//				messageSending.writeshort(dataType.size);
//		}
//		
//		return messageSending;
//	}
}
