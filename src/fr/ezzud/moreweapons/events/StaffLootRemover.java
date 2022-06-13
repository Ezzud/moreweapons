package fr.ezzud.moreweapons.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.ezzud.moreweapons.MoreWeapons;

public class StaffLootRemover implements Listener {
	
    MoreWeapons plugin;
    public StaffLootRemover(MoreWeapons instance) {
        plugin = instance;
    }
    

	   @EventHandler
	   public void death(EntityDeathEvent event) {
		   Entity entity = event.getEntity();
		   if(entity.hasMetadata("spawnedEntity")) {
			   event.getDrops().removeAll(event.getDrops());
			  }
	   }
}
