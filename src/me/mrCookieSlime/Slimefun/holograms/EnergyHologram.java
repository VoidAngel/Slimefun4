package me.mrCookieSlime.Slimefun.holograms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import me.mrCookieSlime.CSCoreLibPlugin.general.Math.DoubleHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.ArmorStandFactory;
import me.mrCookieSlime.Slimefun.SlimefunStartup;

public class EnergyHologram {
	
	public static void update(Location l, double supply, double demand) {
		update(l, demand > supply ? ("&4&l- &c" + DoubleHandler.getFancyDouble(Math.abs(supply - demand)) + " &7J &e\u26A1"): ("&2&l+ &a" + DoubleHandler.getFancyDouble(supply - demand) + " &7J &e\u26A1"));
	}
	
	public static void update(final Location l, final String name) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SlimefunStartup.instance, new Runnable() {
			
			@Override
			public void run() {
				ArmorStand hologram = getArmorStand(l);
				hologram.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
			}
		});
	}	
	
	public static void remove(final Location l) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SlimefunStartup.instance, new Runnable() {
			
			@Override
			public void run() {
				ArmorStand hologram = getArmorStand(l);
				hologram.remove();
			}
		});
	}
	
	private static ArmorStand getArmorStand(Location loc) {
		Location l = loc.clone();
		l.add(0.5, -0.7F, 0.5);
		for (Entity n: l.getChunk().getEntities()) {
			if (n instanceof ArmorStand) {
				if (n.getCustomName() != null && l.distanceSquared(n.getLocation()) < 0.4D) return (ArmorStand) n;
			}
		}
		
		ArmorStand hologram = ArmorStandFactory.createHidden(l);
		return hologram;
	}

}
