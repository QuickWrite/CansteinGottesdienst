package net.quickwrite.cansteingottesdienst.commands.tabcomplete;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrackerMapCommandTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("canstein.trackermap.get")) {
            return null;
        }

        final List<String> completions = new ArrayList<>();

        if (args.length <= 1) {
            completions.add("get");

            return completions;
        }

        if (args.length == 2) {
            StringUtil.copyPartialMatches(args[1],
                    Bukkit.getOnlinePlayers().stream()
                    .map(HumanEntity::getName)
                    .collect(Collectors.toList()),
                    completions);

            if ("all".startsWith(args[1])) {
                completions.add("all");
            }
        }

        return completions;
    }
}
