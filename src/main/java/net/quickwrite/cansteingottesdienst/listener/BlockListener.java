package net.quickwrite.cansteingottesdienst.listener;

/*
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

        if(!regionSet.testState(WorlGuardUtil.getBukkitPlayer(player), Flags.INFINITE_CROPS))
            return;

        CropInfo.CropData cropData = CropInfo.getData(event.getBlock().getType());

        if(cropData == null)
            return;

        World world = event.getPlayer().getWorld();

        if (cropData.isCustomBlock()) {
            onCustomBlockBreak(event, cropData);
            return;
        }

        onNormalBlockBreak(event, cropData);
    }

    private int getRandomInt(int min, int max) {
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    private void onCustomBlockBreak(BlockBreakEvent event, CropInfo.CropData cropData) {
        CustomBlock block = CansteinGottesdienst.BLOCKS.getBlock(event.getBlock().getType());
        if(!block.isCustomBlock(event.getBlock().getLocation())) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(CansteinGottesdienst.getInstance(), new Runnable() {
            @Override
            public void run() {
                block.onBlockPlace(event.getBlock().getLocation());
            }
        }, getRandomInt(40, 1000));
    }

    private void onNormalBlockBreak(BlockBreakEvent event, CropInfo.CropData cropData) {
        event.setCancelled(true);

        if (!(event.getBlock().getBlockData() instanceof Ageable)) {
            return;
        }

        Ageable crop = ((Ageable) event.getBlock().getBlockData());

        if(crop.getAge() != crop.getMaximumAge()) {
            return;
        }

        event.getBlock().setType(crop.getMaterial());

        for(ItemStack drop : cropData.getItems()) {
            event.getPlayer().getWorld().dropItem(event.getBlock().getLocation().add(0.5,-0.5,0.5), drop);
        }

        Bukkit.getScheduler().runTaskLater(CansteinGottesdienst.getInstance(), new Runnable() {
            @Override
            public void run() {
                crop.setAge(crop.getMaximumAge());

                event.getBlock().setBlockData(crop);
            }
        }, getRandomInt(40, 1000));
    }
}
*/
