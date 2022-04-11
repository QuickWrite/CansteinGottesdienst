package net.quickwrite.cansteingottesdienst.listener;

import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class FoodListener implements Listener {
    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        if (!(event.getItem().getType() == Material.MILK_BUCKET)) {
            return;
        }

        if (!event.getItem().hasItemMeta()) {
            return;
        }

        event.setItem(new ItemBuilder(Material.BUCKET).setItemMeta(event.getItem().getItemMeta()).build());
    }
}
