package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import org.bukkit.Material;

public class CeleryBlock extends CustomBlock {
    public CeleryBlock() {
        super("celery_block", new ItemBuilder(Material.CARROT).setCustomModelData(2).build(),
                new ItemBuilder(Material.CARROT).setCustomModelData(1).setDisplayName("§bSellerie").build(),
                new ItemBuilder(Material.CARROT).setCustomModelData(1).setDisplayName("§bSellerie (Place)").build());
    }
}
