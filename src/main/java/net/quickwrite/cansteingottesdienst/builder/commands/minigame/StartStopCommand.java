package net.quickwrite.cansteingottesdienst.builder.commands.minigame;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.builder.minigame.RaceGame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartStopCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(sender.hasPermission("canstein.race")){
                if(args.length == 1){
                    RaceGame game = CansteinGottesdienst.getInstance().getRaceGame();
                    if(args[0].equalsIgnoreCase("start")){
                        if(game != null){
                            p.sendMessage(CansteinGottesdienst.PREFIX + "§cThere is a game running");
                        }else {
                            CansteinGottesdienst.getInstance().initGame(p);
                        }
                    }else if (args[0].equalsIgnoreCase("stop")){
                        if(game == null){
                            p.sendMessage(CansteinGottesdienst.PREFIX + "§cThere is no game to stop");
                        }else {
                            CansteinGottesdienst.getInstance().getRaceGame().stop();
                        }
                    }else{
                        p.sendMessage(CansteinGottesdienst.PREFIX + "§cUse §6/race start/stop");
                    }
                }else{
                    p.sendMessage(CansteinGottesdienst.PREFIX + "§cUse §6/race start/stop");
                }
            }else{
                p.sendMessage(CansteinGottesdienst.PREFIX + "§cYou don't have permission to use this command");
            }

        }else{
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou can only use this command as a player");
        }
        return false;
    }
}
