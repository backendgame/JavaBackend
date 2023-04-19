package database_game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import begame.config.PATH;

public class AdminLoginManager {
	private AdminLoginManager() {}
	
	
	
	public String readAdminFile() {
		try {
			return Files.readString(Paths.get(PATH.DATABASE_FOLDER + "/AdminAccount.json"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void writeAdminFile(Map<?,?> map) {
		try {
			Files.writeString(Paths.get(PATH.DATABASE_FOLDER + "/AdminAccount.json"), new JSONObject(map).toString(), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public String getPasswordAccount(String username) {
		Map<String, Object> mapAccount = loadAdminAccount();
		if(mapAccount.containsKey(username))
			return (String) mapAccount.get(username);
		else
			return null;
	}
	public void deleteAccount(String username) {
		Map<String, Object> mapAccount = loadAdminAccount();
		mapAccount.remove(username);
		writeAdminFile(mapAccount);
	}
	public void setAccount(String username,String password) {
		Map<String, Object> mapAccount = loadAdminAccount();
		mapAccount.put(username, password);
		writeAdminFile(mapAccount);
	}
	
	public Map<String, Object> loadAdminAccount(){
		try {
			return new JSONObject(readAdminFile()).toMap();
		}catch (Exception e) {
			e.printStackTrace();
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("admin", "admin");
		writeAdminFile(map);
		return map;
	}
	
	
	
	private static AdminLoginManager instance=null;
	public static final AdminLoginManager gI() {
		if(instance==null)
			instance=new AdminLoginManager();
		return instance;
	}
}
