package de.gamelos.lobby.Main;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.gamelos.PermissionsAPI.PermissionsAPI;
import main.java.com.bobacadodl.imgmessage.ImageChar;
import main.java.com.bobacadodl.imgmessage.ImageMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public  class ChannelListener implements PluginMessageListener{

	@SuppressWarnings("unused")
	private final Main plugin;
	
	public ChannelListener(Main plugin){
		this.plugin = plugin;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message){
		DataInputStream stream = new DataInputStream(new ByteArrayInputStream(message));
		String subChannel;
		try {
			subChannel = stream.readUTF();
			if(subChannel.equals("data")){
				String input = stream.readUTF();
				String[] ss = input.split("/");
//				===============
				if(ss[0].equals("promote")){
				String spieler = ss[1];
				String server = ss[2];
				URL url = null;
				try {
					url = new URL("https://minotar.net/avatar/"+spieler+"/8.png");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				ImageMessage imageMessage = null;
				try{
					imageMessage = new ImageMessage(ImageIO.read(url), 8, ImageChar.BLOCK.getChar());
				}catch(IOException ex){
					ex.printStackTrace();
				}
				int i = 1;
				for(String lines : imageMessage.getLines()){
					if(i!=4 && i!=5){
						Bukkit.broadcastMessage(lines);
					}
					if(i==4){
						Bukkit.broadcastMessage(lines + " " +PermissionsAPI.getchatprefix(SpielerUUID.getUUID(spieler)) +spieler+ " §7läd dich in seine Runde");
					}
					if(i==5){
						TextComponent message1 = new TextComponent(lines + "   §6"+server+"§7 ein! ");
						TextComponent accept = new TextComponent("[Mitspielen]");
						accept.setColor(ChatColor.GREEN);
						accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fastjump "+server));
						accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN+"Joine in die Runde").create()));
						message1.addExtra(accept);
						for(Player pp:Bukkit.getOnlinePlayers()){
						pp.spigot().sendMessage(message1);
						}
					}
					i++;
				}
				
				}else if(ss[0].equals("servername")){
					String name = ss[1];
					Main.servername = name;
					
					}
//				===============
				
				notifyAll();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
