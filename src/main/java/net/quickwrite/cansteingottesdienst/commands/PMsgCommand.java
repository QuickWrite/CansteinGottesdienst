package net.quickwrite.cansteingottesdienst.commands;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PMsgCommand implements CommandExecutor {
    private final Map<Player, String> playerMap = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou have to be a player to use this command!");
            return true;
        }

        if (!sender.hasPermission("canstein.pmsg"))
            return true;

        if (args.length == 0) {
            String remove = null;

            if((remove = playerMap.remove((Player)sender)) != null) {
                sender.sendMessage(CansteinGottesdienst.PREFIX + "§aSuccessfully removed " + remove + "§r!");
                return true;
            }

            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou are not in the list.");

            return true;
        }

        Player player = (Player) sender;

        if (playerMap.get(player) == null) {
            playerMap.put(player, format(String.join(" ", args)));

            sender.sendMessage(CansteinGottesdienst.PREFIX + "§aSuccessfully added " +
                    format(String.join(" ", args)) + "§r.");
            return true;
        }

        for (Player serverPlayer : sender.getServer().getOnlinePlayers()) {
            serverPlayer.sendMessage("[" + playerMap.get(player) + "§r]: " +
                    format(String.join(" ", args)));
        }

        return true;
    }

    private String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
