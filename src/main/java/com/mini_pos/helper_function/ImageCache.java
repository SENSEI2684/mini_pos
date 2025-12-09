package com.mini_pos.helper_function;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class ImageCache {
    private static Map<String, ImageIcon> cache = new HashMap<>();

    public static ImageIcon get(String fileName) {
        if (fileName == null || fileName.isEmpty()) return null;

        if (cache.containsKey(fileName)) {
            return cache.get(fileName);
        }

        try {
            String projectPath = System.getProperty("user.dir");
            File imgFile = new File(projectPath + "/src/main/resources/static/images/" + fileName);

            if (!imgFile.exists()) return null;

            ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
            Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaled);

            cache.put(fileName, icon);
            return icon;

        } catch (Exception e) {
            return null;
        }
    }
}