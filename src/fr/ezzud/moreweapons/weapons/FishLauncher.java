package fr.ezzud.moreweapons.weapons;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import fr.ezzud.moreweapons.MoreWeapons;
import fr.ezzud.moreweapons.utils.FishLauncherUtil;
import fr.ezzud.moreweapons.utils.InventoryUtil;


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
	
	public static void action(Player player) {
		World world = player.getWorld();
		ItemStack item = player.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		if(InventoryUtil.isDamaged(meta)) {
			return;
		}
		FishLauncherUtil.launchSingleFish(item, meta,player);
		world.playSound(player.getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 1);
		
		
	}
	
	public static void secondAction(Player player) {
		World world = player.getWorld();
		ItemStack item = player.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		if(InventoryUtil.isDamaged(meta)) {
			return;
		}
		FishLauncherUtil.launchMultipleFish(item, meta, 5, player);
		world.playSound(player.getLocation(), Sound.UI_STONECUTTER_TAKE_RESULT, 1, 1);
	}
	
}
