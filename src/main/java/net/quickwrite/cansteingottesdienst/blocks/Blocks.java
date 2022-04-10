package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
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

        loadBlockConfig(customBlock);
    }

    private void loadBlockConfig(CustomBlock cb) {
        FileConfiguration config = CansteinGottesdienst.CUSTOM_BLOCK_CONFIG.getConfig();
        if (!config.contains("cbs." + cb.getIdentifier())){
            System.out.println("Unknown Identifier: " + cb.getIdentifier());
            return;
        }
        cb.fillArmorstands(config.getConfigurationSection("cbs." + cb.getIdentifier()));
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

    public void save(){
        FileConfiguration config = CansteinGottesdienst.CUSTOM_BLOCK_CONFIG.getConfig();
        for(CustomBlock cb : blocks.values()){
            cb.serialize(config);
        }
        CansteinGottesdienst.CUSTOM_BLOCK_CONFIG.saveConfig();
    }
}
