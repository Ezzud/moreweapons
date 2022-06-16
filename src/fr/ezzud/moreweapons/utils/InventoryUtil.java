package fr.ezzud.moreweapons.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUtil {
		
	public static Boolean isInventoryFull(Player player) {
		if(player.getInventory().firstEmpty() == -1) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static Boolean isDamaged(ItemMeta meta) {
		if(((Damageable) meta).getDamage() != 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
