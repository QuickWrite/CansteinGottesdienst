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
import java.io.InputStream;
import java.util.Locale;

public class ImageManager {

    private ImageManager(){
    }

    public static BufferedImage loadImage(String name){
        try {
            InputStream stream = CansteinGottesdienst.getInstance().getClass().getResourceAsStream("/images/" + name.toLowerCase(Locale.ROOT) + ".png");
            System.out.println("/images/" + name.toLowerCase(Locale.ROOT) + ".png");
            if (stream != null) return ImageIO.read(stream);
            return null; //new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; //new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
    }
}
