package fr.ezzud.moreweapons.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.ezzud.moreweapons.MoreWeapons;
import fr.ezzud.moreweapons.weapons.FishLauncher;
import fr.ezzud.moreweapons.weapons.Laser;
import fr.ezzud.moreweapons.weapons.MachineGun;
import fr.ezzud.moreweapons.weapons.SummonerStaff;
import fr.ezzud.moreweapons.weapons.Trident;

public class Interact implements Listener {
	
    MoreWeapons plugin;
    public Interact(MoreWeapons instance) {
        plugin = instance;
    }
    

	   @EventHandler
	   public void interact(PlayerInteractEvent event) {
		   Player player = event.getPlayer();
		   
		   if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			  if(event.getItem() == null) {
					 return;
			}
			 ItemStack item = event.getItem();  
			 if(!item.hasItemMeta()) {
				 return;
			 }
			 ItemMeta meta = item.getItemMeta();
			 if(meta.hasCustomModelData()) {
				 int modelData = meta.getCustomModelData();
				 switch(modelData) {
				 	case 1232401:
				 		SummonerStaff.action(player);
				 		break;
				 	case 1232402:
				 		MachineGun.action(player);
				 		break;
				 	case 1232403:
				 		if(player.isSneaking()) {
				 			FishLauncher.secondAction(player);
				 		} else {
				 			FishLauncher.action(player);
				 		}
				 		break;
				 	case 1232404:
				 		Laser.action(player);
				 		break;
				 	case 1232405:
				 		if(player.isSneaking()) {
				 			Trident.secondAction(player);
				 		} else {
				 			Trident.action(player);
				 		}
				 		
				 		break;
				 }
			 }

		   }
	   }
}
