package fr.ezzud.moreweapons.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.ezzud.moreweapons.MoreWeapons;
import fr.ezzud.moreweapons.weapons.FishLauncher;
import fr.ezzud.moreweapons.weapons.Laser;
import fr.ezzud.moreweapons.weapons.MachineGun;
import fr.ezzud.moreweapons.weapons.SummonerStaff;
import fr.ezzud.moreweapons.weapons.Trident;

public class CommandHandler implements CommandExecutor {
    static MoreWeapons plugin = MoreWeapons.getInstance();
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.equals(Bukkit.getConsoleSender())) return false;
		Player player = Bukkit.getPlayer(sender.getName());
		if(!player.isOp()) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must be op to execute this command"));	
			return true;
		}
		if(args.length <= 0) {
			sender.sendMessage("Usage: /weapon");
	   		return true;
	   	}
		
	   	switch(args[0]) {
	   		case "all":
	   			new SummonerStaff(player).give();
	   			new FishLauncher(player).give();
	   			new MachineGun(player).give();
	   			new Laser(player).give();
	   			new Trident(player).give();
	   			break;
	   		case "staff":
	   			new SummonerStaff(player).give();
	   			break;
	   		case "machinegun":
	   			new MachineGun(player).give();
	   			break;
	   		case "fishlauncher":
	   			new FishLauncher(player).give();
	   			break;
	   		case "laser":
	   			new Laser(player).give();
	   			break;
	   		case "trident":
	   			new Trident(player).give();
	   			break;
	   		default:
	   			break;
	   	}
	   	
	   	return true;
	
	}
}