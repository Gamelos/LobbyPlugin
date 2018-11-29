package de.gamelos.lobby.Main;

import java.util.List;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class NPC extends Reflections {

	
	int entityID;
	Location location;
	GameProfile gameprofile;
	Plugin pl = Bukkit.getPluginManager().getPlugin("Lobby");
	
	public NPC(String name,Location location){
		entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
		gameprofile = new GameProfile(UUID.randomUUID(), name);
		changeSkin();
		this.location = location.clone();
	}
	
	public void spawn(Player p){
		PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
		
		setValue(packet, "a", entityID);
		setValue(packet, "b", gameprofile.getId());
		setValue(packet, "c", (int)MathHelper.floor(location.getX() * 32.0D));
		setValue(packet, "d", (int)MathHelper.floor(location.getY() * 32.0D));
		setValue(packet, "e", (int)MathHelper.floor(location.getZ() * 32.0D));
		setValue(packet, "f", (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));
		setValue(packet, "g", (byte) ((int) (location.getPitch() * 256.0F / 360.0F)));
		setValue(packet, "h", 0);
		DataWatcher w = new DataWatcher(null);
		w.a(6,(float)20);
		w.a(10,(byte)127);
		setValue(packet, "i", w);
		addToTablist(p);
		sendPacket(packet, p);
		headRotation(-100, 6);
		Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable(){
			@Override
			public void run() {
				rmvFromTablist(p);
			}
		},20);
	}
	
    public void changeSkin(){
        String value = "eyJ0aW1lc3RhbXAiOjE1MzE5MTUwMTIwMzUsInByb2ZpbGVJZCI6IjM0NWFiNDVkYmE2ZDQ3OGM5NjVhNDFkNTAwYzMxMDI0IiwicHJvZmlsZU5hbWUiOiJKYXl6b3giLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2EwMWEzNTMxY2I1ZjNiYzZmY2I0ZmZiYjU4YTUzNzdiYmQ1YmJiMTQyYmMyMzk4MmIwZTQwOTEyMWYyZDY1M2MifX19";
        String signature = "Wl5I0pcKMBT5eBnLRVu6ng2Kku3TF3ARj0/1zaJ+FhX5GnF/c5Ze/NHfmvU/7LfF7O0T17+udvh1QoLcoyHIj8ncZdnC6WuATVvtLDmaef9gGuHYd5dKYtWY1qLTuf2Dy2LvJBRCgQ+tQqTWriu45Hlyk+75n9mIAVtYvRkAwK6Ubx2Zxd+6i1Ok7J0i+n4O/6WtwLXVC/TkLxVmliD3STZIDlpLdU8w8AQUTI/88z0uBjz+cW3GZERZ/EJDauNfUVavR4l0QrJvBU6WPSBLV4xu+zr0CLtDyU/Y0idDgDKHzLpZXB/MzfG2oY9+z4+PE+oF7b1LJfl6d0/L/ST/XoVnVCkKqsb/BEW/jEZ7VYERWgUJXx3Axab6UHmgRIE4gc1a4ZeiN2yv2GjEDzj1ad+5XLe+MdyR6NMLRPRuOJy24InDCh/+VHLg+46clDkOlGIuTUEYIspqVu7AADQr/mzIP/lgQIZ4VEzz5TbO9Y4Et9D5n75RJtrtCyNzi6vYQ9CUu10/fbLMPUy6Zy/2Vh2R+3gNihfyHouBYAkaK12v3zMHVxE/8Lovl9ckkLeJunykgENpDWsMc6GyRWi1Wumr8MvyKnhvkI13G7enh72lvYVtkLODFOTfuxo+pDjYhUve2RO5kTPtcvKb/6lherWXSywMLl7SujWBjq+ohIs=";
        gameprofile.getProperties().put("textures", new Property("textures", value, signature));
}
	
	public void destroy(){
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
		sendPacket(packet);
	}
	
	public void addToTablist(Player p){
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
		PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
		@SuppressWarnings("unchecked")
		List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
		players.add(data);
		
		setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
		setValue(packet, "b", players);
		
		sendPacket(packet, p);
	}
	
	public void rmvFromTablist(Player p){
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
		PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
		@SuppressWarnings("unchecked")
		List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
		players.add(data);
		
		setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
		setValue(packet, "b", players);
		
		sendPacket(packet,p);
	}
	
    public void headRotation(float yaw,float pitch){
        PacketPlayOutEntityLook packet = new PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
        PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
        setValue(packetHead, "a", entityID);
        setValue(packetHead, "b", getFixRotation(yaw));
       

        sendPacket(packet);
        sendPacket(packetHead);
}
    public int getFixLocation(double pos){
        return (int)MathHelper.floor(pos * 32.0D);
}

public byte getFixRotation(float yawpitch){
        return (byte) ((int) (yawpitch * 256.0F / 360.0F));
}
	
public int getEntityID(){
	return entityID;
}

}