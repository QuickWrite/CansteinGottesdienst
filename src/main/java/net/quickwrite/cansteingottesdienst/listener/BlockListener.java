package net.quickwrite.cansteingottesdienst.listener;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.util.CropInfo;
import net.quickwrite.cansteingottesdienst.util.WorlGuardUtil;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener {
    private static final RegionQuery query;

    static {
        query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
    }

    /*
     * Called when a block is broken.
     */
    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();

        final ApplicableRegionSet regionSet = WorlGuardUtil.getRegionSet(event.getBlock());

        if(!regionSet.testState(WorlGuardUtil.getBukkitPlayer(player), Flags.INFINITE_CROPS))
            return;

        CropInfo.CropData cropData = CropInfo.getDrops(event.getBlock().getType());

        if(cropData == null)
            return;

        event.setCancelled(true);

        World world = event.getPlayer().getWorld();

        if (!(event.getBlock().getBlockData() instanceof Ageable)) {
            return;
        }

        Ageable crop = ((Ageable) event.getBlock().getBlockData());

        if(crop.getAge() != crop.getMaximumAge()) {
            return;
        }

        event.getBlock().setType(crop.getMaterial());

        for(ItemStack drop : cropData.getItems()) {
            world.dropItem(event.getBlock().getLocation().add(0.5,-0.5,0.5), drop);
        }

        Bukkit.getScheduler().runTaskLater(CansteinGottesdienst.getInstance(), new Runnable() {
            @Override
            public void run() {
                crop.setAge(crop.getMaximumAge());

                event.getBlock().setBlockData(crop);
            }
        }, getRandomInt(40, 1000));
    }

    private int getRandomInt(int min, int max) {
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }
}
