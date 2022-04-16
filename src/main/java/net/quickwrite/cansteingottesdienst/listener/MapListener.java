package net.quickwrite.cansteingottesdienst.listener;

import net.quickwrite.cansteingottesdienst.map.MapInformation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class MapListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        MapInformation.INSTANCE.startTracker(event.getItemDrop());
    }
}
