package de.gamelos.lobby.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;



public class Clandata {
	
	//TODO Rangnamen:
	//   - Admin
	//   - Mod
	//   - Sup
	//   - Builder
	//   - Youtuber
	//   - Eiszeit
	//   - Premium

public static boolean clanExists(String clanname){
		
		
		try {
			@SuppressWarnings("static-access")
			ResultSet rs = Main.mysql.querry("SELECT * FROM Clan WHERE Clanname = '"+ clanname + "'");
			if(rs.next()){
				return rs.getString("Clanshort") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

public static boolean shortExists(String so){
	
	
	try {
		@SuppressWarnings("static-access")
		ResultSet rs = Main.mysql.querry("SELECT * FROM Clan WHERE Clanshort = '"+ so + "'");
		if(rs.next()){
			return rs.getString("Clanname") != null;
		}
		return false;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return false;
}
	
	public static void createClan(String clanname,String Clanshort, Player p){
		if(!(clanExists(clanname))){
			Main.mysql.update("INSERT INTO Clan(Clanname, Clanshort, Clanleader, Clanmod, Clanmember) VALUES ('" +clanname+ "', '"+Clanshort+"', '"+p.getUniqueId().toString()+"', '', '');");
		}
	}
	
	//get-----------------------------------------------------------------------------------------------------------------------------------
	public static String getShort(String clanname){
		String i = null;
		if(clanExists(clanname)){
			try {
				@SuppressWarnings("static-access")
				ResultSet rs = Main.mysql.querry("SELECT * FROM Clan WHERE Clanname = '"+ clanname + "'");
				
				if((!rs.next()) || (String.valueOf(rs.getString("Clanshort")) == null));
				
				i = rs.getString("Clanshort");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getClanleader(String clanname, Player p){
		List list = new ArrayList<>();
		if(clanExists(clanname)){
				String i = "";
				try {
					@SuppressWarnings("static-access")
					ResultSet rs = Main.mysql.querry("SELECT * FROM Clan WHERE Clanname = '"+ clanname + "'");
					
					if((!rs.next()) || (String.valueOf(rs.getString("Clanleader")) == null));
					
					i = rs.getString("Clanleader");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				if(i.contains(",")){
				String[] ss = i.split(",");
				for(String b : ss){
					if(!b.equals(" ")){
						list.add(b);
					}
					}
				}else{
					if(i.length()>5){
						list.add(i);
					}
				}
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getClanmod(String clanname){
		List list = new ArrayList<>();
		if(clanExists(clanname)){
				String i = "";
				
				try {
					@SuppressWarnings("static-access")
					ResultSet rs = Main.mysql.querry("SELECT * FROM Clan WHERE Clanname = '"+ clanname + "'");
					
					if((!rs.next()) || (String.valueOf(rs.getString("Clanmod")) == null));
					
					i = rs.getString("Clanmod");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				if(i.contains(",")){
				String[] ss = i.split(",");
				for(String b : ss){
					if(!b.equals(" ")){
						list.add(b);
					}
			}
		}else{
			if(i.length()>5){
				list.add(i);
			}
		}
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getClanmember(String clanname){
		List list = new ArrayList<>();
		if(clanExists(clanname)){
				String i = "";
				
				try {
					@SuppressWarnings("static-access")
					ResultSet rs = Main.mysql.querry("SELECT * FROM Clan WHERE Clanname = '"+ clanname + "'");
					
					if((!rs.next()) || (String.valueOf(rs.getString("Clanmember")) == null));
					
					i = rs.getString("Clanmember");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				if(i.contains(",")){
				String[] ss = i.split(",");
				for(String b : ss){
					if(!b.equals(" ")){
						list.add(b);
					}
			}
		}else{
			if(i.length()>5){
				list.add(i);
			}
		}
		}
		return list;
	}
	
	//set-----------------------------------------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public static void addLeader(String clanname, String uuid){
		
		if(clanExists(clanname)){
			List<String> list = getClanleader(clanname,Bukkit.getPlayer(uuid));
			list.add(uuid);
			
			String sss = "";
			for(String l : list){
				sss = sss+l+",";
			}
			Main.mysql.update("UPDATE Clan SET Clanleader= '" + sss+ "' WHERE Clanname= '" + clanname+ "';");	
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void addMod(String clanname, String uuid){
		
		if(clanExists(clanname)){
			List<String> list = getClanmod(clanname);
			list.add(uuid);
			
			String sss = "";
			for(String l : list){
				sss = sss+l+",";
			}
			Main.mysql.update("UPDATE Clan SET Clanmod= '" + sss+ "' WHERE Clanname= '" + clanname+ "';");	
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void addmember(String clanname, String uuid){
		
		if(clanExists(clanname)){
			List<String> list = getClanmember(clanname);
			list.add(uuid);
			
			String sss = "";
			for(String l : list){
				sss = sss+l+",";
			}
			Main.mysql.update("UPDATE Clan SET Clanmember= '" + sss+ "' WHERE Clanname= '" + clanname+ "';");	
		}
		
	}
	
	

@SuppressWarnings("unchecked")
public static void delmember(String clanname, String uuid){
	
	if(clanExists(clanname)){
		List<String> list = getClanmember(clanname);
		if(list.contains(uuid)){
		list.remove(uuid);
		
		String sss = "";
		for(String l : list){
			sss = sss+l+",";
		}
		Main.mysql.update("UPDATE Clan SET Clanmember= '" + sss+ "' WHERE Clanname= '" + clanname+ "';");	
		}
	}
	
}
@SuppressWarnings("unchecked")
public static void delleader(String clanname, String uuid){
	
	if(clanExists(clanname)){
		List<String> list = getClanleader(clanname,Bukkit.getPlayer(uuid));
		if(list.contains(uuid)){
		list.remove(uuid);
		
		String sss = "";
		for(String l : list){
			sss = sss+l+",";
		}
		Main.mysql.update("UPDATE Clan SET Clanleader= '" + sss+ "' WHERE Clanname= '" + clanname+ "';");	
		}
	}
	
}
@SuppressWarnings("unchecked")
public static void delmod(String clanname, String uuid){
	
	if(clanExists(clanname)){
		List<String> list = getClanmod(clanname);
		if(list.contains(uuid)){
		list.remove(uuid);
		
		String sss = "";
		for(String l : list){
			sss = sss+l+",";
		}
		Main.mysql.update("UPDATE Clan SET Clanmod= '" + sss+ "' WHERE Clanname= '" + clanname+ "';");	
		}
	}
	
}

public static void delClan(String clanname){
	
	if(clanExists(clanname)){
		Main.mysql.update("DELETE FROM Clan WHERE Clanname = '"+clanname+"';");
	}
}
	
}