package fr.ezzud.moreweapons.utils;

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

public class FishLauncherUtil {
	
	
	static MoreWeapons plugin = MoreWeapons.getInstance();
	
	
	public static Entity spawnFish(Player player, double x, double z, double y) {
		int fishValue = RandomUtil.getRandomNumberInRange(1,4);
		World world = player.getWorld();
		Location loc = player.getLocation().add(x, y, z);
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
		Entity entity = world.spawnEntity(loc, fish);
		return entity;
	}
	
	@SuppressWarnings("deprecation")
	public static Runnable startBomb(ItemStack item, ItemMeta meta, Entity entity, World world, Location location, Boolean hasDelay) {
		Runnable runnable2 = new Runnable() {
			
			@Override
			public void run() {
				if(hasDelay) {
					((Damageable)meta).setDamage((item.getType().getMaxDurability() - 1));
		        	item.setItemMeta(meta);
				}
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
		                	world.playSound(location, Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1, 1);
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
		return runnable2;
	}
	
	
	public static void launchMultipleFish(ItemStack item, ItemMeta meta, int points, Player player) {
		for (int i = 0; i < 360; i += 360/points) {
			double angle = (i * Math.PI / 180);
			double x = 3 * Math.cos(angle);
			double z = 3 * Math.sin(angle);
			Location loc = player.getLocation().add(x, 3, z);
			Block block = player.getWorld().getBlockAt(loc);
			Vector playerDirection = player.getLocation().getDirection();
			if(block.getType().equals(Material.AIR)) {
				
				Entity entity = FishLauncherUtil.spawnFish(player, x, z, 3);
				entity.setMetadata("fishBomb", new FixedMetadataValue(plugin, 1));
				entity.setVelocity(playerDirection.multiply(2));

				Runnable runnable = FishLauncherUtil.startBomb(item, meta, entity, player.getWorld(), player.getLocation(), true);
				ItemCooldown.cooldownItem(10, item, player, runnable);
				
				
				
			}
		  
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public static void launchSingleFish(ItemStack item, ItemMeta meta, Player player) {
		Vector playerDirection = player.getLocation().getDirection();
		Entity entity = FishLauncherUtil.spawnFish(player, 0, 0, 1.5);
		
		entity.setMetadata("fishBomb", new FixedMetadataValue(plugin, 1));
		entity.setVelocity(playerDirection.multiply(3));
		
		
		Runnable runnable = FishLauncherUtil.startBomb(item, meta, entity, player.getWorld(), player.getLocation(), false);
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, runnable, 0, 10);
				
				
				
			
		  
		
	}
	
}
