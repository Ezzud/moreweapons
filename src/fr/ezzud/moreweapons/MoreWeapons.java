package fr.ezzud.moreweapons;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import fr.ezzud.moreweapons.commands.CommandHandler;
import fr.ezzud.moreweapons.commands.TabCompletion;
import fr.ezzud.moreweapons.events.FishGroundDetect;
import fr.ezzud.moreweapons.events.HitDetection;
import fr.ezzud.moreweapons.events.Interact;
import fr.ezzud.moreweapons.events.Join;
import fr.ezzud.moreweapons.events.StaffLootRemover;

public class MoreWeapons extends JavaPlugin implements Listener {
	  private static MoreWeapons plugin;
	   public static MoreWeapons getInstance() {
		      return plugin;
	   }

	   
	   
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new Join(this), this);
		pm.registerEvents(new Interact(this), this);
		pm.registerEvents(new HitDetection(this), this);
		pm.registerEvents(new FishGroundDetect(this), this);
		pm.registerEvents(new StaffLootRemover(this), this);
		plugin = this;
		
		
		this.getCommand("weapon").setExecutor(new CommandHandler());
		this.getCommand("weapon").setTabCompleter(new TabCompletion());
		Bukkit.getLogger().info("[MoreWeapons] Lancement du plugin effectué");
		
		}
	
	
}
