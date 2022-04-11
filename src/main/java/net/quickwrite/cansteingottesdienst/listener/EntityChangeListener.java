package net.quickwrite.cansteingottesdienst.listener;

import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import org.bukkit.Material;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import net.quickwrite.cansteingottesdienst.util.WorlGuardUtil;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EntityChangeListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(!(event.getEntity() instanceof Sheep))
            return;

        if (!WorlGuardUtil.testFlag(event.getEntity().getLocation().getBlock(), event.getEntity().getKiller(), Flags.SHEEP_TO_LAMB))
            return;

        event.setDroppedExp(0);
        List<ItemStack> drops = event.getDrops();
        drops.clear();
        drops.add(new ItemBuilder(Material.MUTTON).setCustomModelData(1).build());
    }
}
