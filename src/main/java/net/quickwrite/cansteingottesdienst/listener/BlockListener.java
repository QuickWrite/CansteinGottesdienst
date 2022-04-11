package net.quickwrite.cansteingottesdienst.listener;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.blocks.CustomBlock;
import net.quickwrite.cansteingottesdienst.blocks.EmtpyGrapesBlock;
import net.quickwrite.cansteingottesdienst.blocks.GrapesBlock;
import net.quickwrite.cansteingottesdienst.blocks.IHarvestable;
import net.quickwrite.cansteingottesdienst.commands.CustomBlockCommand;
import net.quickwrite.cansteingottesdienst.util.CropInfo;
import net.quickwrite.cansteingottesdienst.util.Random;
import net.quickwrite.cansteingottesdienst.util.WorlGuardUtil;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class BlockListener implements Listener {
    private static final RegionQuery query;

    static {
        query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
    }


    // Called when a block is broken.

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();

        final ApplicableRegionSet regionSet = WorlGuardUtil.getRegionSet(event.getBlock());

        if (!regionSet.testState(WorlGuardUtil.getBukkitPlayer(player), Flags.INFINITE_CROPS))
            return;

        CropInfo.CropData cropData = CropInfo.getData(event.getBlock().getType());

        if (cropData == null)
            return;

        onNormalBlockBreak(event, cropData);
    }

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof ArmorStand)) return;

        final ApplicableRegionSet regionSet = WorlGuardUtil.getRegionSet(event.getEntity().getLocation().getBlock());

        if (!regionSet.testState(WorlGuardUtil.getBukkitPlayer((Player) event.getDamager()), Flags.INFINITE_CROPS))
            return;

        ArmorStand stand = (ArmorStand) event.getEntity();

        CustomBlock cb = CansteinGottesdienst.BLOCKS.getBlock(stand.getPersistentDataContainer().getOrDefault(
                CustomBlock.CUSTOM_BLOCK_TYPE_KEY,
                PersistentDataType.STRING,
                ""
        ));
        if(cb != null) {
            if(cb instanceof EmtpyGrapesBlock) return;
            if(cb instanceof IHarvestable) onCustomBlockHarvest(event.getEntity().getLocation(), cb);
            else onCustomBlockBreak(event.getEntity().getLocation(), cb);
        }
    }

    @EventHandler
    public void onEntityInteractEntity(PlayerInteractAtEntityEvent event){
        if(!(event.getRightClicked() instanceof ArmorStand)) return;

        final ApplicableRegionSet regionSet = WorlGuardUtil.getRegionSet(event.getRightClicked().getLocation().getBlock());

        if (!regionSet.testState(WorlGuardUtil.getBukkitPlayer(event.getPlayer()), Flags.INFINITE_CROPS))
            return;

        ArmorStand stand = (ArmorStand) event.getRightClicked();
        String id = stand.getPersistentDataContainer().getOrDefault(CustomBlock.CUSTOM_BLOCK_TYPE_KEY, PersistentDataType.STRING, "");
        CustomBlock b = CansteinGottesdienst.BLOCKS.getBlock(id);
        if(b == null) return;
        if(b instanceof IHarvestable){
            if(!event.getPlayer().hasPermission("canstein.customblocks." + b.getIdentifier())) return;
            onCustomBlockHarvest(event.getRightClicked().getLocation(), b);
        }
    }

    private void onCustomBlockHarvest(Location loc, CustomBlock cb) {
        Bukkit.getScheduler().runTaskLater(CansteinGottesdienst.getInstance(), new Runnable() {
            @Override
            public void run() {
                ArmorStand stand = CustomBlock.getCustomBlockAt(loc);
                if(stand != null) stand.remove();
                cb.getConvertTo().onBlockPlace(loc);
            }
        }, Random.getRandomInt(40, 1000)); //
    }

    private void onCustomBlockBreak(Location loc, CustomBlock cb) {
        Bukkit.getScheduler().runTaskLater(CansteinGottesdienst.getInstance(), new Runnable() {
            @Override
            public void run() {
                cb.getConvertTo().onBlockPlace(loc);
            }
        }, Random.getRandomInt(40, 1000)); // getRandomInt(40, 1000)
    }

    private void onNormalBlockBreak(BlockBreakEvent event, CropInfo.CropData cropData) {
        event.setCancelled(true);

        if (!(event.getBlock().getBlockData() instanceof Ageable)) {
            return;
        }

        Ageable crop = ((Ageable) event.getBlock().getBlockData());

        if (crop.getAge() != crop.getMaximumAge()) {
            return;
        }

        event.getBlock().setType(crop.getMaterial());

        for (ItemStack drop : cropData.getItems()) {
            event.getPlayer().getWorld().dropItem(event.getBlock().getLocation().add(0.5, -0.5, 0.5), drop);
        }

        Bukkit.getScheduler().runTaskLater(CansteinGottesdienst.getInstance(), new Runnable() {
            @Override
            public void run() {
                crop.setAge(crop.getMaximumAge());

                event.getBlock().setBlockData(crop);
            }
        }, Random.getRandomInt(40, 1000)); // getRandomInt(40, 1000)
    }
}
