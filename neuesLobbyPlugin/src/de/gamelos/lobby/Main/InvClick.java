package de.gamelos.lobby.Main;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.Plugin;

import com.connorlinfoot.titleapi.TitleAPI;

import net.md_5.bungee.api.ChatColor;

public class InvClick implements Listener {

	@EventHandler
	public void onklick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getTitle().equals(ChatColor.YELLOW+"Teleporter")){
			if(e.getCurrentItem().getType() == Material.MAGMA_CREAM){
				Double x1 = Main.loc.getDouble("spawn.x");
				Double y1 = Main.loc.getDouble("spawn.y");
				Double z1 = Main.loc.getDouble("spawn.z");
				Float yaw1 = (float) Main.loc.getDouble("spawn.yaw");
				Float pitch1 = (float) Main.loc.getDouble("spawn.pitch");
				World w1 = p.getWorld();
				Location lobbyspawn = new Location(w1,x1,y1,z1,yaw1,pitch1);
				p.teleport(lobbyspawn);
			}else if(e.getCurrentItem().getType() == Material.BED){
				Double x1 = Main.loc.getDouble("bedwarsspawn.x");
				Double y1 = Main.loc.getDouble("bedwarsspawn.y");
				Double z1 = Main.loc.getDouble("bedwarsspawn.z");
				Float yaw1 = (float) Main.loc.getDouble("bedwarsspawn.yaw");
				Float pitch1 = (float) Main.loc.getDouble("bedwarsspawn.pitch");
				World w1 = p.getWorld();
				Location lobbyspawn = new Location(w1,x1,y1,z1,yaw1,pitch1);
				p.teleport(lobbyspawn);
			}else if(e.getCurrentItem().getType() == Material.SEA_LANTERN){
				Double x1 = Main.loc.getDouble("communityspawn.x");
				Double y1 = Main.loc.getDouble("communityspawn.y");
				Double z1 = Main.loc.getDouble("communityspawn.z");
				Float yaw1 = (float) Main.loc.getDouble("communityspawn.yaw");
				Float pitch1 = (float) Main.loc.getDouble("communityspawn.pitch");
				World w1 = p.getWorld();
				Location lobbyspawn = new Location(w1,x1,y1,z1,yaw1,pitch1);
				p.teleport(lobbyspawn);
			}else if(e.getCurrentItem().getType() == Material.GRASS){
				Double x1 = Main.loc.getDouble("skywarsspawn.x");
				Double y1 = Main.loc.getDouble("skywarsspawn.y");
				Double z1 = Main.loc.getDouble("skywarsspawn.z");
				Float yaw1 = (float) Main.loc.getDouble("skywarsspawn.yaw");
				Float pitch1 = (float) Main.loc.getDouble("skywarsspawn.pitch");
				World w1 = p.getWorld();
				Location lobbyspawn = new Location(w1,x1,y1,z1,yaw1,pitch1);
				p.teleport(lobbyspawn);
			}else if(e.getCurrentItem().getType() == Material.GOLD_BLOCK){
				Double x1 = Main.loc.getDouble("trumpspawn.x");
				Double y1 = Main.loc.getDouble("trumpspawn.y");
				Double z1 = Main.loc.getDouble("trumpspawn.z");
				Float yaw1 = (float) Main.loc.getDouble("trumpspawn.yaw");
				Float pitch1 = (float) Main.loc.getDouble("trumpspawn.pitch");
				World w1 = p.getWorld();
				Location lobbyspawn = new Location(w1,x1,y1,z1,yaw1,pitch1);
				p.teleport(lobbyspawn);
			}else if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
				Main.connectserver(p, "Community-1");
			}else if(e.getCurrentItem().getType() == Material.DIAMOND_PICKAXE){
			Main.connectserver(p, "CityBuild-1");
			}else if(e.getCurrentItem().getType() == Material.STICK){
				Double x1 = -485.0;
				Double y1 = 8.0;
				Double z1 = 197.0;
				Float yaw1 = (float) -88.0;
				Float pitch1 = (float) 2.9;
				World w1 = p.getWorld();
				Location lobbyspawn = new Location(w1,x1,y1,z1,yaw1,pitch1);
				p.teleport(lobbyspawn);
			}
		}else if(e.getInventory().getTitle().equals(ChatColor.AQUA+"Lobby wechseln")){
			if(e.getCurrentItem().getType() == Material.REDSTONE){
			p.sendMessage(ChatColor.RED+"Diese Lobby ist Offline");
			p.closeInventory();
			}else if(e.getCurrentItem().getType() == Material.SUGAR){
					if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals(Main.servername)){
					p.sendMessage(ChatColor.RED+"Du bist bereits auf dieser Lobby");
					}else{
						connectserver(p, ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
					}
					
					p.closeInventory();
				
				}else if(e.getCurrentItem().getType() == Material.GLOWSTONE_DUST){
					if(p.getWorld().getName().equals("Premium")){
						p.sendMessage(ChatColor.RED+"Du bist bereits auf der Premium Lobby");
						p.closeInventory();
					}else{
						if(p.hasPermission("lobby.premiumlobby")){
							connectserver(p, "PremiumLobby-1");
						}else{
							p.sendMessage(ChatColor.RED+"Du musst Premium für diese Lobby sein!");
							p.closeInventory();
						}
					}
					}
			
		}
	}
	
	public static void connectserver(Player p, String Server){
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		@SuppressWarnings("resource")
		DataOutputStream out = new DataOutputStream(b);
		
		try{
			out.writeUTF("Connect");
			out.writeUTF(Server);
		}catch(IOException ex){
			System.err.println("Es gab einen Fehler:");
			ex.printStackTrace();
		}
		Plugin pl = Bukkit.getPluginManager().getPlugin("Lobby");
		p.sendPluginMessage(pl, "BungeeCord", b.toByteArray());
	}
	
	@EventHandler
	public void onwchange(PlayerChangedWorldEvent e){
		if(e.getPlayer().getWorld().getName().equals("Premium")){
			TitleAPI.sendTabTitle(e.getPlayer(), "§8===================§9 Jaylos.net §7- §ePremium Lobby §8===================", ChatColor.GRAY+"Reporte Spieler mit "+ChatColor.RED+"/report"+ChatColor.GRAY+" oder erstelle Partys mit "+ChatColor.LIGHT_PURPLE+"/party");
		}else{
			TitleAPI.sendTabTitle(e.getPlayer(), "§8===================§9 Jaylos.net §7- §eLobby §8===================", ChatColor.GRAY+"Reporte Spieler mit "+ChatColor.RED+"/report"+ChatColor.GRAY+" oder erstelle Partys mit "+ChatColor.LIGHT_PURPLE+"/party");
		}
		for(Player pp:Bukkit.getOnlinePlayers()){
			pp.showPlayer(pp);
		}
		for(Player pp:Bukkit.getOnlinePlayers()){
			if(!pp.getWorld().getName().equals(e.getPlayer().getWorld().getName())){
				pp.hidePlayer(pp);
			}
		}
	}
	
}
