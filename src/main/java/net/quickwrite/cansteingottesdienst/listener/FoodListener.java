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
        System.out.println("I am here!");

        if (!event.getItem().hasItemMeta()) {
            return;
        }

        System.out.println("It has META!");

        event.setCancelled(true);

        event.getPlayer().getInventory().remove(event.getItem());

        event.getPlayer().getInventory().addItem(new ItemBuilder(Material.BUCKET).setItemMeta(event.getItem().getItemMeta()).build());
    }
}
