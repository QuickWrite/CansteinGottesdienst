package net.quickwrite.cansteingottesdienst.commands;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import net.quickwrite.cansteingottesdienst.map.DisplayMapRenderer;
import net.quickwrite.cansteingottesdienst.map.MapInformation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.util.ArrayList;
import java.util.List;

public class TrackerMapCommand implements CommandExecutor {
    public static List<Integer> ids = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("canstein.trackermap.get")) {
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou don't have permission to use this command!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cYou have to be a player to use this command");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1 || !args[0].equals("get")) {
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cPlease use §6/" + command.getName() + " get <player|all>");
            return true;
        }

        ItemStack map = new ItemBuilder(Material.FILLED_MAP).setDisplayName("§6Karte der Gegenstände").build();

        MapMeta meta = (MapMeta) map.getItemMeta();
        MapView view = Bukkit.getServer().createMap(Bukkit.getWorlds().get(0));
        view.getRenderers().clear();
        view.setScale(MapView.Scale.FAR);
        view.addRenderer(DisplayMapRenderer.INSTANCE);
        view.setUnlimitedTracking(false);
        view.setTrackingPosition(false);

        meta.setMapView(view);
        map.setItemMeta(meta);

        if (args.length < 2) {
            if (args[1].equalsIgnoreCase("reset")){
                MapInformation.INSTANCE.reset();
                return true;
            }
            this.setMapItem(player, map);
            return true;
        }

        if (args[1].equals("all")) {
            for (Player p : sender.getServer().getOnlinePlayers()) {
                this.setMapItem(p, map);
            }

            return true;
        }

        Player p = Bukkit.getPlayer(args[1]);
        if (p == null) {
            sender.sendMessage(CansteinGottesdienst.PREFIX + "§cThe player " + args[1] + " isn't currently online." );
            return true;
        }

        this.setMapItem(p, map);

        return true;
    }

    private void setMapItem(Player player, ItemStack itemStack) {
        player.getInventory().setItem(EquipmentSlot.OFF_HAND, itemStack);
    }
}
