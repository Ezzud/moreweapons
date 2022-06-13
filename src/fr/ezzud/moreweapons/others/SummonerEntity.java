package fr.ezzud.moreweapons.others;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import fr.ezzud.moreweapons.MoreWeapons;

public class SummonerEntity {
	
	public static void summonGolems(int quantity, Player player, MoreWeapons plugin) {
		for(int i = 0; i < quantity; i++) {
			World world = player.getWorld();
			int radius = RandomUtil.getRandomNumberInRange(0,3);
			Block block = RandomUtil.getRandomBlockAround(player, radius);
			Location blockLocation = new Location(world, block.getX(), block.getY(), block.getZ());
			blockLocation.setY(blockLocation.getBlockY() + 2);
			Entity entity = world.spawnEntity(blockLocation, EntityType.IRON_GOLEM);
			world.spawnParticle(Particle.PORTAL, blockLocation, 20);
			entity.setMetadata("spawnedEntity", new FixedMetadataValue(plugin, 1));
			entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&f&lGolem de " + player.getName() +  " (30)"));
			Timer deleteTimer = new Timer(plugin,
		    			10,
	    		        () ->  {
	    		        },
	    		        () -> {    
	    		        	if(!entity.isValid()) {
	    		        		return;
	    		        	}
	    		        	((Damageable) entity).damage(((Damageable) entity).getHealth());
	    		        },
	    		        (t) -> {
	    		        	if(!entity.isValid()) {
	    		        		return;
	    		        	}
	    		        	entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&f&lGolem de " + player.getName() +  " (" + t.getSecondsLeft() + ")"));
	    		        }

	    		);
			deleteTimer.scheduleTimer();
		}
	}
}
