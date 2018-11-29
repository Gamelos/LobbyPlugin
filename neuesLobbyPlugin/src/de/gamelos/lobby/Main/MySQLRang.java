package de.gamelos.lobby.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.entity.Player;


public class MySQLRang {
	
	//TODO Rangnamen:
	//   -Admin
	//   -Mod
	//   -Sup
	//   -Youtuber
	//   -Premium

public static boolean playerExists(String uuid){
		
		
		try {
			@SuppressWarnings("static-access")
			ResultSet rs = Main.mysql.querry("SELECT * FROM Raenge WHERE UUID = '"+ uuid + "'");
			
			if(rs.next()){
				return rs.getString("UUID") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void createPlayer(String uuid){
		if(!(playerExists(uuid))){
			Main.mysql.update("INSERT INTO Raenge(UUID, RANGNAME, PREFIX, TIME) VALUES ('" +uuid+ "', '0', '0', '0');");
		}
	}
	
	//get-----------------------------------------------------------------------------------------------------------------------------------
	public static String getRangname(String uuid){
		String i = null;
		if(playerExists(uuid)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Raenge WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("RANGNAME")) == null));
				
				i = rs.getString("RANGNAME");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
		}
		return i;
	}
	
	
	
	public static String getPrefix(String uuid){
		String i = null;
		if(playerExists(uuid)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Raenge WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("PREFIX")) == null));
				
				i = rs.getString("PREFIX");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	public static String getTime(String uuid){
		String i = null;
		if(playerExists(uuid)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Raenge WHERE UUID = '"+ uuid + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("TIME")) == null));
				
				i = rs.getString("TIME");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	//set-----------------------------------------------------------------------------------------------------------------------------------
	
	public static void setRangname(String uuid, String Rangname){
		
		if(playerExists(uuid)){
			Main.mysql.update("UPDATE Raenge SET RANGNAME= '" + Rangname+ "' WHERE UUID= '" + uuid+ "';");
		}else{
			createPlayer(uuid);
			setRangname(uuid, Rangname);
		}
		
	}
	
	public static void setPrefix(String uuid, String Prefix){
		
		if(playerExists(uuid)){
			Main.mysql.update("UPDATE Raenge SET PREFIX= '" + Prefix+ "' WHERE UUID= '" + uuid+ "';");
		}else{
			createPlayer(uuid);
			setPrefix(uuid, Prefix);
		}
	}
	
	public static void setTime(String uuid, String Time){
		
		if(playerExists(uuid)){
			Main.mysql.update("UPDATE Raenge SET TIME= '" + Time+ "' WHERE UUID= '" + uuid+ "';");
		}else{
			createPlayer(uuid);
			setTime(uuid, Time);
		}
	}
	//hasrangorbetter----------------------------------------------------------------------------------------------------
	public static boolean HasRangOrBetter(Player p, String Rang){
		String rang = getRangname(p.getUniqueId().toString());
		if(rang != null){
		if(Rang == "Premium"){
			if(rang.equals("Premium")||rang.equals("Youtuber")||rang.equals("Sup")||rang.equals("Mod")||rang.equals("Admin")||rang.equals("Builder")){
				return true;
			}
		}else if(Rang == "Youtuber"){
			if(rang.equals("Youtuber")||rang.equals("Sup")||rang.equals("Mod")||rang.equals("Admin")||rang.equals("Builder")){
				return true;
			}
		}else if(Rang == "Builder"){
			if(rang.equals("Sup")||rang.equals("Mod")||rang.equals("Admin")||rang.equals("Builder")){
				return true;
			}
		}else if(Rang == "Sup"){
			if(rang.equals("Sup")||rang.equals("Mod")||rang.equals("Admin")){
				return true;
			}
		}else if(Rang == "Mod"){
			if(rang.equals("Mod")||rang.equals("Admin")){
				return true;
			}
		}else if(Rang == "Admin"){
			if(rang.equals("Admin")){
				return true;
			}
		}
		}
		return false;
	}
	
}