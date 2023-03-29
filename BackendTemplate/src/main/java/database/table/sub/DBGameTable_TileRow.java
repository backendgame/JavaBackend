package database.table.sub;

import java.io.IOException;

import database.BaseTableData;
import database.DescribeTable;
import database.SubTable;
import database.table.DBString;

public class DBGameTable_TileRow extends BaseTableData {
	public static final int OFFSET_StartTileRow			= 7720;
	
	public static final long OFFSET_Type				=60;//byte
	public static final long OFFSET_DescribeTables	 	= 61;//ID → short → List DescribeTables
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public DBGameTable_TileRow(short _tableId, short _subTableId) throws IOException {
		super(_tableId,SubTable.getSubFile(_tableId, _subTableId));
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void updateDescribeTable(DescribeTable[] list, DBString dbString) throws IOException {updateDescribeTable(OFFSET_DescribeTables, list, OFFSET_StartTileRow, dbString);}
	public DescribeTable[] getDescribeTable(DBString dbString) throws IOException {return getDescribeTables(OFFSET_DescribeTables, OFFSET_StartTileRow, dbString);}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public long countRow() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
}
