package fr.ezzud.moreweapons.weapons;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.ezzud.moreweapons.MoreWeapons;
import fr.ezzud.moreweapons.others.InventoryUtil;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;


public class Trident {
	static MoreWeapons plugin = MoreWeapons.getInstance();
	private Player player;
	public Trident(Player player) {
		this.player = player;
	}
	
	public void give() {
		ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(1232405);
		meta.setDisplayName("Poseidon's Trident");
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
		
		if(InventoryUtil.isInventoryFull(this.player)) {
			this.player.getWorld().dropItem(player.getLocation().add(0, 1, 0), item);
		} else {
			this.player.getInventory().addItem(item);
		}
	}
	
public static void action(Player player) {
	
	if(player.getPlayer().getInventory().getItemInMainHand() != null) {
		
	
	
		World world = player.getWorld();
		int points = 20;
		for (int i = 0; i < 360; i += 360/points) {
			double angle = (i * Math.PI / 180);
			double x = 3 * Math.cos(angle);
			double z = 3 * Math.sin(angle);
			Location loc = player.getLocation().add(x, 2, z);
			Block block = world.getBlockAt(loc);
			if(block.getType().equals(Material.AIR)) {
				block.setType(Material.WATER);
			}
		  
		}
		if(world.getBlockAt(player.getLocation()).getType().equals(Material.AIR)) {
			world.getBlockAt(player.getLocation()).setType(Material.WATER);
		}
		player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_GUARDIAN_ATTACK, 1, 1); 
	}
}

@SuppressWarnings("deprecation")
public static void secondAction(Player player) {
	World world = player.getWorld();
	Vector playerDirection = player.getLocation().getDirection();
	Snowball bullet = player.launchProjectile(Snowball.class, playerDirection);
	player.getLocation().getWorld().playSound(player.getLocation(), Sound.AMBIENT_UNDERWATER_ENTER, 1, 1); 
	Location originalLocation = player.getLocation();
	bullet.setVelocity(bullet.getVelocity().multiply(2));
	bullet.setGravity(false);
	bullet.setMetadata("tridentBullet", new FixedMetadataValue(plugin, 1));
	for(Player p : Bukkit.getServer().getOnlinePlayers()) {
	    PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(bullet.getEntityId());
	    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	BukkitRunnable runnable = new BukkitRunnable(){
        public void run(){
            if(!bullet.isValid() ) {
				 return;
            }
            if(bullet.hasMetadata("bulletTouched")) {
   				 return;
   			}
            Chunk chunk = bullet.getLocation().getChunk();
            if(!chunk.isLoaded()) {
            	bullet.remove();
            	return;
            } else {
            	if(originalLocation.distance(bullet.getLocation()) > 100) {
            		bullet.remove();
            		return;
            	}
            	world.spawnParticle(Particle.DRIP_WATER, bullet.getLocation(), 5);
            }
            
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
    };
    Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, runnable, 0, 1);
}



}
