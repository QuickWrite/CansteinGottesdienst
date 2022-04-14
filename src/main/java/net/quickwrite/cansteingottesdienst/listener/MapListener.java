package net.quickwrite.cansteingottesdienst.listener;

import net.quickwrite.cansteingottesdienst.map.DisplayMapRenderer;
import net.quickwrite.cansteingottesdienst.map.MapInformation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;

public class MapListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        MapInformation.INSTANCE.startTracker(event.getItemDrop());
    }

    @EventHandler
    public void onMapInitialize(MapInitializeEvent event){
        MapView view = event.getMap();
        view.getRenderers().clear();
        view.setScale(MapView.Scale.FAR);
        view.addRenderer(DisplayMapRenderer.INSTANCE);
        view.setUnlimitedTracking(false);
        view.setTrackingPosition(false);
    }
}
