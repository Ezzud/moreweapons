package fr.ezzud.moreweapons.weapons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.ezzud.moreweapons.MoreWeapons;
import fr.ezzud.moreweapons.others.InventoryUtil;
import fr.ezzud.moreweapons.others.ItemCooldown;
import fr.ezzud.moreweapons.others.RandomUtil;


public class FishLauncher {
	static MoreWeapons plugin = MoreWeapons.getInstance();
	private Player player;
	public FishLauncher(Player player) {
		this.player = player;
	}
	
	public void give() {
		ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(1232403);
		meta.setDisplayName("Fish Launcher");
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
		if(InventoryUtil.isInventoryFull(this.player)) {
			this.player.getWorld().dropItem(player.getLocation().add(0, 1, 0), item);
		} else {
			this.player.getInventory().addItem(item);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static void action(Player player) {
		ItemStack item = player.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		if(((Damageable) meta).getDamage() != 0) {
			return;
		}
		int fishValue = RandomUtil.getRandomNumberInRange(1,4);
		EntityType fish = null;
		switch(fishValue) {
			case 1:
				fish = EntityType.COD ;
				break;
			case 2:
				fish = EntityType.SALMON;
				break;
			case 3:
				fish = EntityType.PUFFERFISH;
				break;
			case 4:
				fish = EntityType.TROPICAL_FISH;
				break;
		}
		World world = player.getWorld();
		Location loc = player.getLocation();
		loc.add(0, 1.5, 0);		
		Vector playerDirection = player.getLocation().getDirection();
		Entity entity = world.spawnEntity(loc, fish);
		
		entity.setMetadata("fishBomb", new FixedMetadataValue(plugin, 1));
		entity.setVelocity(playerDirection.multiply(3));
		world.playSound(player.getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 1);
		
		
		BukkitRunnable runnable = new BukkitRunnable(){
            public void run(){
                if(!entity.isValid() ) {
   				 	return;
                }
                Location blockLoc = entity.getLocation();
                blockLoc.add(0, -1, 0);
                Block block = world.getBlockAt(blockLoc.getBlockX(), blockLoc.getBlockY(), blockLoc.getBlockZ());
                
                if(!block.getType().equals(Material.AIR)) {
                	if(entity.hasMetadata("exploding")) {
       				 	return;
       			  	}
                	world.playSound(player.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1, 1);
                	entity.setMetadata("exploding", new FixedMetadataValue(plugin, 1));
                	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
        	            @Override
        	            public void run(){
        	            	if(!entity.isValid()) {
        	            		return;
        	            	}
        	            	World world = entity.getWorld();
        	            	entity.remove();
        	            	world.createExplosion(entity.getLocation(), 2, false);
        	            	
        	            }
        	        }, 6*20);
            		try {
            			Bukkit.getScheduler().cancelTask(this.getTaskId());
            		} catch(IllegalStateException e) {
            			if(e.getCause() == null) {
            				return;
            			}
            			if(e.getCause().getMessage().equals("Not scheduled yet")) {
            				return;
            			}
            		}
   				 	return;
                }
            }
        };
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, runnable, 0, 10);
		
	}
	
	@SuppressWarnings("deprecation")
	public static void secondAction(Player player) {
		World world = player.getWorld();
		ItemStack item = player.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		if(((Damageable) meta).getDamage() != 0) {
			return;
		}
		int points = 5;
		for (int i = 0; i < 360; i += 360/points) {
			double angle = (i * Math.PI / 180);
			double x = 3 * Math.cos(angle);
			double z = 3 * Math.sin(angle);
			Location loc = player.getLocation().add(x, 3, z);
			Block block = world.getBlockAt(loc);
			if(block.getType().equals(Material.AIR)) {
				int fishValue = RandomUtil.getRandomNumberInRange(1,4);
				EntityType fish = null;
				switch(fishValue) {
					case 1:
						fish = EntityType.COD ;
						break;
					case 2:
						fish = EntityType.SALMON;
						break;
					case 3:
						fish = EntityType.PUFFERFISH;
						break;
					case 4:
						fish = EntityType.TROPICAL_FISH;
						break;
				}		
				Vector playerDirection = player.getLocation().getDirection();
				Entity entity = world.spawnEntity(loc, fish);
				
				entity.setMetadata("fishBomb", new FixedMetadataValue(plugin, 1));
				entity.setVelocity(playerDirection.multiply(2));

				Runnable runnable2 = new Runnable() {
					@Override
					public void run() {
						((Damageable)meta).setDamage((item.getType().getMaxDurability() - 1));
			        	item.setItemMeta(meta);
			        	BukkitRunnable runnable = new BukkitRunnable(){
				            public void run(){
				                if(!entity.isValid() ) {
				   				 	return;
				                }
				                Location blockLoc = entity.getLocation();
				                blockLoc.add(0, -1, 0);
				                Block block = world.getBlockAt(blockLoc.getBlockX(), blockLoc.getBlockY(), blockLoc.getBlockZ());
				                
				                if(!block.getType().equals(Material.AIR)) {
				                	if(entity.hasMetadata("exploding")) {
				       				 	return;
				       			  	}
				                	world.playSound(player.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1, 1);
				                	entity.setMetadata("exploding", new FixedMetadataValue(plugin, 1));
				                	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				        	            @Override
				        	            public void run(){
				        	            	if(!entity.isValid()) {
				        	            		return;
				        	            	}
				        	            	World world = entity.getWorld();
				        	            	entity.remove();
				        	            	world.createExplosion(entity.getLocation(), 2, false);
				        	            	
				        	            }
				        	        }, 6*20);
				            		try {
				            			Bukkit.getScheduler().cancelTask(this.getTaskId());
				            		} catch(IllegalStateException e) {
				            			if(e.getCause() == null) {
				            				return;
				            			}
				            			if(e.getCause().getMessage().equals("Not scheduled yet")) {
				            				return;
				            			}
				            		}
				   				 	return;
				                }
				            }
				        };
				        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, runnable, 0, 10);
					}
		        	
				};
				ItemCooldown.cooldownItem(10, item, player, runnable2);
				
				
				
			}
		  
		}
		world.playSound(player.getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 1);
	}
	
}
