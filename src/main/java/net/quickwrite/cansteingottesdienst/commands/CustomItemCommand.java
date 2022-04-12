package net.quickwrite.cansteingottesdienst.commands;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.items.Items;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomItemCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou have to be a player to use this command!");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("canstein.items.get")){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou don't have permission to use this command!");
            return true;
        }
        if(args.length != 1){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cPlease use §6/" + command.getName() + " <itemName>");
            return true;
        }
        for(Items i : Items.values()){
            if(i.matches(args[0])){
                p.getInventory().addItem(i.getItemStack());
                return true;
            }
        }

        sender.sendMessage(CansteinGottesdienst.PREFIX + "§cUnknown item with name §6" + args[0]);
        return true;
    }
}
