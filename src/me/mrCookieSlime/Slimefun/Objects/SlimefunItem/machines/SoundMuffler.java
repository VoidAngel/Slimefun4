package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.machines;

import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunBlockHandler;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.UnregisterReason;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public abstract class SoundMuffler extends SlimefunItem {

    public static final int DISTANCE = 8;
    private static final int[] border = {1, 2, 3, 4, 5, 6, 7};
    
	public SoundMuffler(Category category, ItemStack item, String name, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, name, recipeType, recipe);
		
		new BlockMenuPreset(name, "&3Sound Muffler") {
			
			@Override
			public void init() {
				constructMenu(this);
			}

			@Override
			public void newInstance(final BlockMenu menu, final Block b) {
				int volume = 10;
				if (!BlockStorage.hasBlockInfo(b) || BlockStorage.getLocationInfo(b.getLocation(), "enabled") == null) {
					BlockStorage.addBlockInfo(b, "volume", String.valueOf(volume));
					menu.replaceExistingItem(8, new CustomItem(new MaterialData(Material.SULPHUR), "&7Enabled: &4\u2718", "", "&e> Click to enable this Machine"));
					menu.replaceExistingItem(0, new CustomItem(new MaterialData(Material.PAPER), 
							"&eVolume: &b" + volume, 
							"&7Valid value range: 0-100",
			                "&7L-click: -10",
			                "&7R-click: +10",
			                "&7With shift held: +/-1"));
					
					menu.addMenuClickHandler(0, new MenuClickHandler() {
						@Override
						public boolean onClick(Player p, int arg1, ItemStack arg2, ClickAction arg3) {
							int newVolume = 0;
							int lastVolume = volume;
							
							if(arg3.isRightClicked())
							{
								if(arg3.isShiftClicked())
									newVolume = lastVolume + 1 <= 100? lastVolume + 1 : 100;
								else
									newVolume = lastVolume + 10 <= 100? lastVolume + 10 : 100;
							}
							else
							{
								if(arg3.isShiftClicked())
									newVolume = lastVolume - 1 >= 0? lastVolume - 1 : 0;
								else
									newVolume = lastVolume - 10 >= 0? lastVolume - 10 : 0;
							}
								
							BlockStorage.addBlockInfo(b, "volume", String.valueOf(newVolume));
							newInstance(menu, b);
							return false;
						}
					});
					menu.addMenuClickHandler(8, new MenuClickHandler() {

						@Override
						public boolean onClick(Player p, int arg1, ItemStack arg2, ClickAction arg3) {
							BlockStorage.addBlockInfo(b, "enabled", "true");
							newInstance(menu, b);
							return false;
						}
					});
				}
				else if(BlockStorage.getLocationInfo(b.getLocation(), "enabled").equals("false"))
				{
					menu.replaceExistingItem(8, new CustomItem(new MaterialData(Material.SULPHUR), "&7Enabled: &4\u2718", "", "&e> Click to enable this Machine"));
					menu.replaceExistingItem(0, new CustomItem(new MaterialData(Material.PAPER), 
							"&eVolume: &b" + BlockStorage.getLocationInfo(b.getLocation(), "volume"), 
							"&7Valid value range: 0-100",
			                "&7L-click: -10",
			                "&7R-click: +10",
			                "&7With shift held: +/-1"));
					
					menu.addMenuClickHandler(0, new MenuClickHandler() {
						@Override
						public boolean onClick(Player p, int arg1, ItemStack arg2, ClickAction arg3) {
							int newVolume = 0;
							int lastVolume = Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "volume"));
							
							if(arg3.isRightClicked())
							{
								if(arg3.isShiftClicked())
									newVolume = lastVolume + 1 <= 100? lastVolume + 1 : 100;
								else
									newVolume = lastVolume + 10 <= 100? lastVolume + 10 : 100;
							}
							else
							{
								if(arg3.isShiftClicked())
									newVolume = lastVolume - 1 >= 0? lastVolume - 1 : 0;
								else
									newVolume = lastVolume - 10 >= 0? lastVolume - 10 : 0;
							}
								
							BlockStorage.addBlockInfo(b, "volume", String.valueOf(newVolume));
							newInstance(menu, b);
							return false;
						}
					});
					menu.addMenuClickHandler(8, new MenuClickHandler() {

						@Override
						public boolean onClick(Player p, int arg1, ItemStack arg2, ClickAction arg3) {
							BlockStorage.addBlockInfo(b, "enabled", "true");
							newInstance(menu, b);
							return false;
						}
					});
				}
				else {
					menu.replaceExistingItem(8, new CustomItem(new MaterialData(Material.REDSTONE), "&7Enabled: &2\u2714", "", "&e> Click to disable this Machine"));
					menu.replaceExistingItem(0, new CustomItem(new MaterialData(Material.PAPER), 
							"&eVolume: &b" + BlockStorage.getLocationInfo(b.getLocation(), "volume"), 
							"&7Valid value range: 0-100",
			                "&7L-click: -10",
			                "&7R-click: +10",
			                "&7With shift held: +/-1"));
					
					menu.addMenuClickHandler(0, new MenuClickHandler() {
						@Override
						public boolean onClick(Player p, int arg1, ItemStack arg2, ClickAction arg3) {
							int newVolume = 0;
							int lastVolume = Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "volume"));
							
							if(arg3.isRightClicked())
							{
								if(arg3.isShiftClicked())
									newVolume = lastVolume + 1 <= 100? lastVolume + 1 : 100;
								else
									newVolume = lastVolume + 10 <= 100? lastVolume + 10 : 100;
							}
							else
							{
								if(arg3.isShiftClicked())
									newVolume = lastVolume - 1 >= 0? lastVolume - 1 : 0;
								else
									newVolume = lastVolume - 10 >= 0? lastVolume - 10 : 0;
							}
								
							BlockStorage.addBlockInfo(b, "volume", String.valueOf(newVolume));
							newInstance(menu, b);
							return false;
						}
					});
					menu.addMenuClickHandler(8, new MenuClickHandler() {

						@Override
						public boolean onClick(Player p, int arg1, ItemStack arg2, ClickAction arg3) {
							BlockStorage.addBlockInfo(b, "enabled", "false");
							newInstance(menu, b);
							return false;
						}
					});
				}
			}


			@Override
			public boolean canOpen(Block b, Player p) {
				return p.hasPermission("slimefun.inventory.bypass") || CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true);
			}

			@Override
			public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
				return new int[0];
			}
		};
		
		registerBlockHandler(name, new SlimefunBlockHandler() {
			
			@Override
			public void onPlace(Player p, Block b, SlimefunItem item) {
				BlockStorage.loaded_mufflers.add(b.getWorld().getName() + ";" + b.getX() + ";" + b.getY()+ ";" + b.getZ());
				BlockStorage.addBlockInfo(b, "enabled", "false");
				BlockStorage.addBlockInfo(b, "volume", "10");
			}
			
			@Override
			public boolean onBreak(Player p, Block b, SlimefunItem item, UnregisterReason reason) {
				BlockStorage.loaded_mufflers.remove(b.getWorld().getName() + ";" + b.getX() + ";" + b.getY()+ ";" + b.getZ());
				return true;
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	protected void constructMenu(BlockMenuPreset preset) {
		for (int i: border) {
			preset.addItem(i, new CustomItem(new MaterialData(Material.STAINED_GLASS_PANE, (byte) 7), " "),
			new MenuClickHandler() {

				@Override
				public boolean onClick(Player arg0, int arg1, ItemStack arg2, ClickAction arg3) {
					return false;
				}
						
			});
		}
	}
	
	public abstract int getEnergyConsumption();
	
	@Override
	public void register(boolean slimefun) {
		addItemHandler(new BlockTicker() {
			
			@Override
			public void tick(Block b, SlimefunItem sf, Config data) {
				try {
					SoundMuffler.this.tick(b);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void uniqueTick() {
			}

			@Override
			public boolean isSynchronized() {
				return true;
			}
		});

		super.register(slimefun);
	}
	
	protected void tick(Block b) throws Exception {
		ChargableBlock.addCharge(b, -getEnergyConsumption());
	}
}