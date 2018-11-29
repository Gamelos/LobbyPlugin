package de.gamelos.lobby.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.gamelos.PermissionsAPI.PermissionsAPI;
import net.md_5.bungee.api.ChatColor;

public class Tabprefix {

	static Scoreboard board;
	static Objective obj;
	static Team Admin;
	static Team Youtuber;
	static Team Premium;
	static Team Builder;
	static Team Sup;
	static Team Mod;
	static Team Spieler;
	static Team Eiszeit;
	static Team Dev;
	static Team Tester;
	
	public static void crate(){
		
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("asfd", "bbsb");
		obj.setDisplayName(ChatColor.BOLD+"Jaylos.net");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score spieler1 = obj.getScore(ChatColor.GRAY+"Webseite:");
		Score spielerzahl = obj.getScore(ChatColor.DARK_GRAY+"•"+ChatColor.YELLOW+" jaylos.net");
		Score space1 = obj.getScore(ChatColor.BLUE+" ");
		Score map = obj.getScore(ChatColor.GRAY+"Youtube:");
		Score mapname = obj.getScore(ChatColor.DARK_GRAY+"•"+ChatColor.RED+" Jaylos net");
		Score space = obj.getScore(" ");
		Score kills = obj.getScore(ChatColor.GRAY+"Twitter:");
		Score killamount = obj.getScore(ChatColor.DARK_GRAY+"•"+ChatColor.AQUA+" @Jaylos_net");
		
		spieler1.setScore(7);
		spielerzahl.setScore(6);
		space1.setScore(5);
		map.setScore(4);
		mapname.setScore(3);
		space.setScore(2);
		kills.setScore(1);
		killamount.setScore(0);
		
	    Admin = board.registerNewTeam("001Admin");
	    Admin.setPrefix(PermissionsAPI.gettabname("Admin"));
		
		Youtuber = board.registerNewTeam("006Youtuber");
		String mYoutuber = PermissionsAPI.gettabname("Youtuber");
		Youtuber.setPrefix(mYoutuber);
		
		Premium = board.registerNewTeam("008Premium");
		String mPremium= PermissionsAPI.gettabname("Premium");
		Premium.setPrefix(mPremium);
		
		Eiszeit = board.registerNewTeam("007Eiszeit");
		String mEiszeit = PermissionsAPI.gettabname("Eiszeit");
		Eiszeit.setPrefix(mEiszeit);
		
		Dev = board.registerNewTeam("002Dev");
		String mEiszeit1 = PermissionsAPI.gettabname("Dev");
		Dev.setPrefix(mEiszeit1);
		
		Builder = board.registerNewTeam("005Builder");
		String mBuilder= PermissionsAPI.gettabname("Builder");
		Builder.setPrefix(mBuilder);
		
		Sup = board.registerNewTeam("004Sup");
		String mSup = PermissionsAPI.gettabname("Sup");
		Sup.setPrefix(mSup);
		
		Mod = board.registerNewTeam("003Mod");
		String mmod = PermissionsAPI.gettabname("Mod");
		Mod.setPrefix(mmod);
		
		String mtester = PermissionsAPI.gettabname("Tester");
		Tester = board.registerNewTeam("009Tester");
		Tester.setPrefix(mtester);
		
		String mspieler = PermissionsAPI.gettabname("Spieler");
		Spieler = board.registerNewTeam("009Spieler");
		Spieler.setPrefix(mspieler);
	}
	
	
	
	@SuppressWarnings("deprecation")
	public static void setscoreboard(Player p){
		
		Admin.removeEntry(p.getName());
		Youtuber.removeEntry(p.getName());
		Premium.removeEntry(p.getName());
		Builder.removeEntry(p.getName());
		Sup.removeEntry(p.getName());
		Eiszeit.removeEntry(p.getName());
		Mod.removeEntry(p.getName());
		Spieler.removeEntry(p.getName());
		
			String team = PermissionsAPI.getrangname(p);
			if(team == null){
				Spieler.addPlayer(p);
			}else{
				if(team.equals("Admin")){
					Admin.addPlayer(p);
				}else if(team.equals("Youtuber")){
					Youtuber.addPlayer(p);
				}else if(team.equals("Premium")){
					Premium.addPlayer(p);
				}else if(team.equals("Builder")){
					Builder.addPlayer(p);
				}else if(team.equals("Eiszeit")){
					Eiszeit.addPlayer(p);
				}else if(team.equals("Dev")){
					Dev.addPlayer(p);
				}else if(team.equals("Sup")){
					Sup.addPlayer(p);
				}else if(team.equals("Mod")){
					Mod.addPlayer(p);
				}else{
					Spieler.addPlayer(p);
				}
			}
			
			
			p.setScoreboard(board);
	}
	
//	======================================================================================
	@SuppressWarnings({ "deprecation" })
	public static void set(){
		
		String color = "";
	for(Player p: Bukkit.getOnlinePlayers()){

//		====================================================================
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("asfd", "bbsb");
		obj.setDisplayName(ChatColor.BOLD+"Jaylos.net");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score spieler1 = obj.getScore(ChatColor.GRAY+"Rang:");
		String team11 = "";
		String tt = PermissionsAPI.getrangname(p);
		if(tt==null){
			team11 = "Spieler";
		}else{
			if(tt.equals("Contant")) {
				tt = "Content";
			}
			team11 = tt;
		}
		Score spielerzahl = obj.getScore(PermissionsAPI.getchatprefix(p.getUniqueId().toString())+team11);
		Score space1 = obj.getScore(ChatColor.BLUE+" ");
		Score map = obj.getScore(ChatColor.GRAY+"Clan:");
		String clan = Claninfo.getshort(p.getUniqueId().toString());
		if(clan ==null){
			clan = "Kein Clan";
		}
		Score mapname = obj.getScore(ChatColor.AQUA+clan);
		Score space = obj.getScore(" ");
		Score kills = obj.getScore(ChatColor.GRAY+"Webseite:");
		Score killamount = obj.getScore(ChatColor.YELLOW+" jaylos.net");
		
		spieler1.setScore(7);
		spielerzahl.setScore(6);
		space1.setScore(5);
		map.setScore(4);
		mapname.setScore(3);
		space.setScore(2);
		kills.setScore(1);
		killamount.setScore(0);
//		====================================================================
		
		for(Player pp:Bukkit.getOnlinePlayers()){
	String team = PermissionsAPI.getrangname(pp);
	String ordnung = "";
	if(team == null){
		color = PermissionsAPI.gettabname("Spieler");
		ordnung = "011";
	}else{
		if(team.equals("Admin")){
			color = PermissionsAPI.gettabname("Admin");
			ordnung = "001";
		}else if(team.equals("Youtuber")){
			color =  PermissionsAPI.gettabname("Youtuber");
			ordnung = "007";
		}else if(team.equals("Premium")){
			color = PermissionsAPI.gettabname("Premium");
			ordnung = "009";
		}else if(team.equals("Builder")){
			ordnung = "006";
			color = PermissionsAPI.gettabname("Builder");
		}else if(team.equals("Eiszeit")){
			color = PermissionsAPI.gettabname("Eiszeit");
			ordnung = "007";
		}else if(team.equals("Dev")){
			color = PermissionsAPI.gettabname("Dev");
			ordnung = "004";
		}else if(team.equals("Sup")){
			color = PermissionsAPI.gettabname("Sup");
			ordnung = "005";
		}else if(team.equals("Mod")){
			color = PermissionsAPI.gettabname("Mod");
			ordnung = "002";
		}else if(team.equals("Tester")){
			color = ChatColor.GREEN+"Tester "+ChatColor.GRAY+"| "+ChatColor.GREEN;
			ordnung = "010";
		}else if(team.equals("Contant")){
			color = PermissionsAPI.gettabname("Contant");
			ordnung = "003";
		}else if(team.equals("Prem+")){
			color = PermissionsAPI.gettabname("Prem+");
			ordnung = "008";
		}else{
			ordnung = "011";
			color = PermissionsAPI.gettabname("Spieler");
		}
	}
	
	Team t = board.registerNewTeam(ordnung+pp.getName().substring(0, pp.getName().length()-3));	
	t.setPrefix(color);
	String klan = Claninfo.getshort(pp.getUniqueId().toString());
	if((klan!=null)&&(!klan.equals("null"))){
	t.setSuffix(ChatColor.DARK_GRAY+" ["+ChatColor.YELLOW+klan+ChatColor.DARK_GRAY+"]");
	}
	t.addPlayer(pp);
	}
		p.setScoreboard(board);
	}
	}
	
	@SuppressWarnings("deprecation")
	public static void update(Player p){
		for(Player pp:Bukkit.getOnlinePlayers()){
			if(pp!=p){
				Scoreboard board = pp.getScoreboard();
				String color = "";
				String team = PermissionsAPI.getrangname(p);
				String ordnung = "";
				if(team == null){
					color = PermissionsAPI.gettabname("Spieler");
					ordnung = "011";
				}else{
					if(team.equals("Admin")){
						color = PermissionsAPI.gettabname("Admin");
						ordnung = "001";
					}else if(team.equals("Youtuber")){
						color =  PermissionsAPI.gettabname("Youtuber");
						ordnung = "007";
					}else if(team.equals("Premium")){
						color = PermissionsAPI.gettabname("Premium");
						ordnung = "009";
					}else if(team.equals("Builder")){
						ordnung = "006";
						color = PermissionsAPI.gettabname("Builder");
					}else if(team.equals("Eiszeit")){
						color = PermissionsAPI.gettabname("Eiszeit");
						ordnung = "007";
					}else if(team.equals("Dev")){
						color = PermissionsAPI.gettabname("Dev");
						ordnung = "004";
					}else if(team.equals("Sup")){
						color = PermissionsAPI.gettabname("Sup");
						ordnung = "005";
					}else if(team.equals("Mod")){
						color = PermissionsAPI.gettabname("Mod");
						ordnung = "002";
					}else if(team.equals("Tester")){
						color = ChatColor.GREEN+"Tester "+ChatColor.GRAY+"| "+ChatColor.GREEN;
						ordnung = "010";
					}else if(team.equals("Contant")){
						color = PermissionsAPI.gettabname("Contant");
						ordnung = "003";
					}else if(team.equals("Prem+")){
						color = PermissionsAPI.gettabname("Prem+");
						ordnung = "008";
					}else{
						ordnung = "011";
						color = PermissionsAPI.gettabname("Spieler");
					}
				}
				if(board.getTeams().contains(ordnung+p.getName().substring(0, p.getName().length()-3))){
				board.getTeam(ordnung+p.getName().substring(0, p.getName().length()-3)).addPlayer(p);
				}else{
				Team t = board.registerNewTeam(ordnung+p.getName().substring(0, p.getName().length()-3));	
				
				t.setPrefix(color);
				if((Claninfo.getshort(p.getUniqueId().toString())!=null)&&(!Claninfo.getshort(p.getUniqueId().toString()).equals("null"))){
				t.setSuffix(ChatColor.DARK_GRAY+" ["+ChatColor.YELLOW+Claninfo.getshort(p.getUniqueId().toString())+ChatColor.DARK_GRAY+"]");
				}
				t.addPlayer(p);
				}
				
			}
		}
	}
	
	@EventHandler
	public void onqu(PlayerQuitEvent e){
		Player p = e.getPlayer();
//		
		String color = "";
		String team = PermissionsAPI.getrangname(p);
		String ordnung = "";
		if(team == null){
			color = PermissionsAPI.gettabname("Spieler");
			ordnung = "010";
		}else{
			if(team.equals("Admin")){
				color = PermissionsAPI.gettabname("Admin");
				ordnung = "001";
			}else if(team.equals("Youtuber")){
				color =  PermissionsAPI.gettabname("Youtuber");
				ordnung = "006";
			}else if(team.equals("Premium")){
				color = PermissionsAPI.gettabname("Premium");
				ordnung = "008";
			}else if(team.equals("Builder")){
				ordnung = "005";
				color = PermissionsAPI.gettabname("Builder");
			}else if(team.equals("Eiszeit")){
				color = PermissionsAPI.gettabname("Eiszeit");
				ordnung = "007";
			}else if(team.equals("Dev")){
				color = PermissionsAPI.gettabname("Dev");
				ordnung = "004";
			}else if(team.equals("Sup")){
				color = PermissionsAPI.gettabname("Sup");
				ordnung = "003";
			}else if(team.equals("Mod")){
				color = PermissionsAPI.gettabname("Mod");
				ordnung = "002";
			}else if(team.equals("Tester")){
				color = ChatColor.GREEN+"Tester "+ChatColor.GRAY+"| "+ChatColor.GREEN;
				ordnung = "009";
			}else{
				ordnung = "010";
				color = PermissionsAPI.gettabname("Spieler");
			}
		}
//		
		if(p.getScoreboard().getTeam(ordnung+p.getName().substring(0, p.getName().length()-3)) != null){
			Team t = p.getScoreboard().getTeam(ordnung+p.getName().substring(0, p.getName().length()-3));
			t.unregister();
		}
	}
}
