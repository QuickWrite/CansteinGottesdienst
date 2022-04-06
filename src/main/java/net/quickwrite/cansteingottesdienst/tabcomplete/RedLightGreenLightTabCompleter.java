package net.quickwrite.cansteingottesdienst.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RedLightGreenLightTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(args.length <= 1) {
            final List<String> completions = new ArrayList<>();
            //copy matches of first argument from list (ex: if first arg is 'p' will return just 'pause')
            StringUtil.copyPartialMatches(args[0], Arrays.asList("start", "stop", "setup", "help"), completions);

            //sort the list
            Collections.sort(completions);
            return completions;
        }
        else return null;
    }
}
