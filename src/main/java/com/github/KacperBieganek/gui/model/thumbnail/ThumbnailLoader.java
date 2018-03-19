package com.github.KacperBieganek.gui.model.thumbnail;

import java.lang.ref.WeakReference;
import java.util.Map;

public class ThumbnailLoader extends Thread {

    private String url;
    private Map<String,WeakReference<Thumbnail>> thumbnailCache;

    public ThumbnailLoader(String url,Map map){
        this.url = url;
        this.thumbnailCache = map;
    }

    @Override
    public void run() {
        super.run();
        
    }
}
