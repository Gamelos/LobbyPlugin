package de.gamelos.lobby.Main;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import com.connorlinfoot.titleapi.TitleAPI;
import com.google.gson.JsonObject;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudServer;
import de.gamelos.PermissionsAPI.PermissionsAPI;
import de.gamelos.jaylosapi.JaylosAPI;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener{

	public static File locations;
	public static FileConfiguration loc;
	public static File actionbar;
	public static FileConfiguration act;
	public static MySQL mysql;
	public static MySQL nick;
	public static String servername = null;
	private ChannelListener channelListener;
	
	@Override
	public void onEnable() {
		this.channelListener = new ChannelListener(this);
		Bukkit.getMessenger().registerIncomingPluginChannel(this, "info", channelListener);
		npc = new NPC(ChatColor.YELLOW+"Community", new Location(Bukkit.getWorld("spawn"), -574.7, 13, 200, -530F, 0F));
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		System.out.println("[Lobby] Das Plugin wurde geladen!");
		Main.locations = new File("plugins/Lobby", "locations.yml");
		Main.loc = YamlConfiguration.loadConfiguration(Main.locations);
		Main.actionbar = new File("plugins/Lobby", "actionbar.yml");
		Main.act = YamlConfiguration.loadConfiguration(Main.actionbar);
		loadConfig();
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new InvClick(), this);
//		Bukkit.getPluginManager().registerEvents(new Cosmatics(), this);
		super.onEnable();
		ConnectMySQL();
		getCommand("lb").setExecutor(new Commands());
		getCommand("fastjump").setExecutor(new Commands());
		getCommand("test").setExecutor(new Commands());
		Tabprefix.crate();
//		
		String group = CloudAPI.getInstance().getGroup();
		int id = CloudAPI.getInstance().getGroupInitId();
		servername = group+"-"+id;
		if(servername != null){
			Lobby.addServer(Bukkit.getServer().getIp()+":"+Bukkit.getServer().getPort(), servername);
		}
//		
		if(Main.loc != null){
		if(Main.act.getStringList("Actionbar")==null){
		ArrayList<String> list = new ArrayList<>();
		list.add("'test'");
		act.set("Actionbar", list);
		try {
			Main.act.save(Main.actionbar);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		if(Main.loc.getString("spawn.world")!=null){
			for(Entity ee:Bukkit.getWorld(Main.loc.getString("spawn.world")).getEntities()){
				if(!(ee instanceof Player)){
					if(!(ee instanceof ArmorStand)){
					ee.remove();
					}
				}
			}
		}
		}
		Bukkit.createWorld(new WorldCreator("Premium"));
		startactionbar();
	}

	
	private void ConnectMySQL(){
		mysql = new MySQL(JaylosAPI.gethost(), JaylosAPI.getuser(), JaylosAPI.getdatabase(), JaylosAPI.getpassword());
//		mysql = new MySQL("185.230.161.79", "hallotest", "hallotest", "Ichmagjaylos1");
		mysql.update("CREATE TABLE IF NOT EXISTS Raenge(UUID varchar(64), RANGNAME varchar(1000), PREFIX varchar(1000), TIME varchar(1000));");
		mysql.update("CREATE TABLE IF NOT EXISTS Nick(Name varchar(1000), Nick varchar(1000));");
		mysql.update("CREATE TABLE IF NOT EXISTS Location(UUID varchar(1000), X int, Y int, Z int, Yaw int, Pich int);");
		mysql.update("CREATE TABLE IF NOT EXISTS Lobbys(IP varchar(1000), Name varchar(1000), Spieler int);");
		mysql.update("CREATE TABLE IF NOT EXISTS Clan(Clanname varchar(64), Clanshort varchar(1000), Clanleader varchar(1000), Clanmod varchar(1000), Clanmember varchar(1000));");
		mysql.update("CREATE TABLE IF NOT EXISTS Claninfo(UUID varchar(64),Clanname varchar(64), Clanshort varchar(1000));");
		mysql.update("CREATE TABLE IF NOT EXISTS Skins(UUID varchar(64),name varchar(64), value varchar(2000), signature varchar(2000), id varchar(2000));");
	}
	
	public void loadConfig(){
		getConfig().options().copyDefaults(true);
		saveConfig();
		}
	
	@Override
	public void onDisable() {
		if(servername != null){
		Lobby.removeServer(servername);
		}
		System.out.println("[Lobby] Das Plugin wurde deaktiviert!");
		super.onDisable();
	}	
	
	int aktuellespielerzahl;
	
	@EventHandler
	public void onlogin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		if(CloudServer.getInstance().getGroupData().getName().equals("PremiumLobby")){
		if(!MySQLRang.playerExists(p.getUniqueId().toString())||MySQLRang.getRangname(p.getUniqueId().toString())==null){
			e.disallow(Result.KICK_FULL, ChatColor.RED+"Du brauchst Premium um diese Lobby zu betreten!");
		}else{
		if(MySQLRang.getRangname(p.getUniqueId().toString()).equals("Admin")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Sup")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Mod")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Youtuber")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Premium")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Builder")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Contant")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Prem+")||MySQLRang.getRangname(p.getUniqueId().toString()).equals("Dev")){
		}else{
			e.disallow(Result.KICK_FULL, ChatColor.RED+"Du brauchst Premium um diese Lobby zu betreten!");
		}
		}
		}
	}
	
	
	public static ArrayList<Player>totjoin = new ArrayList<>();
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		 if(!totjoin.contains(p)){
				totjoin.add(p);
			    Double x1 = Main.loc.getDouble("spawn.x");
				Double y1 = Main.loc.getDouble("spawn.y");
				Double z1 = Main.loc.getDouble("spawn.z");
				Float yaw1 = (float) Main.loc.getDouble("spawn.yaw");
				Float pitch1 = (float) Main.loc.getDouble("spawn.pitch");
				World w1 = Bukkit.getWorld(Main.loc.getString("spawn.world"));
				Location lobbyspawn = new Location(w1,x1,y1,z1,yaw1,pitch1);
				p.teleport(lobbyspawn);
			}
	
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			@Override
			public void run() {
				if(de.gamelos.lobby.Main.Location.playerExists(e.getPlayer().getUniqueId().toString())){
				double x = de.gamelos.lobby.Main.Location.getX(e.getPlayer().getUniqueId().toString());
				double y = de.gamelos.lobby.Main.Location.getY(e.getPlayer().getUniqueId().toString())+1.5;
				double z = de.gamelos.lobby.Main.Location.getZ(e.getPlayer().getUniqueId().toString());
				float yaw = de.gamelos.lobby.Main.Location.getYaw(e.getPlayer().getUniqueId().toString());
				float pitch = de.gamelos.lobby.Main.Location.getPich(e.getPlayer().getUniqueId().toString());
				Location loc = new Location(Bukkit.getWorld(Main.loc.getString("spawn.world")), x, y, z, yaw, pitch);
				p.teleport(loc);
				 if(servername != null){
					 Lobby.setSpieler(servername, Bukkit.getOnlinePlayers().size());	
					 }
				}
			}
		}, 5);
		 
		for(Player pp : Bukkit.getOnlinePlayers()){
			if(pp.getWorld().getName().equals("Premium")){
				pp.hidePlayer(e.getPlayer());
				e.getPlayer().hidePlayer(pp);
			}
		}
		if(p.hasPermission("lobby.fly")){
			p.setAllowFlight(true);
		}
		e.setJoinMessage(null);
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
		p.getInventory().setBoots(new ItemStack(Material.AIR));
		p.getInventory().setChestplate(new ItemStack(Material.AIR));
		p.getInventory().setLeggings(new ItemStack(Material.AIR));
		p.setExp(0);
		p.setLevel(0);
		p.setGameMode(GameMode.SURVIVAL);
		String s = CloudServer.getInstance().getGroupData().getName();
		TitleAPI.sendTabTitle(p, "§8===================§9 Jaylos.net §7- §e"+s+" §8===================", ChatColor.GRAY+"Reporte Spieler mit "+ChatColor.RED+"/report"+ChatColor.GRAY+" oder erstelle Partys mit "+ChatColor.LIGHT_PURPLE+"/party");
		p.setLevel(0);
		p.setExp(0);
		p.setHealth(20);
		p.setCanPickupItems(false);
		p.setFoodLevel(20);
		for(Player pp:Bukkit.getOnlinePlayers()){
			if(!pp.getWorld().getName().equals(e.getPlayer().getWorld().getName())){
				pp.hidePlayer(pp);
			}
		}
		 for (PotionEffect effect : p.getActivePotionEffects()){
		        p.removePotionEffect(effect.getType());
		 }
		 aktuellespielerzahl = Bukkit.getOnlinePlayers().size();
		 p.setAllowFlight(true);
		 setlobbyitems(p);
		 Tabprefix.set();
		 Tabprefix.update(p);
//		 
			
//		 
	}
	
	
	@EventHandler
	public void onquit(PlayerQuitEvent e){
		aktuellespielerzahl --;
		e.setQuitMessage(null);
		de.gamelos.lobby.Main.Location.setX(e.getPlayer().getUniqueId().toString(), ""+e.getPlayer().getLocation().getBlock().getX());
		de.gamelos.lobby.Main.Location.setY(e.getPlayer().getUniqueId().toString(), ""+e.getPlayer().getLocation().getBlock().getY());
		de.gamelos.lobby.Main.Location.setZ(e.getPlayer().getUniqueId().toString(), ""+e.getPlayer().getLocation().getBlock().getZ());
		de.gamelos.lobby.Main.Location.setYaw(e.getPlayer().getUniqueId().toString(), ""+e.getPlayer().getLocation().getYaw());
		de.gamelos.lobby.Main.Location.setPich(e.getPlayer().getUniqueId().toString(), ""+e.getPlayer().getLocation().getPitch());
		
		 if(servername != null){
			 Lobby.setSpieler(servername, aktuellespielerzahl);	
			 }
	}
	
	
	@EventHandler
	public void onbrak(BlockBreakEvent e){
		if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
			e.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void onclick(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK||e.getAction() == Action.LEFT_CLICK_BLOCK){
		if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
			if(e.getClickedBlock().getType() != Material.STONE_BUTTON){
			e.setCancelled(true);
			}
		}
	}
	}

	@EventHandler
	public void onplace(BlockPlaceEvent e){
		if(e.getPlayer().getGameMode() != GameMode.CREATIVE){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onfood(FoodLevelChangeEvent e){
			e.setCancelled(true);
	}
	
	@EventHandler
	public void ondamage(EntityDamageEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
			e.setCancelled(true);
	}
	 //ITEMMOVE-----------------------------------------------------------------------------------------------------------------------------------
	@EventHandler
	public void onIMove(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(p.getGameMode() != GameMode.CREATIVE){
		e.setCancelled(true);
	}
	}
	
	@EventHandler
	public void onwhetherchange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	
//	ASDFDFS
//	@EventHandler
//	public void onwhetherchange(EntitySpawnEvent e) {
//		if(e.getEntity().getType() != EntityType.PLAYER &&e.getEntity().getType() != EntityType.VILLAGER){
//			e.setCancelled(true);
//		}
//	}
//	ASDFSF
	
	@EventHandler
	   public void onPlayerMoveEvent (PlayerMoveEvent event) {
	   Player p = event.getPlayer();
	      
	   if(p.getGameMode() != GameMode.CREATIVE && (!p.hasPermission("lobby.fly"))){
	      if(p.isFlying()){
	    	  Vector v = p.getLocation().getDirection().multiply(3D).setY(1D);
	    	  p.setAllowFlight(false);
	    	  p.setVelocity(v);
	    	  p.playSound(p.getLocation(), Sound.CAT_HISS, 1F, 1F);
	    	  Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){

				@Override
				public void run() {
					p.setAllowFlight(true);
					
				}
	    		  
	    	  }, 40);
	      }
	   }else{
		   if(p.isFlying()){
			   for(Player pp: Bukkit.getOnlinePlayers()){
				   pp.playEffect(p.getLocation(), Effect.SMOKE, 1);
			   }
		   }
	   }
	   }
	
	@EventHandler
	public static void onclick1(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(p.getItemInHand() != null){
			if(e.getMaterial().equals(Material.INK_SACK)){
			if(p.getItemInHand().getItemMeta().getDisplayName().equals("§aAlle Spieler")){
				
				ItemStack VIP1 = new ItemStack(Material.INK_SACK, 1, (short)5);
				ItemMeta meta1 = VIP1.getItemMeta();
				meta1.setDisplayName(ChatColor.DARK_PURPLE+"Nur VIP´s");
				VIP1.setItemMeta(meta1);
				p.getInventory().setItem(1, VIP1);
				
				p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1F, 1F);
				
				for(Player current : Bukkit.getOnlinePlayers()){
					if(MySQLRang.getRangname(current.getUniqueId().toString()) != null){
					if(MySQLRang.getRangname(current.getUniqueId().toString()).equals("Premium")){
					p.hidePlayer(current);
					}else if(MySQLRang.getRangname(current.getUniqueId().toString()).equals("0")){
						p.hidePlayer(current);
						}
					}else{
						p.hidePlayer(current);
					}
					}
				
			}else if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GRAY+"Keine Spieler")){
				
				ItemStack VIP = new ItemStack(Material.INK_SACK, 1, (short)10);
				ItemMeta meta = VIP.getItemMeta();
				meta.setDisplayName(ChatColor.GREEN+"Alle Spieler");
				VIP.setItemMeta(meta);
				
				p.getInventory().setItem(1, VIP);
				
				p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1F, 1F);
				
				for(Player current : Bukkit.getOnlinePlayers()){
					p.showPlayer(current);
					}
				
			}else if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE+"Nur VIP´s")){
				
				ItemStack VIP = new ItemStack(Material.INK_SACK, 1, (short)8);
				ItemMeta meta = VIP.getItemMeta();
				meta.setDisplayName(ChatColor.GRAY+"Keine Spieler");
				VIP.setItemMeta(meta);
				
				p.getInventory().setItem(1, VIP);
				
				p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1F, 1F);
				for(Player current : Bukkit.getOnlinePlayers()){
					p.hidePlayer(current);
					}
				
			}
			}else if(e.getMaterial().equals(Material.COMPASS)){
				Inventory inv = p.getPlayer().getServer().createInventory(null, 9*6, ChatColor.YELLOW+"Teleporter");
				
//				
				ItemStack Skywars = new ItemStack(Material.GRASS);
				ItemMeta Skywarsmeta = Skywars.getItemMeta();
				Skywarsmeta.setDisplayName(ChatColor.GREEN+"SkyWars");
				Skywars.setItemMeta(Skywarsmeta);
//				
				ItemStack Knockout = new ItemStack(Material.STICK);
				ItemMeta Knockoutmeta = Knockout.getItemMeta();
				Knockoutmeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
				Knockoutmeta.setDisplayName(ChatColor.RED+"Knockout");
				Knockout.setItemMeta(Knockoutmeta);
//				
				ItemStack spawn = new ItemStack(Material.MAGMA_CREAM);
				ItemMeta spawnmeta = Skywars.getItemMeta();
				spawnmeta.setDisplayName(ChatColor.YELLOW+"Spawn");
				spawn.setItemMeta(spawnmeta);
//				
				ItemStack trump = new ItemStack(Material.GOLD_BLOCK);
				ItemMeta trumpmeta = trump.getItemMeta();
				trumpmeta.setDisplayName(ChatColor.YELLOW+"Trump");
				trump.setItemMeta(trumpmeta);
//				
				ItemStack community = new ItemStack(Material.SKULL_ITEM);
				ItemMeta communitymeta = community.getItemMeta();
				communitymeta.setDisplayName(ChatColor.AQUA+"Community");
				community.setItemMeta(communitymeta);
//				
				ItemStack citybuild = new ItemStack(Material.DIAMOND_PICKAXE);
				ItemMeta citybuildmeta = citybuild.getItemMeta();
				citybuildmeta.setDisplayName(ChatColor.GRAY+"CityBuild");
				citybuild.setItemMeta(citybuildmeta);
//				
				ItemStack bedwars = new ItemStack(Material.BED);
				ItemMeta bedwarsmeta = bedwars.getItemMeta();
				bedwarsmeta.setDisplayName(ChatColor.RED+"BedWars");
				bedwars.setItemMeta(bedwarsmeta);
//		
				ItemStack pl = new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.GRAY.getData());
				ItemMeta plmeta = pl.getItemMeta();
				plmeta.setDisplayName(" ");
				pl.setItemMeta(plmeta);
//		
				for(int i = 0;i<=(9*6)-1 ;i++){
					inv.setItem(i, pl);
				}
				
				inv.setItem(11, trump);
				inv.setItem(15, Knockout);
				inv.setItem(22, spawn);
				inv.setItem(28, Skywars);
				inv.setItem(34, community);
				inv.setItem(39, bedwars);
				inv.setItem(41, citybuild);
				
				p.openInventory(inv);
			}else if(e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR){
				
				Inventory inv = p.getPlayer().getServer().createInventory(null, InventoryType.BREWING, ChatColor.AQUA+"Lobby wechseln");
//				
				int bbb = 0;
				for(String ss:Lobby.showlobbys()){
					if(!ss.contains("PremiumLobby")){
//						
						if(bbb<=2){
//						
						int players = Lobby.getSpieler(ss);
						ItemStack online = new ItemStack(Material.SUGAR,players);
						ItemMeta onlinemeta = online.getItemMeta();
						ArrayList<String> lore = new ArrayList<>();
						if(ss.equals(servername)){
							onlinemeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
							lore.add(ChatColor.AQUA+"Du bist auf dieser Lobby");
						}
						lore.add(ChatColor.GRAY+"Online: "+ChatColor.YELLOW+players+" Spieler");
						onlinemeta.setLore(lore);
						onlinemeta.setDisplayName(ChatColor.GREEN+ss);
						online.setItemMeta(onlinemeta);
						inv.setItem(bbb, online);
						bbb++;
						}else{
							break;
						}
					}
				}
				
				if(bbb<=2){
					for(int i = bbb;i<=2;i++){
						ItemStack offline = new ItemStack(Material.REDSTONE);
						ItemMeta offlinemeta = offline.getItemMeta();
						offlinemeta.setDisplayName(ChatColor.RED+"Diese Lobby ist Offline");
						offline.setItemMeta(offlinemeta);
						inv.setItem(i, offline);
					}
				}
				
				ItemStack item = new ItemStack(Material.GLOWSTONE_DUST);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.YELLOW+"Premium Lobby");
				item.setItemMeta(meta);
				inv.setItem(3, item);
//			
				p.openInventory(inv);
				
			}else if (e.getPlayer().getItemInHand().getType() == Material.TNT){
				if(silenthub.contains(p)){
					silenthub.remove(p);
					p.sendMessage(ChatColor.RED+"Du bist nun nicht mehr in der Silentlobby");
					for(Player pp : Bukkit.getOnlinePlayers()){
						p.showPlayer(pp);
					}
				}else{
				silenthub.add(p);
				p.sendMessage(ChatColor.GREEN+"Du bist nun in der Silentlobby");
				for(Player pp : Bukkit.getOnlinePlayers()){
					p.hidePlayer(pp);
				}
				}
			}else if (e.getPlayer().getItemInHand().getType() == Material.CHEST){
//				if(e.getPlayer().hasPermission("lobby.cosmetics")){
//				Inventory inv = Bukkit.createInventory(null, 9*3, ChatColor.GOLD+"Dein Inventar");
//				for(int i = 0; i<9*3;i++){
//					ItemStack i1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
//					ItemMeta m = i1.getItemMeta();
//					m.setDisplayName(" ");
//					i1.setItemMeta(m);
//					inv.setItem(i, i1);
//				}
//				
//				inv.setItem(9, createitem(Material.IRON_HELMET, ChatColor.GRAY+"Hüte"));
//				inv.setItem(10, createitem(Material.GOLD_BOOTS, ChatColor.GRAY+"Boots"));
//				inv.setItem(11, createitem(Material.MONSTER_EGG, ChatColor.GRAY+"Pets"));
//				inv.setItem(12, createitem(Material.SADDLE, ChatColor.GRAY+"Mounts"));
//				inv.setItem(13, createitem(Material.FIREWORK, ChatColor.GRAY+"Effekte"));
//				inv.setItem(14, createitem(Material.SNOW_BALL, ChatColor.GRAY+"Getgets"));
//				inv.setItem(15, createitem(Material.RECORD_10, ChatColor.GRAY+"Musik"));
//				inv.setItem(16, createitem(Material.IRON_SWORD, ChatColor.GRAY+"Ingame"));
//				inv.setItem(17, createitem(Material.DIAMOND, ChatColor.GRAY+"Spezial"));
////				
//				p.openInventory(inv);
//				}else{
//					p.sendMessage(ChatColor.RED+"Dieses Feature ist im Moment nur für Admins verfügbar");
//				}
			}
			}
		}
	}
	
	public static void connectserver(Player p, String Server){
		ByteArrayOutputStream b = new ByteArrayOutputStream();
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
	
	
	public static ItemStack createitem(Material m,String displayname){
		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayname);
		item.setItemMeta(meta);
		return item;
	}
	
	@EventHandler
	public void onj(PlayerJoinEvent e){
		setLabbymodNick(e.getPlayer(), "--");
		for(Player pp:silenthub){
			pp.hidePlayer(e.getPlayer());
		}
	}
	
	
	
	public static ArrayList<Player> silenthub = new ArrayList<>();
	public static HashMap<String,ItemStack> köpfe = new HashMap<>();
	
	public void setlobbyitems(Player p){
		ItemStack VIP = new ItemStack(Material.INK_SACK, 1, (short)10);
		ItemMeta meta = VIP.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN+"Alle Spieler");
		VIP.setItemMeta(meta);
		p.getInventory().setItem(1, VIP);
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassmeta = compass.getItemMeta();
		compassmeta.setDisplayName(ChatColor.YELLOW+"Teleporter");
		compass.setItemMeta(compassmeta);
		p.getInventory().setItem(0, compass);
		//
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
				skullmeta.setDisplayName(ChatColor.YELLOW+"Freunde");
				skullmeta.setOwner(p.getName());
				skull.setItemMeta(skullmeta);
				p.getInventory().setItem(8, skull);
		//
		ItemStack server = new ItemStack(Material.NETHER_STAR);
		ItemMeta servermeta = server.getItemMeta();
		servermeta.setDisplayName(ChatColor.AQUA+"Lobby wechseln");
		servermeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		server.setItemMeta(servermeta);
		p.getInventory().setItem(7, server);
		//
		ItemStack cosmatic = new ItemStack(Material.CHEST);
		ItemMeta cosmaticmeta = cosmatic.getItemMeta();
		cosmaticmeta.setDisplayName(ChatColor.GOLD+"Dein Inventar");
		cosmatic.setItemMeta(cosmaticmeta);
		p.getInventory().setItem(4, cosmatic);
		//
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Lobby"), new Runnable(){
			@Override
			public void run() {
				
				if(p.hasPermission("lobby.nick")){
					if(MySQLNick.playerExists(p.getUniqueId().toString())){
						if(MySQLNick.getNick(p.getUniqueId().toString()) == 1){
							ItemStack silent = new ItemStack(Material.NAME_TAG);
							ItemMeta silentmeta = silent.getItemMeta();
							silentmeta.setDisplayName(ChatColor.GREEN+"Automatischer Nickname");
							silent.setItemMeta(silentmeta);
							p.getInventory().setItem(6, silent);
						}else{
							ItemStack silent = new ItemStack(Material.NAME_TAG);
							ItemMeta silentmeta = silent.getItemMeta();
							silentmeta.setDisplayName(ChatColor.RED+"Automatischer Nickname");
							silent.setItemMeta(silentmeta);
							p.getInventory().setItem(6, silent);
						}
					}else{
					ItemStack silent = new ItemStack(Material.NAME_TAG);
					ItemMeta silentmeta = silent.getItemMeta();
					silentmeta.setDisplayName(ChatColor.RED+"Automatischer Nickname");
					silent.setItemMeta(silentmeta);
					p.getInventory().setItem(6, silent);
					}
					}
				
				if(p.hasPermission("lobby.silent")){
					ItemStack silent = new ItemStack(Material.TNT);
					ItemMeta silentmeta = silent.getItemMeta();
					silentmeta.setDisplayName(ChatColor.RED+"Silent Lobby");
					silent.setItemMeta(silentmeta);
					p.getInventory().setItem(2, silent);
					}
			}
		},25L);
		//
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
	if(e.getAction().equals(Action.PHYSICAL) && e.getClickedBlock().getType().equals(Material.SOIL)){
	e.setCancelled(true);
	}
	}
	
	public static String nickprefix = ChatColor.DARK_GRAY+"["+ChatColor.DARK_PURPLE+"Nick"+ChatColor.DARK_GRAY+"] "+ChatColor.GRAY;
	
	@EventHandler
	public void onkl(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR||e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(p.getItemInHand().getType() == Material.NAME_TAG){
				if(p.hasPermission("lobby.nick")){
					String s = ""+MySQLNick.getNick(p.getUniqueId().toString());
					if(s.equals("0")){
						p.sendMessage(nickprefix+ChatColor.GREEN+"Der Automatische Nickname wurde aktiviert!");
						ItemStack silent = new ItemStack(Material.NAME_TAG);
						ItemMeta silentmeta = silent.getItemMeta();
						silentmeta.setDisplayName(ChatColor.GREEN+"Automatischer Nickname");
						silent.setItemMeta(silentmeta);
						p.getInventory().setItem(6, silent);
						MySQLNick.setNick(p.getUniqueId().toString(), "1");
					}else{
						p.sendMessage(nickprefix+ChatColor.RED+"Der Automatische Nickname wurde deaktiviert!");
						ItemStack silent = new ItemStack(Material.NAME_TAG);
						ItemMeta silentmeta = silent.getItemMeta();
						silentmeta.setDisplayName(ChatColor.RED+"Automatischer Nickname");
						silent.setItemMeta(silentmeta);
						p.getInventory().setItem(6, silent);
						MySQLNick.setNick(p.getUniqueId().toString(), "0");
					}
				}else{
					p.sendMessage(Main.nickprefix+ChatColor.RED+"Du hast keine Rechte dazu!");
				}
			}
		}
	}
	static int actioni = 0;
	static String action = "";
	public static void startactionbar(){
		List<String> list = act.getStringList("Actionbar");
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Lobby"), new Runnable(){
			@Override
			public void run() {
				action = ChatColor.translateAlternateColorCodes('&', list.get(actioni));
				if(list.size() >actioni+1){
				actioni++;
				}else{
					actioni = 0;
				}
			}
		}, 10, 20*10);
		startshower();
	}
	public static void startshower(){
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Lobby"), new Runnable(){
			@Override
			public void run() {
				for(Player pp : Bukkit.getOnlinePlayers()){
					ActionBar.sendActionBar(pp, action);
				}
			}
			}, 20, 20);
	}
	
	public static ArrayList<Player> list = new ArrayList<>();

	@EventHandler
	public void onmove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		
		if(p.getLocation().getBlock().getType() == Material.WOOD_PLATE){
			Vector v = p.getLocation().getDirection().multiply(7D).setY(1.2D);
			p.setVelocity(v);
			p.playSound(p.getLocation(), Sound.CAT_HISS, 1F, 1F);
		}
		
		if(p.getLocation().getBlockY() >= 40){
			Location loc = p.getLocation().subtract(0, 0.1, 0);
			p.teleport(loc);
		}
		
	}

	public static NPC npc =null;
	@EventHandler
	public void onsn(PlayerJoinEvent e){
//		-574 13 200
		npc.spawn(e.getPlayer());
			PacketReader pr = new PacketReader(e.getPlayer());
            pr.inject();
	}
	
	@EventHandler
	public void onPlayerInteractEvenFt(PlayerInteractEvent e) {
	if(e.getAction().equals(Action.PHYSICAL) && e.getClickedBlock().getType().equals(Material.SOIL)){
	e.setCancelled(true);
	}
	}
	
	@EventHandler
	public void onfire(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK||e.getAction() == Action.LEFT_CLICK_BLOCK){
		if(e.getClickedBlock() != null ||e.getClickedBlock().getType() != Material.AIR){
		Location loc = e.getClickedBlock().getLocation();
		loc.add(0, 1, 0);
		if(loc.getBlock().getType()==Material.FIRE){
			e.setCancelled(true);
			loc.getBlock().setType(Material.FIRE);
		}
		}
		}
	}

	@EventHandler
	public void oni(PlayerInteractAtEntityEvent e){
		if(e.getRightClicked() instanceof ArmorStand){
			e.setCancelled(true);
		}
	}
	
	public static HashMap<Player,Skin> skins = new HashMap<>();
	
	@EventHandler
	public void itemdrop(EntitySpawnEvent e){
		if(e.getEntityType() == EntityType.EGG){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onchat(PlayerChatEvent e){
		e.setCancelled(true);
		Player p = e.getPlayer();
		if(!de.gamelos.friends.Main.Statusset.contains(p)) {
		for(Player pp:Bukkit.getOnlinePlayers()){
			pp.sendMessage(PermissionsAPI.getchatprefix(p.getUniqueId().toString())+p.getName()+ChatColor.DARK_GRAY+" >> "+ChatColor.GRAY+e.getMessage());
		}	
		}
	}
	
	
    public void sendmsgtobungee(String msg, Player p) {
    	ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("data");
			out.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.sendPluginMessage(Bukkit.getPluginManager().getPlugin("Lobby"), "BungeeCord", b.toByteArray());
    }
    
	public void setLabbymodNick(Player p, String nickname){
//		 ßßßßßßßßßßßßßßßßßßßßßßßßßßßß
				    JsonObject pointsObject = new JsonObject();
				    pointsObject.addProperty( nickname, 1 );

				    // Sending the server message
				    LabyModPlugin.getInstance().sendServerMessage( p, "Nickname", pointsObject );
//		 ßßßßßßßßßßßßßßßßßßßßßßßßßßßß
	}
}
