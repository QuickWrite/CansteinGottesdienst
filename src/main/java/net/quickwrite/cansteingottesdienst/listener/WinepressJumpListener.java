package net.quickwrite.cansteingottesdienst.listener;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.quickwrite.cansteingottesdienst.util.worldguard.WorlGuardUtil;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import net.quickwrite.cansteingottesdienst.util.storage.WinepressList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WinepressJumpListener implements Listener {
    @EventHandler
    public void onPlayerJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!(event.getFrom().getBlockY() < event.getTo().getBlockY() && !player.isSwimming() && !player.isFlying()))
            return;

        if (!event.getFrom().getBlock().getType().equals(Material.PURPLE_CARPET))
            return;


        ApplicableRegionSet regionSet = WorlGuardUtil.getRegionSet(event.getFrom().getBlock());

        WinepressList.WinepressInfo regionInfo = getRegionInfo(regionSet);

        if (regionInfo == null)
            return;

        regionInfo.bumpJumps();
    }

    private WinepressList.WinepressInfo getRegionInfo(ApplicableRegionSet regionSet) {
        for (ProtectedRegion region : regionSet.getRegions()) {
            if (region.getFlag(Flags.WINE_PRESS) == null)
                continue;

            WinepressList.WinepressInfo info = WinepressList.WINEPRESSES.get(region);

            if (info == null)
                continue;

            return info;
        }

        return null;
    }
}
