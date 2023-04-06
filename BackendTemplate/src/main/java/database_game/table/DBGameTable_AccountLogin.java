package database_game.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import backendgame.com.database.Btree2Primary;
import begame.config.PATH;

public class DBGameTable_AccountLogin extends BasePrimaryDatabaseGame{
	public static final String DATA_EXTENSION = ".P2Data";
	public static final String INDEX_EXTENSION = ".P2Index";

	private Btree2Primary btree;
	public DBGameTable_AccountLogin(short tableId) throws FileNotFoundException {
		path=PATH.DATABASE_FOLDER+"/"+tableId+"/Account";
		rfData = new RandomAccessFile(path+DATA_EXTENSION,"rw");
		rfBTree = new RandomAccessFile(path+INDEX_EXTENSION,"rw");
	}



	private void initProcess() {if(btree==null) {btree=new Btree2Primary(rfData, rfBTree);}}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////
	@Override public void deleteFile(){try {Files.deleteIfExists(FileSystems.getDefault().getPath(path+DATA_EXTENSION));} catch (IOException e) {e.printStackTrace();}try {Files.deleteIfExists(FileSystems.getDefault().getPath(path+INDEX_EXTENSION));} catch (IOException e) {e.printStackTrace();}}
	///////////////////////////////////////////////////////////////////////////////////



	public long insertRow(String credential, byte databaseId, long passwordLocation, DBGameTable_UserData databaseUserData) {

		return 0;
	}



	public long[] getOffset(String credential) throws IOException {
		if(credential.equals(""))
			return null;
		initProcess();
		return btree.querryOffset(credential.getBytes(StandardCharsets.UTF_8));
	}



}
