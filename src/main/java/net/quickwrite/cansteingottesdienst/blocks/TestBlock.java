package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;

public class TestBlock extends CustomBlock{

    public TestBlock() {
        super(  "testBlock",
                new ItemBuilder(Material.AXOLOTL_BUCKET).build(),
                new ItemBuilder(Material.DIAMOND).setDisplayName("§cDrop Test").build(),
                new ItemBuilder(Material.DIAMOND).setDisplayName("§aCustomBlock TestBlock").setCustomModelData(100).build(),
                Material.AMETHYST_BLOCK);
    }
}
