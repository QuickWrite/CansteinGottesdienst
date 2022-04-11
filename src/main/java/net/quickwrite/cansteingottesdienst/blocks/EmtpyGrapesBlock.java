package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import org.bukkit.Material;

public class EmtpyGrapesBlock extends CustomBlock{

    public EmtpyGrapesBlock() {
        super("empty_grapes",
                new ItemBuilder(Material.SWEET_BERRIES).setCustomModelData(3).build(),
                null,
                new ItemBuilder(Material.SWEET_BERRIES).setCustomModelData(3).setDisplayName("§5Empty Trauben (Place)").build()
        );
    }
}
