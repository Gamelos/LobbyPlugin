package de.gamelos.lobby.Main;

import java.awt.geom.Area;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Lobby {

public static boolean serverExists(String ip){
		
		
		try {
			@SuppressWarnings("static-access")
			ResultSet rs = Main.mysql.querry("SELECT * FROM Lobbys WHERE IP = '"+ ip + "'");
			
			if(rs.next()){
				return rs.getString("Name") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

public static boolean nameExists(String name){
	
	
	try {
		@SuppressWarnings("static-access")
		ResultSet rs = Main.mysql.querry("SELECT * FROM Lobbys WHERE Name = '"+ name + "'");
		
		if(rs.next()){
			return rs.getString("IP") != null;
		}
		return false;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return false;
}
	
	public static void addServer(String ip, String Servername){
		if(!(serverExists(ip))){
			Main.mysql.update("INSERT INTO Lobbys(IP, Name, Spieler) VALUES ('" +ip+ "', '"+Servername+"', '0');");
		}
	}
	
	public static void removeServer(String Name){
			Main.mysql.update("DELETE FROM Lobbys WHERE Name = '"+Name+"'");
	}
	
	//get-----------------------------------------------------------------------------------------------------------------------------------
	public static String getName(String ip){
		String i = null;
		if(serverExists(ip)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Lobbys WHERE IP = '"+ ip + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("Name")) == null));
				
				i = rs.getString("Name");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
		}
		return i;
	}
	
	
	
	public static Integer getSpieler(String name){
		int i = 0;
		if(nameExists(name)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Lobbys WHERE Name = '"+ name + "'");
				
				if((!rs.next()) || (Integer.valueOf(rs.getInt("Spieler")) == null));
				
				i = rs.getInt("Spieler");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
		}
		return i;
	}
	
	
	//set-----------------------------------------------------------------------------------------------------------------------------------
	
public static void setSpieler(String name, int spieler){
		
		if(nameExists(name)){
			Main.mysql.update("UPDATE Lobbys SET Spieler= '" + spieler+ "' WHERE Name= '" + name+ "';");
		}else{
		}
	}

@SuppressWarnings("static-access")
public static List<String> showlobbys(){
	List<String> list = new ArrayList<>();
	ResultSet rs = Main.mysql.querry("SELECT * FROM Lobbys");
	try {
		while (rs.next()) {
		  String name = rs.getString("Name");
		  list.add(name);
		  //usw.
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return list;
}
	
}