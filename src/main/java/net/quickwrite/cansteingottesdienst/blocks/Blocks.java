package net.quickwrite.cansteingottesdienst.blocks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Blocks {

    private final HashMap<Material, CustomBlock> blocks;

    public Blocks(){
        blocks = new HashMap<>();
    }

    public void register(CustomBlock customBlock){
        blocks.put(customBlock.getBaseBlock(), customBlock);
    }

    public HashMap<Material, CustomBlock> getBlocks() {
        return blocks;
    }

    public CustomBlock getBlock(Material m){
        return blocks.get(m);
    }

    public CustomBlock getBlock(ItemStack s){
        for(CustomBlock cb : blocks.values()){
            if(cb.getInvItem().isSimilar(s)) return cb;
        }
        return null;
    }
    public CustomBlock getBlock(String s){
        for(CustomBlock cb : blocks.values()){
            if(cb.getIdentifier().equalsIgnoreCase(s)) return cb;
        }
        return null;
    }

    public ArrayList<String> getIdentifiers(){
        ArrayList<String> ids = new ArrayList<>();
        for(CustomBlock cb : blocks.values()){
            ids.add(cb.getIdentifier());
        }
        return ids;
    }
}
