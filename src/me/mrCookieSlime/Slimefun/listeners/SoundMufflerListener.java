package me.mrCookieSlime.Slimefun.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.machines.SoundMuffler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

public class SoundMufflerListener extends PacketAdapter implements Listener {

    public SoundMufflerListener(Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.NAMED_SOUND_EFFECT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        int distance = SoundMuffler.DISTANCE * SoundMuffler.DISTANCE;
        if (event.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT) {
            int x = event.getPacket().getIntegers().read(0) >> 3;
            int y = event.getPacket().getIntegers().read(1) >> 3;
            int z = event.getPacket().getIntegers().read(2) >> 3;
            Location loc = new Location(event.getPlayer().getWorld(), x, y, z);
            for (String sm : BlockStorage.loaded_mufflers) {
            	Location l = deserializeLocation(sm);
                if (loc.getWorld().equals(l.getWorld()) && loc.distanceSquared(l) < distance) {
                	if(BlockStorage.hasBlockInfo(l) && BlockStorage.getBlockInfo(l, "enabled") != null)
                	{
                		if (BlockStorage.getBlockInfo(l, "enabled").equals("true"))
                		{
                			try{
		                    	if(Integer.parseInt(BlockStorage.getBlockInfo(l, "energy-charge")) >= 8)
		                    	{
				                	int volume = 10;
				                	try{
				                		volume = Integer.parseInt(BlockStorage.getBlockInfo(l, "volume"));
				                	}catch(Exception e){
				                		System.out.println("SOUND MUFFLERS VOLUME COULD NOT BE FOUND. DEFAULTING TO 10.");
				                	}
				                	if (volume == 0) {
				                        event.setCancelled(true);
				                    } else {
				                        event.getPacket().getFloat().write(0, (float) volume / 100.0f);
				                    }
		                    	}
                			}catch(Exception e){
                				System.out.println("SOUND MUFFLERS CHARGE COULD NOT BE FOUND. DEFAULTING TO 0.");
                				BlockStorage.addBlockInfo(l, "energy-charge", "0");
                			}
                		}
                	}
                	else
                	{
                		System.out.println("GLITCHED MUFFLER AT " + l);
                		BlockStorage.addBlockInfo(l, "enabled", "true");
                	}
                }
            }
        }
    }

	private static Location deserializeLocation(String l) {
		try {
			World w = Bukkit.getWorld(l.split(";")[0]);
			if (w != null) return new Location(w, Integer.parseInt(l.split(";")[1]), Integer.parseInt(l.split(";")[2]), Integer.parseInt(l.split(";")[3]));
		} catch(NumberFormatException x) {
		}
		return null;
	}

    public void start() {
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }
}
