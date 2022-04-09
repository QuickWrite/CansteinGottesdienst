package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import org.bukkit.Material;

public class GrapesBlock extends CustomBlock {
    public GrapesBlock() {
        super("grapes_block", new ItemBuilder(Material.SWEET_BERRIES).setCustomModelData(2).build(),
                new ItemBuilder(Material.SWEET_BERRIES).setCustomModelData(1).setDisplayName("§5Trauben").build(),
                new ItemBuilder(Material.SWEET_BERRIES).setCustomModelData(1).setDisplayName("§5Trauben").build(),
                Material.FERN);
    }
}
