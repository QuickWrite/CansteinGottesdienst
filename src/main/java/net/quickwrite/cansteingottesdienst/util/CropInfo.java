package net.quickwrite.cansteingottesdienst.util;

import net.quickwrite.cansteingottesdienst.blocks.CustomBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropInfo {
    private static final Map<Material, CropData> cropMap = new HashMap<>();

    public static void addCrop(Material material, List<ItemStack> items) {
        cropMap.put(material, new CropData(items));
    }

    public static void addCrop(CustomBlock customBlock) {
        cropMap.put(customBlock.getBaseBlock(), new CropData(customBlock));
    }

    public static CropData getData(Material material) {
        return cropMap.get(material);
    }

    public static void flush() {
        cropMap.clear();
    }

    public static class CropData {
        private final List<ItemStack> items;
        private final CustomBlock customBlock;

        public CropData(List<ItemStack> items) {
            this.items = items;
            this.customBlock = null;
        }

        public CropData(CustomBlock customBlock) {
            this.items = null;
            this.customBlock = customBlock;
        }

        public List<ItemStack> getItems() {
            return this.items;
        }

        public String toString() {
            return "CropData{items[" + items + ",customBlock{" + customBlock + "}]}";
        }

        public boolean isCustomBlock() {
            return customBlock != null;
        }

        public CustomBlock getCustomBlock() {
            return this.customBlock;
        }
    }
}
