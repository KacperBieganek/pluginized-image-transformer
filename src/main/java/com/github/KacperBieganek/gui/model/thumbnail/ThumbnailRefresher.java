package com.github.KacperBieganek.gui.model.thumbnail;

import javax.swing.*;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Map;

public class ThumbnailRefresher extends SwingWorker<Map<String, WeakReference<Thumbnail>>, Thumbnail> {

    Map<String, WeakReference<Thumbnail>> cache;
    File[] files;

    ThumbnailRefresher(File[] files, Map map) {
        this.files = files;
        this.cache = map;
    }

    @Override
    protected Map<String, WeakReference<Thumbnail>> doInBackground() throws Exception {
        return null;
    }
}
