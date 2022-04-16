package net.quickwrite.cansteingottesdienst.util.storage;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.items.Items;
import net.quickwrite.cansteingottesdienst.util.Placeholder;
import net.quickwrite.cansteingottesdienst.util.worldguard.WorlGuardUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.ArmorStand;

import java.util.HashMap;
import java.util.Map;

public class WinepressList {
    public static Map<ProtectedRegion, WinepressInfo> WINEPRESSES = new HashMap<>();

    public static String grapes_hologram;
    public static String jump_hologram;

    static {
        Configuration config = CansteinGottesdienst.getInstance().getDefaultConfig().getConfig();

        grapes_hologram = config.getString("winepress.grapes_hologram", "%current% / %max% Trauben");
        jump_hologram = config.getString("winepress.jump_hologram", "%jumps% / %needed_jumps% Sprünge");
    }

    public static WinepressInfo getInfo(ProtectedRegion region, World world) {
        WinepressInfo info = WINEPRESSES.get(region);

        if (info == null) {
            info = new WinepressInfo(region, region.getFlag(Flags.WINE_PRESS), world);
            WINEPRESSES.put(region, info);
        }

        return info;
    }

    public static void delete() {
        for (WinepressInfo info : WINEPRESSES.values()) {
            info.delete();
        }

        WINEPRESSES.clear();
    }

    public static class WinepressInfo {
        private int max;
        private int current;
        private int neededJumps = 0;

        private boolean traversed = false;

        private final ArmorStand armorStand;

        private int jumps = 0;

        private final ProtectedRegion region;
        private final World world;

        public WinepressInfo(final ProtectedRegion region, final int max, final World world) {
            this.region = region;
            this.max = max - 1;

            this.world = world;

            this.armorStand = world.spawn(WorlGuardUtil.getCenter(region, world).add(0.5, 1.5, 0.5),
                    ArmorStand.class, entity -> {
                        entity.setCustomNameVisible(true);
                        entity.setVisible(false);
                        entity.setMarker(true);

                        entity.setCustomName(hologramAdd());
                    });

            this.current = 0;
        }

        public void setMax(int max) {
            this.max = max - 1;
        }

        public void bump(int count) {
            this.current += count;

            this.armorStand.setCustomName(hologramAdd());

            if (!(this.current > this.max)) {
                return;
            }

            this.neededJumps = ((this.current - (this.current % 5)) * 2);

            if (!this.traversed) {
                traverseAllBlocks(Material.PURPLE_CARPET);
                this.traversed = true;

                this.armorStand.setCustomName(hologramJump());
            }
        }

        public void bumpJumps() {
            this.jumps++;

            if (this.jumps >= this.neededJumps) {
                int currentSave = this.current / 5;

                Bukkit.getScheduler().runTaskLater(CansteinGottesdienst.getInstance(), () -> {
                    Location location = WorlGuardUtil.getCenter(region, world);

                    for (int i = 0; i < currentSave; i++) {
                        world.dropItemNaturally(location, Items.WINE.getItemStack());
                    }
                }, 1);

                this.jumps = 0;

                reset();

                return;
            }

            this.armorStand.setCustomName(hologramJump());
        }

        public void reset() {
            this.traversed = false;
            current = current % 5;

            traverseAllBlocks(Material.AIR);

            this.armorStand.setCustomName(hologramAdd());
        }

        private void traverseAllBlocks(Material material) {
            Bukkit.getScheduler().runTaskLater(CansteinGottesdienst.getInstance(), () -> {
                traverseAllBlocksHard(material);
            }, 1);
        }

        private void traverseAllBlocksHard(Material material) {
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
        }

        private String hologramAdd() {
            return Placeholder.replace(Placeholder.replace(grapes_hologram, "current", this.current),
                    "max", (max + 1));
        }

        private String hologramJump() {
            return Placeholder.replace(Placeholder.replace(jump_hologram, "jumps", this.jumps),
                    "needed_jumps", this.neededJumps);
        }

        public void delete() {
            this.armorStand.remove();

            traverseAllBlocksHard(Material.AIR);
        }
    }
}
