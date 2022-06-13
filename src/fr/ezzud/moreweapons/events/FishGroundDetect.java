package fr.ezzud.moreweapons.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.ezzud.moreweapons.MoreWeapons;

public class FishGroundDetect implements Listener {
	
    MoreWeapons plugin;
    public FishGroundDetect(MoreWeapons instance) {
        plugin = instance;
    }
    

	   @EventHandler
	   public void move(EntityDamageEvent event) {
		  Entity entity = event.getEntity();
			  if(entity.hasMetadata("fishBomb")) {
				  event.setCancelled(true); 
			  }
			  
	   }
}