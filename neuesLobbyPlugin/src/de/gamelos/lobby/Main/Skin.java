package de.gamelos.lobby.Main;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Skin {

	
	
	String uuid;
	String name;
	String value;
	String signatur;
	String id;
	
	Skin(String uuid){
		this.uuid = uuid;
		load();
	}
	
	
	
	private void load(){
		try {
			// Get the name from SwordPVP
			URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
			URLConnection uc = url.openConnection();
			uc.setUseCaches(false);
			uc.setDefaultUseCaches(false);
			uc.addRequestProperty("User-Agent", "Mozilla/5.0");
			uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
			uc.addRequestProperty("Pragma", "no-cache");

			// Parse it
			String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
			String[] b = json.split(",");
			String id = b[0].substring(7, b[0].length()-1);
			this.id = id;
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(json);
			JSONArray properties = (JSONArray) ((JSONObject) obj).get("properties");
			for (int i = 0; i < properties.size(); i++) {
				try {
					JSONObject property = (JSONObject) properties.get(i);
					String name = (String) property.get("name");
					String value = (String) property.get("value");
					String signature = property.containsKey("signature") ? (String) property.get("signature") : null;

					
					this.name = name;
					this.value = value;
					this.signatur = signature;
					
					
				} catch (Exception e) {
					Bukkit.getLogger().log(Level.WARNING, "Failed to apply auth property", e);
				}
			}
		} catch (Exception e) {
			; // Failed to load skin
		}
	}
	
 public String getSkinValue(){
return value;
 }
 
 public String getSkinName(){
	 	return name;
	 }
 
 
 public String getSkinSignatur(){
	 	return signatur;
	 }
	
 public String getid(){
	return id;
 }
 
}