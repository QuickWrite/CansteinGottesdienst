package net.quickwrite.cansteingottesdienst.listener.block;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.blocks.CustomBlock;
import net.quickwrite.cansteingottesdienst.blocks.EmtpyGrapesBlock;
import net.quickwrite.cansteingottesdienst.blocks.IHarvestable;
import net.quickwrite.cansteingottesdienst.util.worldguard.WorlGuardUtil;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class BlockInteractListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
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
            if(CansteinGottesdienst.BLOCKS.getFromDrop(event.getItem()) != null) event.setCancelled(true);
            return;
        }

        if(!event.getPlayer().hasPermission("canstein.customblocks.place." + block.getIdentifier())) return;

        Location place = event.getClickedBlock().getLocation().add(event.getBlockFace().getModX(), event.getBlockFace().getModY(), event.getBlockFace().getModZ());

        if(!place.clone().add(0, -1, 0).getBlock().getType().isSolid() || CustomBlock.isCustomBlock(place)){
            event.setCancelled(true);
            return;
        }

        if(block.onBlockPlace(place)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityInteractEntity(PlayerInteractAtEntityEvent event){
        if(!(event.getRightClicked() instanceof ArmorStand)) return;

        ArmorStand stand = (ArmorStand) event.getRightClicked();

        ApplicableRegionSet set = WorlGuardUtil.getRegionSet(event.getRightClicked().getLocation().getBlock());
        if(!set.testState(WorlGuardUtil.getBukkitPlayer(event.getPlayer()), Flags.CUSTOM_BLOCKS)){
            return;
        }

        String id = stand.getPersistentDataContainer().getOrDefault(CustomBlock.CUSTOM_BLOCK_TYPE_KEY, PersistentDataType.STRING, "");
        CustomBlock b = CansteinGottesdienst.BLOCKS.getBlock(id);
        if(b == null) return;
        if(b instanceof IHarvestable){
            ((IHarvestable) b).convert(stand);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)) return;
        if(!(event.getEntity() instanceof ArmorStand)) return;

        ApplicableRegionSet set = WorlGuardUtil.getRegionSet(event.getEntity().getLocation().getBlock());
        if(!set.testState(WorlGuardUtil.getBukkitPlayer((Player) event.getDamager()), Flags.CUSTOM_BLOCKS)){
            return;
        }

        ArmorStand stand = (ArmorStand) event.getEntity();
        if(stand.getPersistentDataContainer().getOrDefault(CustomBlock.BLOCK_KEY, PersistentDataType.INTEGER, 0) != 1){
            return;
        }
        String id = stand.getPersistentDataContainer().getOrDefault(CustomBlock.CUSTOM_BLOCK_TYPE_KEY, PersistentDataType.STRING, "");
        CustomBlock b = CansteinGottesdienst.BLOCKS.getBlock(id);
        if(b == null) return;

        if(b instanceof EmtpyGrapesBlock) {
            event.setCancelled(true);
            return;
        }

        if(b instanceof IHarvestable){
            ((IHarvestable) b).convert(stand);
        } else {
            stand.remove();
            b.dropItem(CustomBlock.normalizeLocation(event.getEntity().getLocation()).add(0, 0.5, 0));
        }
        event.setCancelled(true);
    }

}
