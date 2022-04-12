package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import net.quickwrite.cansteingottesdienst.items.Items;
import org.bukkit.Material;

public class CeleryBlock extends CustomBlock {
    public CeleryBlock() {
        super("celery_block", new ItemBuilder(Material.CARROT).setCustomModelData(2).build(),
                Items.CELERY.getItemStack(),
                new ItemBuilder(Material.CARROT).setCustomModelData(2).setDisplayName("§bSellerie (Place)").build()

        );
    }
}
