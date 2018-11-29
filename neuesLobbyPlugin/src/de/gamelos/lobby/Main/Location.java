package de.gamelos.lobby.Main;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Location {
	
	//TODO Rangnamen:
	//   -Admin
	//   -Mod
	//   -Sup
	//   -Youtuber
	//   -Premium

public static boolean playerExists(String spielername){
		
		
		try {
			@SuppressWarnings("static-access")
			ResultSet rs = Main.mysql.querry("SELECT * FROM Location WHERE UUID = '"+ spielername + "'");
			
			if(rs.next()){
				return rs.getString("X") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void createPlayer(String spielername){
		if(!(playerExists(spielername))){
			Main.mysql.update("INSERT INTO Location(UUID, X, Y, Z, Yaw, Pich) VALUES ('" +spielername+ "', '0', '0', '0', '0', '0');");
		}
	}
	
	//get-----------------------------------------------------------------------------------------------------------------------------------
	public static Integer getX(String spielername){
		int i = 0;
		if(playerExists(spielername)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Location WHERE UUID = '"+ spielername + "'");
				
				if((!rs.next()) || (Integer.valueOf(rs.getInt("X")) == null));
				
				i = rs.getInt("X");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			createPlayer(spielername);
			getX(spielername);
		}
		return i;
	}
	
	
	
	public static Integer getY(String spielername){
		int i = 0;
		if(playerExists(spielername)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Location WHERE UUID = '"+ spielername + "'");
				
				if((!rs.next()) || (Integer.valueOf(rs.getInt("Y")) == null));
				
				i = rs.getInt("Y");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			createPlayer(spielername);
			getY(spielername);
		}
		return i;
	}
	
	
	public static Integer getZ(String spielername){
		int i = 0;
		if(playerExists(spielername)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Location WHERE UUID = '"+ spielername + "'");
				
				if((!rs.next()) || (Integer.valueOf(rs.getInt("Z")) == null));
				
				i = rs.getInt("Z");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			createPlayer(spielername);
			getZ(spielername);
		}
		return i;
	}
	
	public static Float getYaw(String spielername){
		Float i = 0F;
		if(playerExists(spielername)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Location WHERE UUID = '"+ spielername + "'");
				
				if((!rs.next()) || (Float.valueOf(rs.getFloat("Yaw")) == null));
				
				i = rs.getFloat("Yaw");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			createPlayer(spielername);
			getYaw(spielername);
		}
		return i;
	}
	
	public static Float getPich(String spielername){
		Float i = 0F;
		if(playerExists(spielername)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Location WHERE UUID = '"+ spielername + "'");
				
				if((!rs.next()) || (Float.valueOf(rs.getFloat("Pich")) == null));
				
				i = rs.getFloat("Pich");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			createPlayer(spielername);
			getPich(spielername);
		}
		return i;
	}
	
	
	//set-----------------------------------------------------------------------------------------------------------------------------------
	
	public static void setX(String spielername, String Nick){
		
		if(playerExists(spielername)){
			Main.mysql.update("UPDATE Location SET X= '" + Nick+ "' WHERE UUID= '" + spielername+ "';");
		}else{
			createPlayer(spielername);
			setX(spielername, Nick);
		}
		
	}
	
public static void setY(String spielername, String Nick){
		
		if(playerExists(spielername)){
			Main.mysql.update("UPDATE Location SET Y= '" + Nick+ "' WHERE UUID= '" + spielername+ "';");
		}else{
			createPlayer(spielername);
			setY(spielername, Nick);
		}
		
	}

public static void setZ(String spielername, String Nick){
	
	if(playerExists(spielername)){
		Main.mysql.update("UPDATE Location SET Z= '" + Nick+ "' WHERE UUID= '" + spielername+ "';");
	}else{
		createPlayer(spielername);
		setX(spielername, Nick);
	}
	
}

public static void setYaw(String spielername, String Nick){
	
	if(playerExists(spielername)){
		Main.mysql.update("UPDATE Location SET Yaw= '" + Nick+ "' WHERE UUID= '" + spielername+ "';");
	}else{
		createPlayer(spielername);
		setX(spielername, Nick);
	}
	
}

public static void setPich(String spielername, String Nick){
	
	if(playerExists(spielername)){
		Main.mysql.update("UPDATE Location SET Pich= '" + Nick+ "' WHERE UUID= '" + spielername+ "';");
	}else{
		createPlayer(spielername);
		setPich(spielername, Nick);
	}
	
}
	
}