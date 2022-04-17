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
        if(!(sender instanceof Player)){
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou can only use this command as a player");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("canstein.race")){
            p.sendMessage(CansteinGottesdienst.PREFIX + "§cYou don't have permission to use this command");
            return true;
        }
        if(args.length != 1){
            sendHelp(p);
            return true;
        }
        RedLightGreenLightGame game = CansteinGottesdienst.getInstance().getRaceGame();
        if(args[0].equalsIgnoreCase("start")){
            if(game != null){
                p.sendMessage(CansteinGottesdienst.PREFIX + "§cEs ist schon ein Spiel gestartet");
            }else {
                if(CansteinGottesdienst.getInstance().initGame(p)){
                    p.sendMessage(CansteinGottesdienst.PREFIX + "§aDas Spiel wurde gestartet");
                }else{
                    p.sendMessage(CansteinGottesdienst.PREFIX + "§cDas Spiel kann nicht gestartet werden, da ein setup fehlt");
                }
            }
        }else if (args[0].equalsIgnoreCase("stop")){
            if(game == null){
                p.sendMessage(CansteinGottesdienst.PREFIX + "§cEs ist kein Spiel am laufen");
            } else {
                CansteinGottesdienst.getInstance().stopGame();
                p.sendMessage(CansteinGottesdienst.PREFIX + "§aDas Spiel wurde gestoppt");
            }
        }else if (args[0].equalsIgnoreCase("setup")){
            if(game != null){
                p.sendMessage(CansteinGottesdienst.PREFIX + "§cDas Setup kann nicht durchgeführ werden da das spiel gerade läuft");
            } else {
                RedLightGreenLightGame.setSettings(p);
                p.sendMessage(CansteinGottesdienst.PREFIX + "§aDer Setup wurde ausgeführt. Benutze §6/" + command.getName() + " start §aum das spiel zu starten");
            }
        }else if (args[0].equalsIgnoreCase("help")){
            sendHelp(p);
        }else{
            p.sendMessage(CansteinGottesdienst.PREFIX + "§cUse §6/" + command.getName() + " <start/stop/setup/help>");
        }
        return true;
    }

    public void sendHelp(Player p){
        String sb = CansteinGottesdienst.PREFIX + "§6Rlgl Help:\n" +
                "§a - start: Start the Game\n" +
                "§a - stop: Stop the Game\n" +
                "§a - setup: Set up the game | go to the finish line and look in the direction the players are looking\n";
        p.sendMessage(sb);
    }
}
