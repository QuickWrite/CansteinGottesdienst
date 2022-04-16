package net.quickwrite.cansteingottesdienst.commands;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.map.DisplayMapRenderer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InitMapCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //DisplayMapRenderer.INSTANCE.loadImages();
        if(!(sender instanceof Player)){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou have to be a player to use this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("canstein.map.init")){
            p.sendMessage(CansteinGottesdienst.PREFIX + "§cYou don't have permission to use this command");
            return true;
        }

        DisplayMapRenderer.INSTANCE.loadImages();
        p.sendMessage(CansteinGottesdienst.PREFIX + "§aSuccessfully reloaded the images");

        return true;
    }
}
