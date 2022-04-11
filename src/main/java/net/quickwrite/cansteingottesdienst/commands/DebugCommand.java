package net.quickwrite.cansteingottesdienst.commands;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.blocks.CustomBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        for(CustomBlock c : CansteinGottesdienst.BLOCKS.getBlocks().values()){
            sender.sendMessage(c.getIdentifier() + ": " + c.getArmorstands().values());
        }

        return true;
    }
}
