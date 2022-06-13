package fr.ezzud.moreweapons.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.ezzud.moreweapons.MoreWeapons;

public class Join implements Listener {
	
    MoreWeapons plugin;
    public Join(MoreWeapons instance) {
        plugin = instance;
    }
    

	   @EventHandler
	   public void joinEvent(PlayerJoinEvent event) {
		   event.getPlayer().setResourcePack(plugin.getConfig().getString("url"));
	   }
}