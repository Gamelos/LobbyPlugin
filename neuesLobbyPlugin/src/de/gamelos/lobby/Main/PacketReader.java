package de.gamelos.lobby.Main;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.lang.reflect.Field;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.Packet;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketReader {

	Player player;
	Channel channel;
	
	public PacketReader(Player player) {
		this.player = player;
	}
	
	public void inject(){
		CraftPlayer cPlayer = (CraftPlayer)this.player;
		channel = cPlayer.getHandle().playerConnection.networkManager.channel;
		channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {@Override protected void decode(ChannelHandlerContext arg0, Packet<?> packet,List<Object> arg2) throws Exception {arg2.add(packet);readPacket(packet);}});
	}
	
	public void uninject(){
		if(channel.pipeline().get("PacketInjector") != null){
			channel.pipeline().remove("PacketInjector");
		}
	}
	

	public void readPacket(Packet<?> packet){
		if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")){
			int id = (Integer)getValue(packet, "a");
			if(Main.npc.getEntityID() == id){
			System.out.println(getValue(packet, "action").toString());
			
			
				if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK")){
					player.sendMessage(ChatColor.GRAY+"Du wirst mit dem Server verbunden...");
					Main.connectserver(player, "Community-1");
				}else if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")){
					player.sendMessage(ChatColor.GRAY+"Du wirst mit dem Server verbunden...");
					Main.connectserver(player, "Community-1");
					}
			}
		}
	}
	

	public void setValue(Object obj,String name,Object value){
		try{
		Field field = obj.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(obj, value);
		}catch(Exception e){}
	}
	
	public Object getValue(Object obj,String name){
		try{
		Field field = obj.getClass().getDeclaredField(name);
		field.setAccessible(true);
		return field.get(obj);
		}catch(Exception e){}
		return null;
	}
	
}