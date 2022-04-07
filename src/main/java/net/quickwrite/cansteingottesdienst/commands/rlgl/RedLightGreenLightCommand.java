package net.quickwrite.cansteingottesdienst.commands.rlgl;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.rlgl.RedLightGreenLightGame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RedLightGreenLightCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(sender.hasPermission("canstein.race")){
                if(args.length == 1){
                    RedLightGreenLightGame game = CansteinGottesdienst.getInstance().getRaceGame();
                    if(args[0].equalsIgnoreCase("start")){
                        if(game != null){
                            p.sendMessage(CansteinGottesdienst.PREFIX + "§cThere is a game running");
                        }else {
                            if(CansteinGottesdienst.getInstance().initGame(p)){
                                p.sendMessage(CansteinGottesdienst.PREFIX + "§aThe game has been started");
                            }else{
                                p.sendMessage(CansteinGottesdienst.PREFIX + "§cThe game can not be started as it is not set up");
                            }
                        }
                    }else if (args[0].equalsIgnoreCase("stop")){
                        if(game == null){
                            p.sendMessage(CansteinGottesdienst.PREFIX + "§cThere is no game to stop");
                        } else {
                            CansteinGottesdienst.getInstance().stopGame();
                            p.sendMessage(CansteinGottesdienst.PREFIX + "§aThe game has been stopped");
                        }
                    }else if (args[0].equalsIgnoreCase("setup")){
                        if(game != null){
                            p.sendMessage(CansteinGottesdienst.PREFIX + "§cCan't perform game setup as there is a game running");
                        } else {
                            RedLightGreenLightGame.setSettings(p);
                            p.sendMessage(CansteinGottesdienst.PREFIX + "§aThe game has been set up. Use §6/" + command.getName() + " start §ato start it");
                        }
                    }else if (args[0].equalsIgnoreCase("help")){
                        sendHelp(p);
                    }else{
                        p.sendMessage(CansteinGottesdienst.PREFIX + "§cUse §6/" + command.getName() + " <start/stop/setup/help>");
                    }
                }else{
                    sendHelp(p);
                }
            }else{
                p.sendMessage(CansteinGottesdienst.PREFIX + "§cYou don't have permission to use this command");
            }

        }else{
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou can only use this command as a player");
        }
        return false;
    }

    public void sendHelp(Player p){
        String sb = CansteinGottesdienst.PREFIX + "§6Rlgl Help:\n" +
                "§a - start: Start the Game\n" +
                "§a - stop: Stop the Game\n" +
                "§a - setup: Set up the game | go to the finish line and look in the direction the players are looking\n";
        p.sendMessage(sb);
    }
}
