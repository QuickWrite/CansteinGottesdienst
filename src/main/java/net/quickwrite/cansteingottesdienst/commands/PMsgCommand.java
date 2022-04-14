package net.quickwrite.cansteingottesdienst.commands;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PMsgCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cPlease use §6/" + command.getName() + " <name> <message>");
            return true;
        }

        if (!sender.hasPermission("canstein.pmsg"))
            return true;

        for (Player player : sender.getServer().getOnlinePlayers()) {
            player.sendMessage("[" + format(args[0]) + "§r]: " +
                    format(Arrays.stream(args).skip(1).collect(Collectors.joining(" "))));
        }

        return true;
    }

    private String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
