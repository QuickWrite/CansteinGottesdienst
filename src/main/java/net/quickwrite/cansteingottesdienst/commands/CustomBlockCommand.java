package net.quickwrite.cansteingottesdienst.commands;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.blocks.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomBlockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equals("get")) {
            return getCustomBlock(sender, command, args);
        }

        if (args[0].equals("remove")) {
            return removeCustomBlock(sender, command, args);
        }

        return true;
    }

    private boolean getCustomBlock(CommandSender sender, Command command, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("canstein.get.customblock")){
            p.sendMessage(CansteinGottesdienst.PREFIX + "§cYou don't have permission to use this command");
            return true;
        }
        if(args.length != 2){
            p.sendMessage(CansteinGottesdienst.PREFIX + "§cPlease use §6/" + command.getName() + " get <id>");
            return true;
        }

        CustomBlock c = CansteinGottesdienst.BLOCKS.getBlock(args[1]);
        if (c == null){
            p.sendMessage(CansteinGottesdienst.PREFIX + "§cUnknown Identifier");
            return true;
        }
        p.getInventory().addItem(c.getInvItem());

        return true;
    }

    private boolean removeCustomBlock(CommandSender sender, Command command, String[] args) {
        if(args.length != 2){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cPlease use §6/" + command.getName() + " remove <id>");
        }

        if(!sender.hasPermission("canstein.remove.customblock")){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou don't have permission to use this command");
            return true;
        }

        if (args[1].equals("all")) {
            int size = 0;

            for(CustomBlock block : CansteinGottesdienst.BLOCKS.getBlocks()) {
                for(World w : Bukkit.getWorlds()){
                    size += block.removeBlocks(w);
                }
            }

            sender.sendMessage("§cRemoved " + size + " custom blocks.");

            return true;
        }

        CustomBlock customBlock = CansteinGottesdienst.BLOCKS.getBlock(args[1]);
        int size = 0;
        for(World w : Bukkit.getWorlds()){
            size += customBlock.removeBlocks(w);
        }

        sender.sendMessage("§cRemoved " + size + " custom blocks.");

        return true;
    }
}
