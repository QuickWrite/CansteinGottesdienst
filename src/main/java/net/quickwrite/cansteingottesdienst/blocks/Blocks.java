package net.quickwrite.cansteingottesdienst.blocks;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Blocks {

    private final List<CustomBlock> blocks;

    public Blocks(){
        blocks = new ArrayList<>();
    }

    public void register(CustomBlock customBlock){
        blocks.add(customBlock);
    }

    public List<CustomBlock> getBlocks() {
        return blocks;
    }

    public CustomBlock getBlock(ItemStack s){
        for(CustomBlock cb : blocks){
            if(cb.getInvItem().isSimilar(s)) return cb;
        }
        return null;
    }
    public CustomBlock getBlock(String s){
        for(CustomBlock cb : blocks){
            if(cb.getIdentifier().equalsIgnoreCase(s)) return cb;
        }
        return null;
    }

    public ArrayList<String> getIdentifiers(){
        ArrayList<String> ids = new ArrayList<>();
        for(CustomBlock cb : blocks){
            ids.add(cb.getIdentifier());
        }
        return ids;
    }
}
