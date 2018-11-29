package de.gamelos.lobby.Main;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("lb")){
		}else if(cmd.getName().equalsIgnoreCase("test")){
			if(sender.getName().equals("Gamelos")){
				if(args[0].equalsIgnoreCase("setspawn")){
					//spawnset
					Location loc = p.getLocation();
					Main.loc.set("spawn.x", loc.getX());
					Main.loc.set("spawn.y", loc.getY());
					Main.loc.set("spawn.z", loc.getZ());
					Main.loc.set("spawn.yaw", loc.getYaw());
					Main.loc.set("spawn.pitch", loc.getPitch());
					Main.loc.set("spawn.world", loc.getWorld().getName());
					try {
						Main.loc.save(Main.locations);
						p.sendMessage(ChatColor.GREEN+"Der Lobbyspawn wurde erfolgreich gesetzt");	
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
				}else if(args[0].equalsIgnoreCase("setbedwarsspawn")){
					//spawnset
					Location loc = p.getLocation();
					Main.loc.set("bedwarsspawn.x", loc.getX());
					Main.loc.set("bedwarsspawn.y", loc.getY());
					Main.loc.set("bedwarsspawn.z", loc.getZ());
					Main.loc.set("bedwarsspawn.yaw", loc.getYaw());
					Main.loc.set("bedwarsspawn.pitch", loc.getPitch());
					Main.loc.set("bedwarsspawn.world", loc.getWorld().getName());
					try {
						Main.loc.save(Main.locations);
						p.sendMessage(ChatColor.GREEN+"Der BedWars Spawn wurde erfolgreich gesetzt");	
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
				}else if(args[0].equalsIgnoreCase("setskywarsspawn")){
					//spawnset
					Location loc = p.getLocation();
					Main.loc.set("skywarsspawn.x", loc.getX());
					Main.loc.set("skywarsspawn.y", loc.getY());
					Main.loc.set("skywarsspawn.z", loc.getZ());
					Main.loc.set("skywarsspawn.yaw", loc.getYaw());
					Main.loc.set("skywarsspawn.pitch", loc.getPitch());
					Main.loc.set("skywarsspawn.world", loc.getWorld().getName());
					try {
						Main.loc.save(Main.locations);
						p.sendMessage(ChatColor.GREEN+"Der SkyWars Spawn wurde erfolgreich gesetzt");	
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
				}else if(args[0].equalsIgnoreCase("settrumpspawn")){
					//spawnset
					Location loc = p.getLocation();
					Main.loc.set("trumpspawn.x", loc.getX());
					Main.loc.set("trumpspawn.y", loc.getY());
					Main.loc.set("trumpspawn.z", loc.getZ());
					Main.loc.set("trumpspawn.yaw", loc.getYaw());
					Main.loc.set("trumpspawn.pitch", loc.getPitch());
					Main.loc.set("trumpspawn.world", loc.getWorld().getName());
					try {
						Main.loc.save(Main.locations);
						p.sendMessage(ChatColor.GREEN+"Der Trump Spawn wurde erfolgreich gesetzt");	
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
				}else if(args[0].equalsIgnoreCase("setcommunityspawn")){
					//spawnset
					Location loc = p.getLocation();
					Main.loc.set("communityspawn.x", loc.getX());
					Main.loc.set("communityspawn.y", loc.getY());
					Main.loc.set("communityspawn.z", loc.getZ());
					Main.loc.set("communityspawn.yaw", loc.getYaw());
					Main.loc.set("communityspawn.pitch", loc.getPitch());
					Main.loc.set("communityspawn.world", loc.getWorld().getName());
					try {
						Main.loc.save(Main.locations);
						p.sendMessage(ChatColor.GREEN+"Der Community Spawn wurde erfolgreich gesetzt");	
					} catch (IOException e) {
						e.printStackTrace();
					}
					return true;
			}else if(args[0].equalsIgnoreCase("setservername")){
				//spawnset
				Main.loc.set("servername", args[1]);
				try {
					Main.loc.save(Main.locations);
					p.sendMessage(ChatColor.GREEN+"Der Servername wurde erfolgreich gesetzt");	
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}else if(args[0].equalsIgnoreCase("setrang")){
				//spawnset
				Player pp = Bukkit.getPlayer(args[1]);
				MySQLRang.setRangname(pp.getUniqueId().toString(), args[2]);
				MySQLRang.setPrefix(pp.getUniqueId().toString(), args[3]);
				return true;
			}else if(args[0].equalsIgnoreCase("create")){
				p.setGameMode(GameMode.CREATIVE);
				p.sendMessage(ChatColor.GREEN+"Du wurdest in den Create Modus gesetzt");
				p.getInventory().setItem(2, new ItemStack(Material.SIGN));
			}else {
				p.sendMessage(ChatColor.RED+"Diesen Command gibt es nicht");
			}
		}else{
				p.sendMessage(ChatColor.RED+"Du hast keine Rechte dazu");	
			}
		}else if(cmd.getName().equalsIgnoreCase("fastjump")){
			String server = args[0];
			Main.connectserver(p, server);
		}
		return false;
	}

}
