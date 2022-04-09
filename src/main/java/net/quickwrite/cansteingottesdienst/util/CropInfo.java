package net.quickwrite.cansteingottesdienst.util;

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

    public static CropData getDrops(Material material) {
        return cropMap.get(material);
    }

    public static void flush() {
        cropMap.clear();
    }

    public static class CropData {
        private final List<ItemStack> items;

        public CropData(List<ItemStack> items) {
            this.items = items;
        }

        public List<ItemStack> getItems() {
            return this.items;
        }

        public String toString() {
            return "CropData{items[" + items + "]}";
        }
    }
}
