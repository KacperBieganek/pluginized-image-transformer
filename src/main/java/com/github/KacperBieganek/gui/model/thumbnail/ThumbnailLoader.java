package com.github.KacperBieganek.gui.model.thumbnail;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;

public class ThumbnailLoader extends Thread {

    private static final int THUMBNAIL_MAX_WIDTH = 100;
    private static final int THUMBNAIL_MAX_HEIGHT = 100;

    private Map<String, WeakReference<Thumbnail>> thumbnailCache;
    private File file;

    public ThumbnailLoader(File file, Map map) {
        this.file = file;
        this.thumbnailCache = map;
    }

    @Override
    public void run() {
        super.run();
        try {
            Thumbnail thumbnail = new Thumbnail(new ImageIcon(ImageIO.read(file)
                    .getScaledInstance(THUMBNAIL_MAX_WIDTH, THUMBNAIL_MAX_HEIGHT, Image.SCALE_SMOOTH)));
            thumbnailCache.put(file.getCanonicalPath(), new WeakReference<>(thumbnail));
        } catch (IOException e) {

        }

    }
}
