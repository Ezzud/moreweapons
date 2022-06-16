package fr.ezzud.moreweapons.weapons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import fr.ezzud.moreweapons.MoreWeapons;
import fr.ezzud.moreweapons.others.MachineGunShoot;
import fr.ezzud.moreweapons.utils.InventoryUtil;


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
		MachineGunShoot.run(player);
	}
}
