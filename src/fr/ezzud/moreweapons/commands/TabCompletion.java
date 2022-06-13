package fr.ezzud.moreweapons.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabCompletion implements TabCompleter{
    @Override
    public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("weapon") && args.length < 2){
            if(sender instanceof Player){
                Player player = (Player) sender;

                List<String> list = new ArrayList<>();
                if(player.isOp()) {
                    list.add("all");
                    list.add("staff");
                    list.add("machinegun");
                    list.add("fishlauncher");
                    list.add("laser");
                    list.add("trident");
                }
                return list;
            }
            
        }
        return null;
    }
}