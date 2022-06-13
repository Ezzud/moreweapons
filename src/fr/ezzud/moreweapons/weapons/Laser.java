package fr.ezzud.moreweapons.weapons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import fr.ezzud.moreweapons.MoreWeapons;
import fr.ezzud.moreweapons.others.InventoryUtil;


public class Laser {
	static MoreWeapons plugin = MoreWeapons.getInstance();
	private Player player;
	public Laser(Player player) {
		this.player = player;
	}
	
	public void give() {
		ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(1232404);
		meta.setDisplayName("Laser");
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
		
		if(InventoryUtil.isInventoryFull(this.player)) {
			this.player.getWorld().dropItem(player.getLocation().add(0, 1, 0), item);
		} else {
			this.player.getInventory().addItem(item);
		}
	}
	
	public static void action(Player player) {
		Vector playerDirection = player.getLocation().getDirection();
		player.getLocation().getWorld().playSound(player.getLocation(), Sound.UI_LOOM_TAKE_RESULT, 1, 1); 	
		for(int i = 0; i < 5; i++) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
	            @Override
	            public void run(){
	            	Arrow bullet = player.launchProjectile(Arrow.class, playerDirection);
	            	bullet.setMetadata("bouncing", new FixedMetadataValue(plugin, 1));
	            	bullet.setVelocity(bullet.getVelocity().multiply(4)); 
	            }
	        }, i);
			
		}
	}
}
