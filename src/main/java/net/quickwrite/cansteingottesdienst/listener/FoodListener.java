package net.quickwrite.cansteingottesdienst.listener;

import net.quickwrite.cansteingottesdienst.items.Items;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class FoodListener implements Listener {
    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        if (!(event.getItem().equals(Items.WINE.getItemStack()))) {
            return;
        }

        if (!event.getItem().hasItemMeta()) {
            return;
        }

        event.setItem(Items.EMPTY_WINE_BOTTLE.getItemStack());
    }
}
