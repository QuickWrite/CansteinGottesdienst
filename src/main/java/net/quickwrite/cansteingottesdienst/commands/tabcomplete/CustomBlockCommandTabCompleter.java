package net.quickwrite.cansteingottesdienst.commands.tabcomplete;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomBlockCommandTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender.hasPermission("canstein.remove.customblock")
                || sender.hasPermission("canstein.get.customblock")))
            return null;

        final List<String> completions = new ArrayList<>();

        if (args.length <= 1) {
            if (sender.hasPermission("canstein.add.customblock")) {
                completions.add("get");
            }

            if (sender.hasPermission("canstein.remove.customblock")) {
                completions.add("remove");
            }

            return completions;
        }

        if (args.length == 2) {
            StringUtil.copyPartialMatches(args[1], CansteinGottesdienst.BLOCKS.getIdentifiers(), completions);

            Collections.sort(completions);

            if (args[0].equals("remove") && sender.hasPermission("canstein.remove.customblock")) {
                completions.add("all");
            }

            return completions;
        }

        return null;
    }
}
