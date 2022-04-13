package net.quickwrite.cansteingottesdienst.map;

import net.quickwrite.cansteingottesdienst.items.Items;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Locale;

public class DisplayMapRenderer extends MapRenderer {

    public static final DisplayMapRenderer INSTANCE;
    private final HashMap<String, BufferedImage> images;
    private HashMap<MapView, Boolean> updates;

    static {
        INSTANCE = new DisplayMapRenderer();
    }

    private DisplayMapRenderer() {
        updates = new HashMap<>();
        images = new HashMap<>();

        loadImages();
    }

    private void loadImages() {
        InformationGatherer gatherer = InformationGatherer.INSTANCE;
        for(String key : gatherer.getAmounts().keySet()){
            images.put(key, ImageManager.INSTANCE.loadImage(key));
        }
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        if(updates.getOrDefault(map, false)) return; //Don't waste time to redraw

        for(int x = 0; x < 128; x++){
            for(int y = 0; y < 128; y++){
                canvas.setPixel(x, y, MapPalette.WHITE);
            }
        }
        int y = 5;
        InformationGatherer informationGatherer = InformationGatherer.INSTANCE;
        for(Items item : informationGatherer.getToSearchAmounts().keySet()){
            BufferedImage image = images.get(item.name());

            drawImage(image, 5, y, canvas);

            int amount = informationGatherer.getAmounts().get(item.name());
            int toSearch = informationGatherer.getToSearchAmounts().get(item);

            String text = amount < toSearch ? "§16;" : "§4;";
            text += amount + " / " + toSearch;

            canvas.drawText(5 + image.getWidth() + 10, y + (image.getHeight() - MinecraftFont.Font.getHeight()) / 2, MinecraftFont.Font, text);
            y += image.getHeight() + 5;
        }


        updates.put(map, true);
    }

    public void drawImage(BufferedImage image, int px, int py, MapCanvas canvas){
        byte[] data = MapPalette.imageToBytes(image);
        for(int y = 0; y < image.getWidth(); y++){
            for(int x = 0; x < image.getHeight(); x++){
                int p = image.getRGB(x, y);
                int a = (p>>24)&0xff;
                if(a == 255){
                    canvas.setPixel(px + x, py + y, data[y * image.getWidth() + x]);
                }
            }
        }
    }


    public void update(){
        updates.clear();
    }
}
