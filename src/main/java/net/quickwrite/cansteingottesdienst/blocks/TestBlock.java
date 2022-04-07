package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;

public class TestBlock extends CustomBlock{

    public TestBlock(Location loc) {
        super(  new ItemBuilder(Material.AXOLOTL_BUCKET).build(),
                new ItemBuilder(Material.DIAMOND).setDisplayName("§cDrop Test").build(),
                new ItemBuilder(Material.STRUCTURE_BLOCK).setCustomModelData(100).build(), Material.AMETHYST_BLOCK);
    }
}
