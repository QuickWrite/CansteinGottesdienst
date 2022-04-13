package net.quickwrite.cansteingottesdienst.commands;

import net.quickwrite.cansteingottesdienst.map.DisplayMapRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            ItemStack map = new ItemStack(Material.FILLED_MAP);
            MapMeta meta = (MapMeta) map.getItemMeta();
            MapView view = Bukkit.createMap(p.getWorld());
            view.getRenderers().clear();
            view.setScale(MapView.Scale.FARTHEST);
            view.setTrackingPosition(false);
            view.setUnlimitedTracking(false);
            view.addRenderer(DisplayMapRenderer.INSTANCE);
            meta.setMapView(view);
            map.setItemMeta(meta);
            p.getInventory().addItem(map);
        }

        return true;
    }
}
