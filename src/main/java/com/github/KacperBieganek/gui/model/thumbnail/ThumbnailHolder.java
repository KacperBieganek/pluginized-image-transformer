package com.github.KacperBieganek.gui.model.thumbnail;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThumbnailHolder {
    private Map<String, WeakReference<Thumbnail>> thumbnailCache;

    public ThumbnailHolder() {
        thumbnailCache = new ConcurrentHashMap<>();
    }

    public Thumbnail getThumbnail(File file) throws IOException, InterruptedException {
        WeakReference<Thumbnail> tmp = thumbnailCache.get(file.getCanonicalPath());
        if (tmp != null && tmp.get() != null ) {
            return tmp.get();
        } else {
            Thread loader = new ThumbnailLoader(file.getCanonicalFile(),thumbnailCache);
            loader.start();
            loader.join();
            return thumbnailCache.get(file.getCanonicalPath()).get();
        }
    }
}
