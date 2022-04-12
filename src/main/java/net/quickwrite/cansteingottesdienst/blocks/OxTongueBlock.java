package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import net.quickwrite.cansteingottesdienst.items.Items;
import org.bukkit.Material;

public class OxTongueBlock extends CustomBlock {
    public OxTongueBlock() {
        super("ox_tongue", new ItemBuilder(Material.DANDELION).setCustomModelData(2).build(),
                Items.OX_TONGUE.getItemStack(),
                new ItemBuilder(Material.DANDELION).setCustomModelData(2).setDisplayName("§2Bitterkraut (Place)").build());
    }
}
