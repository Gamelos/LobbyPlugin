package de.gamelos.lobby.Main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Cosmatics implements Listener {

	public static ArrayList<ItemStack> list = new ArrayList<>();
//	
//	public static void enable(){
//		list.add(createkopf("Gamelos"));
//		list.add(createkopf("JayCTV"));
//		list.add(createkopf("GommeHD"));
//		list.add(createkopf("FixxYT"));
//		list.add(createkopf("TomsTopic"));
//		list.add(createkopf("Rewinside"));
//		list.add(createkopf("CraftingPat"));
//	}
	
//	@EventHandler
//	public void onkli(InventoryClickEvent e){
//		Player p = (Player) e.getWhoClicked();
//		if(e.getClickedInventory().getTitle().equals(ChatColor.GOLD+"Dein Inventar")){
//			if(e.getCurrentItem() !=null){
//				if(e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE){
//					if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY+"Hüte")){
//						Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GRAY+"Hüte");
//						for(ItemStack item : list){
//							inv.addItem(item);	
//						}
//						p.openInventory(inv);
//				}else if(e.getCurrentItem().getType() == Material.GOLD_BOOTS){
//						Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GRAY+"Boots");
//						inv.addItem(createboots(ChatColor.GRAY+"Geister Boots", Color.GRAY));
//						inv.addItem(createboots(ChatColor.GRAY+"Snow Boots", Color.WHITE));
//						p.openInventory(inv);
//				}else if(e.getCurrentItem().getType() == Material.MONSTER_EGG){
//					Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GRAY+"Pets");
//					inv.addItem(createbyteitem(Material.MONSTER_EGG, (byte)90, ChatColor.LIGHT_PURPLE+"Schwein"));
//					inv.addItem(createbyteitem(Material.MONSTER_EGG, (byte)93, ChatColor.YELLOW+"Huhn"));
//					p.openInventory(inv);
//					}else if(e.getCurrentItem().getType() == Material.SADDLE){
//						Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GRAY+"Mounts");
//						p.openInventory(inv);
//						}else if(e.getCurrentItem().getType() == Material.FIREWORK){
//							Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GRAY+"Effekte");
//							p.openInventory(inv);
//							}else if(e.getCurrentItem().getType() == Material.SNOW_BALL){
//								Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GRAY+"Getgets");
//								p.openInventory(inv);
//								}else if(e.getCurrentItem().getType() == Material.RECORD_10){
//									Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GRAY+"Musik");
//									p.openInventory(inv);
//									}else if(e.getCurrentItem().getType() == Material.IRON_SWORD){
//										Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GRAY+"Ingame");
//										p.openInventory(inv);
//										}else if(e.getCurrentItem().getType() == Material.DIAMOND){
//											Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GRAY+"Spezial");
//											p.openInventory(inv);
//											}
//			}
//			}
//		}else if(e.getClickedInventory().getTitle().equals(ChatColor.GRAY+"Hüte")){
//			if(e.getCurrentItem() != null){
//				p.getInventory().setHelmet(e.getCurrentItem());
//				p.closeInventory();
//			}
//		}else if(e.getClickedInventory().getTitle().equals(ChatColor.GRAY+"Boots")){
//			if(e.getCurrentItem() != null){
//				p.getInventory().setBoots(e.getCurrentItem());
//				p.closeInventory();
//			}
//		}
//	}
//	
//	public static ItemStack createbyteitem(Material m, byte b,String displayname){
//		ItemStack item = new ItemStack(m,1,b);
//		ItemMeta meta = item.getItemMeta();
//		meta.setDisplayName(displayname);
//		item.setItemMeta(meta);
//		return item;
//	}
//	
//	
//	public static ItemStack createkopf(String skullowner){
//		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
//		SkullMeta meta = (SkullMeta) item.getItemMeta();
//		meta.setDisplayName(ChatColor.YELLOW+skullowner);
//		meta.setOwner(skullowner);
//		item.setItemMeta(meta);
//		return item;
//	}
//	
//	public static ItemStack createboots(String displayname, Color white){
//		ItemStack schuhe = new ItemStack(Material.LEATHER_BOOTS);
//		LeatherArmorMeta schuheMeta = (LeatherArmorMeta)schuhe.getItemMeta();
//		schuheMeta.setColor(white);
//		schuheMeta.setDisplayName(displayname);
//		schuhe.setItemMeta(schuheMeta);
//		return schuhe;
//	}
//	
//	@SuppressWarnings("deprecation")
//	@EventHandler
//	public void onmove(PlayerMoveEvent e){
//		Player p = e.getPlayer();
//		if(p.getInventory().getBoots() != null){
//			if(p.getInventory().getBoots().getItemMeta().getDisplayName().equals(ChatColor.GRAY+"Geister Boots")){
//				p.playEffect(p.getLocation(), Effect.SMOKE, 5);
//				p.playEffect(p.getLocation(), Effect.LARGE_SMOKE, 5);
//			}if(p.getInventory().getBoots().getItemMeta().getDisplayName().equals(ChatColor.GRAY+"Snow Boots")){
//				p.playEffect(p.getLocation(), Effect.SNOWBALL_BREAK, 5);
//				org.bukkit.Location loc = p.getLocation();
//				org.bukkit.Location l1 = p.getLocation();
//				if(loc.subtract(0, 1, 0).getBlock().getType() != Material.AIR){
//				l1.getBlock().setType(Material.SNOW);
//				Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Lobby"), new Runnable(){
//					@Override
//					public void run() {
//						l1.getBlock().setType(Material.AIR);
//					}
//				},50);
//			}
//			}
//		}
//	}
//	
//	@EventHandler
//	public void ons(PlayerToggleSneakEvent e){
//		Player p = e.getPlayer();
//		if(p.getInventory().getBoots().getItemMeta().getDisplayName().equals(ChatColor.GRAY+"Geister Boots")){
//		if(p.hasPotionEffect(PotionEffectType.INVISIBILITY)){
//			p.removePotionEffect(PotionEffectType.INVISIBILITY);
//		}else{
//			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
//		}
//		}
//	}
	
}
