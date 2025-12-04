package com.mini_pos.helper_function;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class ImageCache {
    private static Map<String, ImageIcon> cache = new HashMap<>();

    public static ImageIcon get(String fileName) {
        if (cache.containsKey(fileName)) {
            return cache.get(fileName);
        }

        URL imgUrl = ImageCache.class.getClassLoader()
                .getResource("static/images/" + fileName);

        if (imgUrl == null) {
            return null;
        }

        ImageIcon icon = new ImageIcon(imgUrl);
        Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaled);

        cache.put(fileName, icon);
        return icon;
    }
}