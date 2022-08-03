package net.quickwrite.cansteingottesdienst.commands;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.util.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomPrivateMessageToAllPlayersCommand implements CommandExecutor {
    private final String messageTemplate;

    public CustomPrivateMessageToAllPlayersCommand() {
        FileConfiguration config = CansteinGottesdienst.getInstance().getDefaultConfig().getConfig();
        this.messageTemplate = config.getString("pmsg.messageTemplate", "[%player%§r] -> ");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou have to be a player to use this command!");
            return true;
        }

        if (!sender.hasPermission("canstein.pmsg"))
            return true;

        if (args.length == 0) {
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou have to send a message!");
            return true;
        }

        Player player = (Player) sender;

        if(CustomMessageGlobalCommand.playerMap.get(player) == null){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cNo custom name defined. Please define one using §6/pmsglobal <name>");
            return true;
        }

        for (Player serverPlayer : sender.getServer().getOnlinePlayers()) {
            serverPlayer.sendMessage(Placeholder.replace(messageTemplate, "player", CustomMessageGlobalCommand.playerMap.get(player))
                    + format(Placeholder.replace(String.join(" ", args), "player", serverPlayer.getDisplayName())));
        }

        return true;
    }

    private String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
