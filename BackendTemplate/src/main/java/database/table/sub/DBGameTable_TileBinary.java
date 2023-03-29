package database.table.sub;

import java.io.IOException;
import java.io.RandomAccessFile;

import database.BaseTableData;
import database.SubTable;

public class DBGameTable_TileBinary extends BaseTableData{
	public static final long OFFSET_Type					=60;//byte
	public static final long OFFSET_StartDaata_Leaderboard	=61;//short
	///////////////////////////////////////////////////////////////////////////////////////
	public RandomAccessFile rfDes;
	public DBGameTable_TileBinary(short _tableId, short _subTableId) throws IOException {
		super(_tableId,SubTable.getSubFile(_tableId, _subTableId));
		try {
			rfDes = new RandomAccessFile(SubTable.getDesFile(_tableId, _subTableId), "rw");
		}catch (Exception e) {
			e.printStackTrace();
			close();
			throw e;
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////
	@Override public void close() {if (rf != null)try {rf.close();rf = null;} catch (IOException e) {e.printStackTrace();}if (rfDes != null)try {rfDes.close();rfDes = null;} catch (IOException e) {e.printStackTrace();}}

	@Override public long countRow() throws IOException {return rfDes.length()/21;}
}
