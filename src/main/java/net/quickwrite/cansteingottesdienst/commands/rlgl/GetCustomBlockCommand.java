package net.quickwrite.cansteingottesdienst.commands.rlgl;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.blocks.CustomBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetCustomBlockCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("canstein.customblock")){
            p.sendMessage(CansteinGottesdienst.PREFIX + "§cYou don't have permission to use this command");
            return true;
        }
        if(args.length != 1){
            p.sendMessage(CansteinGottesdienst.PREFIX + "§cPlease use §6/" + command.getName() + " <id>");
            return true;
        }
        CustomBlock c = CansteinGottesdienst.BLOCKS.getBlock(args[0]);
        if (c == null){
            p.sendMessage(CansteinGottesdienst.PREFIX + "§cUnknown Identifier");
            return true;
        }
        p.getInventory().addItem(c.getInvItem());

        return true;
    }
}
