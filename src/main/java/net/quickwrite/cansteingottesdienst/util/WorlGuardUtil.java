package net.quickwrite.cansteingottesdienst.util;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.BukkitPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class WorlGuardUtil {
    private static final RegionQuery query;

    static {
        query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
    }

    private WorlGuardUtil() {

    }

    public static ApplicableRegionSet getRegionSet(Block block) {
        return query.getApplicableRegions(getWELocation(block));
    }

    public static Location getWELocation(final Block block) {
        return new Location(new BukkitWorld(block.getWorld()), block.getX(), block.getY(), block.getZ());
    }

    public static BukkitPlayer getBukkitPlayer(final Player player) {
        return new BukkitPlayer(CansteinGottesdienst.WORLDGUARD_PLUGIN, player);
    }
}
