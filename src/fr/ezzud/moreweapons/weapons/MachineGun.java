package fr.ezzud.moreweapons.weapons;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import fr.ezzud.moreweapons.MoreWeapons;
import fr.ezzud.moreweapons.others.InventoryUtil;


public class MachineGun {
	static MoreWeapons plugin = MoreWeapons.getInstance();
	private Player player;
	public MachineGun(Player player) {
		this.player = player;
	}
	
	public void give() {
		ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(1232402);
		meta.setDisplayName("Machine Gun");
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
		Snowball bullet = player.launchProjectile(Snowball.class, playerDirection);
		player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1); 
		bullet.setVelocity(bullet.getVelocity().multiply(3));
		bullet.setMetadata("bullet", new FixedMetadataValue(plugin, 1));
	}
}
