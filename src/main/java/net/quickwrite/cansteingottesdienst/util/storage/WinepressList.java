package net.quickwrite.cansteingottesdienst.util.storage;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.items.Items;
import net.quickwrite.cansteingottesdienst.util.WorlGuardUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class WinepressList {
    public static Map<ProtectedRegion, WinepressInfo> WINEPRESSES = new HashMap<>();

    public static WinepressInfo getInfo(ProtectedRegion region, World world) {
        WinepressInfo info = WINEPRESSES.get(region);

        if (info == null) {
            info = new WinepressInfo(region, region.getFlag(Flags.WINE_PRESS), world);
            WINEPRESSES.put(region, info);
        }

        return info;
    }

    public static class WinepressInfo {
        private final int max;
        private int current;
        private boolean traversed = false;

        private int jumps = 0;

        private final ProtectedRegion region;
        private final World world;

        public WinepressInfo(final ProtectedRegion region, final int max, final World world) {
            this.region = region;
            this.max = max;

            this.world = world;

            this.current = 0;
        }

        public void bump(int count) {
            this.current += count;

            if (!(this.current > this.max)) {
                return;
            }

            if (!this.traversed) {
                traverseAllBlocks(Material.PURPLE_CARPET);
                this.traversed = true;
            }
        }

        public void bumpJumps() {
            jumps++;

            if (jumps > current * 2) {
                final int currentSave = current;

                Bukkit.getScheduler().runTaskLater(CansteinGottesdienst.getInstance(), () -> {
                    Location location = WorlGuardUtil.getCenter(region, world);

                    for (int i = 0; i < currentSave; i++) {
                        world.dropItemNaturally(location, Items.WINE.getItemStack());
                    }
                }, 1);

                reset();

                jumps = 0;
            }
        }

        public int reset() {
            int c = current;
            current = 0;

            this.traversed = false;

            traverseAllBlocks(Material.AIR);

            return c;
        }

        private void traverseAllBlocks(Material material) {
            Bukkit.getScheduler().runTaskLater(CansteinGottesdienst.getInstance(), () -> {
                for (int x = region.getMinimumPoint().getX(); x <= region.getMaximumPoint().getX(); x++) {
                    for (int y = region.getMinimumPoint().getY(); y <= region.getMaximumPoint().getY(); y++) {
                        for (int z = region.getMinimumPoint().getZ(); z <= region.getMaximumPoint().getZ(); z++) {
                            if (!region.contains(x, y, z)) {
                                continue;
                            }

                            if(!world.getBlockAt(x, y - 2, z).getType().equals(Material.GREEN_GLAZED_TERRACOTTA)) {
                                continue;
                            }
                            world.getBlockAt(x, y, z).setType(material);
                        }
                    }
                }
            }, 1);
        }
    }
}
