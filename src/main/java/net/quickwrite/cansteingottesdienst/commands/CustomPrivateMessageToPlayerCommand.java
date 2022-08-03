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

public class CustomPrivateMessageToPlayerCommand implements CommandExecutor {
    private final String messageTemplate;

    public CustomPrivateMessageToPlayerCommand() {
        FileConfiguration config = CansteinGottesdienst.getInstance().getDefaultConfig().getConfig();
        this.messageTemplate = config.getString("pmsg.messageTemplatePrivateMessage", "[%player%§r] -> ");
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
            String remove = null;

            if((remove = CustomMessageGlobalCommand.playerMap.remove((Player)sender)) != null) {
                sender.sendMessage(CansteinGottesdienst.PREFIX + "§aSuccessfully removed " + remove + "§r!");
                return true;
            }

            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou are not in the list.");

            return true;
        }

        Player player = (Player) sender;

        if(CustomMessageGlobalCommand.playerMap.get(player) == null){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cNo custom name defined. Please define one using §6/pmsglobal <name>");
            return true;
        }

        if(args.length < 2){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cNo Message specified. Use §6/pmsg <player> <Message>");
            return true;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cUnknown Player with name: §6" + args[0]);
            return true;
        }

        p.sendMessage(Placeholder.replace(messageTemplate, "player", CustomMessageGlobalCommand.playerMap.get(player))
                + format(String.join(" ", Arrays.copyOfRange(args,  1, args.length))));

        return true;
    }

    private String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
