package net.quickwrite.cansteingottesdienst.map;

import net.quickwrite.cansteingottesdienst.items.Items;
import org.bukkit.entity.Player;
import org.bukkit.map.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

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
        MapInformation gatherer = MapInformation.INSTANCE;
        for(String key : gatherer.getAmounts().keySet()){
            images.put(key, ImageManager.INSTANCE.loadImage(key));
        }
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        if(updates.getOrDefault(map, false)) return; //Don't waste time to redraw

        for(int x = 0; x < 128; x++){
            for(int y = 0; y < 128; y++){
                canvas.setPixel(x, y, MapPalette.matchColor(Color.WHITE));
            }
        }

        MapInformation informationGatherer = MapInformation.INSTANCE;

        String text = "Zutatenliste";
        canvas.drawText((128 - MinecraftFont.Font.getWidth(text)) / 2, 4, MinecraftFont.Font, text);

        int y = MinecraftFont.Font.getHeight() + 10;
        for(Items item : informationGatherer.getToSearchAmounts().keySet()){
            BufferedImage image = images.get(item.name());
            drawItemProgress(canvas, item, y);

            /*drawImage(image, 5, y, canvas);

            int amount = informationGatherer.getAmounts().get(item.name());
            int toSearch = informationGatherer.getToSearchAmounts().get(item);

            String text = amount < toSearch ? "§16;" : "§4;";
            text += amount + " / " + toSearch;

            canvas.drawText(5 + image.getWidth() + 10, y + (image.getHeight() - MinecraftFont.Font.getHeight()) / 2, MinecraftFont.Font, text);


             */
            y += image.getHeight() + 5;

        }


        updates.put(map, true);
    }

    public void drawItemProgress(MapCanvas canvas, Items item, int y){
        int x = 7;
        drawImage(images.get(item.name()), x, y, canvas);
        y += 2;
        MapInformation information = MapInformation.INSTANCE;

        int available = information.getAmounts().get(item.name());
        int toSearch = information.getToSearchAmounts().get(item);

        float div = (available / (toSearch + 0f));

        byte color;
        if (div < .25) color = MapPalette.matchColor(Color.RED);
        else if (div < .5) color = MapPalette.matchColor(Color.ORANGE);
        else if (div < .75) color = MapPalette.matchColor(Color.YELLOW);
        else color = MapPalette.matchColor(Color.GREEN);

        int width = 128 - 16 - 2*x - 5;
        for (int oy = 0; oy < 13; oy++){
            for (int ox = 0; ox < width; ox++){
                if(oy == 0 || oy == 12 || ox == 0 || ox == width - 1)
                    canvas.setPixel(x + ox + 16 + 5, y + oy, color);
            }
        }

        for (int oy = 0; oy < 11; oy++) {
            for (int ox = 0; ox < width; ox++) {
                float rate = ox / (width + 0f);
                if(rate < div){
                    canvas.setPixel(x + ox + 16 + 5, y + oy + 1, color);
                }
            }
        }

        String text = available + " / " + toSearch;
        canvas.drawText(x + 16 + 5 + (width - MinecraftFont.Font.getWidth(text)) / 2,
                y - 1 + (16 - MinecraftFont.Font.getHeight()) / 2, MinecraftFont.Font, text);


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
