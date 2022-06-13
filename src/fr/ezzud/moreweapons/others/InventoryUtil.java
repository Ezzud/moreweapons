package fr.ezzud.moreweapons.others;

import org.bukkit.entity.Player;

public class InventoryUtil {
		
	public static Boolean isInventoryFull(Player player) {
		if(player.getInventory().firstEmpty() == -1) {
			return true;
		} else {
			return false;
		}
		
	}
	
}
