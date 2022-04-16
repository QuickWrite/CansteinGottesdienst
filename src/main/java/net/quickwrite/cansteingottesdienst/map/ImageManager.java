package net.quickwrite.cansteingottesdienst.map;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.util.JarUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class ImageManager {

    public static final ImageManager INSTANCE;

    static {
        INSTANCE = new ImageManager();
    }

    private ImageManager(){
        unpack();
    }

    private void unpack() {
        File f = new File(CansteinGottesdienst.getInstance().getDataFolder(), "images");
        if(!f.exists()){
            f.getParentFile().mkdirs();
            try {
                JarUtils.copyResourcesToDirectory(JarUtils.jarForClass(ImageManager.class, null), "images", f.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BufferedImage loadImage(String name){
        File f = new File(CansteinGottesdienst.getInstance().getDataFolder(), "images/" + name.toLowerCase(Locale.ROOT) + ".png");
        if(!f.exists()){
            return new BufferedImage(16, 16, ColorSpace.TYPE_RGB);
        }
        try {
            return ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
            return new BufferedImage(16, 16, ColorSpace.TYPE_RGB);
        }
    }
}
