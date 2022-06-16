package fr.ezzud.moreweapons.others;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import fr.ezzud.moreweapons.MoreWeapons;
import fr.ezzud.moreweapons.utils.RandomUtil;
import fr.ezzud.moreweapons.utils.Timer;
import net.minecraft.server.v1_16_R3.World;

public class SummonerEntity {
	
	public static void summonGolems(int quantity, Player player, MoreWeapons plugin) {
		for(int i = 0; i < quantity; i++) {
			int radius = RandomUtil.getRandomNumberInRange(0,3);
			Block block = RandomUtil.getRandomBlockAround(player, radius);
			Location blockLocation = new Location(player.getWorld(), block.getX(), block.getY(), block.getZ());
			blockLocation.setY(blockLocation.getBlockY() + 2);
			Entity entity = player.getWorld().spawnEntity(blockLocation, EntityType.IRON_GOLEM);
			player.getWorld().spawnParticle(Particle.PORTAL, blockLocation, 20);
			entity.setMetadata("spawnedEntity", new FixedMetadataValue(plugin, 1));
			entity.setCustomName(ChatColor.translateAlternateColorCodes('&', "&f&lGolem de " + player.getName() +  " (10)"));
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

	public static void summonZombies(int quantity, Player player, MoreWeapons plugin) {
		for(int i = 0; i < quantity; i++) {
			int radius = RandomUtil.getRandomNumberInRange(0,3);
			Block block = RandomUtil.getRandomBlockAround(player, radius);
			Location blockLocation = new Location(player.getWorld(), block.getX(), block.getY(), block.getZ());
			blockLocation.setY(blockLocation.getBlockY() + 2);
			World world = (((CraftWorld) player.getWorld()).getHandle());
	        PetZombie entity = new PetZombie(world, blockLocation);
			player.getWorld().spawnParticle(Particle.PORTAL, blockLocation, 20);
			entity.setDisplayName("&5Zombie de " + player.getName() +  " (15)");
			entity.setOwner(player);
			Timer deleteTimer = new Timer(plugin,
		    			15,
	    		        () ->  {
	    		        	
	    		        },
	    		        () -> {    
	    		        	if(!entity.isAlive()) {
	    		        		return;
	    		        	}
	    		        	entity.killEntity();
	    		        },
	    		        (t) -> {
	    		        	if(!entity.isAlive()) {
	    		        		return;
	    		        	}
	    		        	entity.setDisplayName("&5Zombie de " + player.getName() + " (" + t.getSecondsLeft() + ")");
	    		        }

	    		);
			deleteTimer.scheduleTimer();
		}
		
		
	}
}
