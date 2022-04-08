package net.quickwrite.cansteingottesdienst.listener.block;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.blocks.CustomBlock;
import net.quickwrite.cansteingottesdienst.util.WorlGuardUtil;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class BlockInteractListener implements Listener {

    @EventHandler
    public void onBlockPlace(PlayerInteractEvent event){
        if(event.getHand() != EquipmentSlot.HAND) return;
        if(event.getClickedBlock() == null) return;

        ApplicableRegionSet set = WorlGuardUtil.getRegionSet(event.getClickedBlock());
        if(!set.testState(WorlGuardUtil.getBukkitPlayer(event.getPlayer()), Flags.CUSTOM_BLOCKS)){
            return;
        }


        CustomBlock block = CansteinGottesdienst.BLOCKS.getBlock(event.getItem());
        if(block == null){
            return;
        }
        block.onBlockPlace(event.getClickedBlock().getLocation().add(event.getBlockFace().getModX(), event.getBlockFace().getModY(), event.getBlockFace().getModZ()));
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        ApplicableRegionSet set = WorlGuardUtil.getRegionSet(event.getBlock());
        if(!set.testState(WorlGuardUtil.getBukkitPlayer(event.getPlayer()), Flags.CUSTOM_BLOCKS)){
            return;
        }
        CustomBlock block = CansteinGottesdienst.BLOCKS.getBlock(event.getBlock().getType());
        if(block == null){
            return;
        }

        if(block.onBlockBreak(event.getPlayer(), event.getBlock().getLocation())){
            block.dropItem(event.getBlock().getLocation());
            event.setCancelled(true);
        }
    }

}
