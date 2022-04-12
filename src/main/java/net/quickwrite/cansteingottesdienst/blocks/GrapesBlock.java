package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import net.quickwrite.cansteingottesdienst.items.Items;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;

public class GrapesBlock extends CustomBlock implements IHarvestable{
    public GrapesBlock() {
        super("grapes_block", new ItemBuilder(Material.SWEET_BERRIES).setCustomModelData(2).build(),
                Items.GRAPES.getItemStack(),
                new ItemBuilder(Material.SWEET_BERRIES).setCustomModelData(2).setDisplayName("§5Trauben (Place)").build());
    }

    @Override
    public void convert(ArmorStand stand) {
        Location loc = stand.getLocation();
        dropItem(loc);
        stand.remove();
        CansteinGottesdienst.BLOCKS.getBlock("empty_grapes_block").onBlockPlace(loc);
    }
}
