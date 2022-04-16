package net.quickwrite.cansteingottesdienst.listener;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.items.Items;
import net.quickwrite.cansteingottesdienst.util.Random;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import net.quickwrite.cansteingottesdienst.util.worldguard.WorlGuardUtil;
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
        drops.add(Items.LAMB_GIGOT.getItemStack());

        Bukkit.getScheduler().runTaskLater(CansteinGottesdienst.getInstance(), new Runnable() {
            @Override
            public void run() {
                event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.SHEEP);
            }
        }, Random.getRandomInt(40, 1000));
    }
}
