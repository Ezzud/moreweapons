package fr.ezzud.moreweapons.others;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import fr.ezzud.moreweapons.MoreWeapons;

public class MachineGunShoot {
	
	static MoreWeapons plugin = MoreWeapons.getInstance();
	
	public static void run(Player player) {
		Vector playerDirection = player.getLocation().getDirection();
		Snowball bullet = player.launchProjectile(Snowball.class, playerDirection);
		player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1); 
		bullet.setVelocity(bullet.getVelocity().multiply(3));
		bullet.setMetadata("bullet", new FixedMetadataValue(plugin, 1));
	}
}
