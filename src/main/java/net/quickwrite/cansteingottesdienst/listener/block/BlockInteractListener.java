package net.quickwrite.cansteingottesdienst.listener.block;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.blocks.CustomBlock;
import net.quickwrite.cansteingottesdienst.util.WorlGuardUtil;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class BlockInteractListener implements Listener {

    @EventHandler
    public void onBlockPlace(PlayerInteractEvent event){
        if(event.getHand() != EquipmentSlot.HAND) return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(event.getClickedBlock() == null) return;

        ApplicableRegionSet set = WorlGuardUtil.getRegionSet(event.getClickedBlock());
        if(!set.testState(WorlGuardUtil.getBukkitPlayer(event.getPlayer()), Flags.CUSTOM_BLOCKS)){
            return;
        }


        CustomBlock block = CansteinGottesdienst.BLOCKS.getBlock(event.getItem());
        if(block == null){
            return;
        }
        block.onBlockPlace(event.getClickedBlock().getLocation().add(event.getBlockFace().getModX(),
                event.getBlockFace().getModY(), event.getBlockFace().getModZ()));
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractEntity(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)) return;
        if(!(event.getEntity() instanceof ArmorStand)) return;
        ArmorStand stand = (ArmorStand) event.getEntity();
        if(stand.getPersistentDataContainer().getOrDefault(CustomBlock.BLOCK_KEY, PersistentDataType.INTEGER, 0) == 1){
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event){
        CustomBlock block = CansteinGottesdienst.BLOCKS.getBlock(event.getBlock().getType());
        if(block == null){
            return;
        }

        if(block.onBlockBreak(event.getPlayer(), event.getBlock().getLocation())){
            block.dropItem(event.getBlock().getLocation().add(0.5,0.5,0.5));
            event.setCancelled(true);
        }
    }

}
