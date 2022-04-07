package net.quickwrite.cansteingottesdienst.listener;

import com.sk89q.worldedit.bukkit.BukkitWorld;;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.BukkitPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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

        final ApplicableRegionSet regionSet = query.getApplicableRegions(getWELocation(event.getBlock()));

        if(!regionSet.testState(getBukkitPlayer(player), CansteinGottesdienst.INFINITE_CROPS))
            return;

        if (!(event.getBlock().getBlockData() instanceof Ageable))
            return;

        event.setCancelled(true);
    }

    private Location getWELocation(final Block block) {
        return new Location(new BukkitWorld(block.getWorld()), block.getX(), block.getY(), block.getZ());
    }

    private BukkitPlayer getBukkitPlayer(final Player player) {
        return new BukkitPlayer(CansteinGottesdienst.WORLDGUARD_PLUGIN, player);
    }
}