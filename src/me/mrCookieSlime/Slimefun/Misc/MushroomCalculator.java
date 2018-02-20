package me.mrCookieSlime.Slimefun.Misc;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class MushroomCalculator {
	
	private static final BlockFace[] faces = new BlockFace[] {BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST, BlockFace.NORTH_NORTH_EAST};
	
	public static void getMush(Location origin, Location anchor, List<Location> list) {
		int max = 200;
		if (list.size() > max) return;
		
		for (BlockFace face: faces) {
			Block next = anchor.getBlock().getRelative(face);
			if (next.getType() == anchor.getBlock().getType() && !list.contains(next.getLocation())) {
				list.add(next.getLocation());
				getMush(origin, next.getLocation(), list);
			}
		}
	}
}
