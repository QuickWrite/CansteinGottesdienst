package net.quickwrite.cansteingottesdienst.util;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.BukkitPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.World;
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

    public static boolean testFlag(Block block, Player player, StateFlag flag) {
        ApplicableRegionSet regionSet = getRegionSet(block);

        return regionSet.testState(getBukkitPlayer(player), flag);
    }

    public static Location getWELocation(final Block block) {
        return new Location(new BukkitWorld(block.getWorld()), block.getX(), block.getY(), block.getZ());
    }

    public static BukkitPlayer getBukkitPlayer(final Player player) {
        return new BukkitPlayer(CansteinGottesdienst.WORLDGUARD_PLUGIN, player);
    }

    /**
     * Code from https://www.spigotmc.org/threads/how-to-get-the-center-of-a-world-guard-region.234272/
     * @param region The region itself
     * @param world The world where the region is hosted
     * @return A location on where the center of the region is.
     */
    public static org.bukkit.Location getCenter(ProtectedRegion region, World world) {
        org.bukkit.Location top = new org.bukkit.Location(world, 0, 0, 0);
        top.setX(region.getMaximumPoint().getX());
        top.setY(region.getMaximumPoint().getY());
        top.setZ(region.getMaximumPoint().getZ());

        //Get bottom location
        org.bukkit.Location bottom = new org.bukkit.Location(world, 0, 0, 0);
        bottom.setX(region.getMinimumPoint().getX());
        bottom.setY(region.getMinimumPoint().getY());
        bottom.setZ(region.getMinimumPoint().getZ());

        //Split difference
        double X =  ((top.getX() - bottom.getX())/2) + bottom.getX();
        double Y =  ((bottom.getY() - top.getY())/2) + bottom.getY();
        double Z =  ((top.getZ() - bottom.getZ())/2) + bottom.getZ();

        //Setup new location
        return new org.bukkit.Location(world, X, Y, Z);
    }
}
