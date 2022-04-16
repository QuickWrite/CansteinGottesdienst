package net.quickwrite.cansteingottesdienst.commands;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.map.MapInformation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MapInformationCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("canstein.map.setup")){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou don't have permission to use this command");
            return true;
        }
        MapInformation.INSTANCE.setup(p.getLocation());
        p.sendMessage(CansteinGottesdienst.PREFIX + "§aSet Information Getter Location");

        return true;
    }
}
