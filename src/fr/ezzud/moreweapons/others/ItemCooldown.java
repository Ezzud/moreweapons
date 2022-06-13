package fr.ezzud.moreweapons.others;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;

import fr.ezzud.moreweapons.MoreWeapons;

public class ItemCooldown {
	static MoreWeapons plugin = MoreWeapons.getInstance();
	public static void cooldownItem(int cooldown, ItemStack item, Player player, Runnable action) {
		ItemMeta meta = item.getItemMeta();
		meta.setUnbreakable(false);
		item.setItemMeta(meta);
		Timer cooldownTimer = new Timer(plugin,
    			15,
		        action,
		        () -> { 
		        	((Damageable)meta).setDamage(0);
		        	meta.setUnbreakable(true);
		        	item.setItemMeta(meta);
		        },
		        (t) -> {
		        	((Damageable)meta).setDamage((item.getType().getMaxDurability() / 15) * (t.getSecondsLeft()));
		        	item.setItemMeta(meta);
		        	
		        }

		);
		cooldownTimer.scheduleTimer();
	}
}
