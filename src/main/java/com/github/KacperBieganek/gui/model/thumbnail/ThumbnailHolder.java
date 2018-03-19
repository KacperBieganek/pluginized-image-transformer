package com.github.KacperBieganek.gui.model.thumbnail;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThumbnailHolder {
    private Map <String,WeakReference<Thumbnail> thumbnailCache;

    ThumbnailHolder(){
        thumbnailCache = new ConcurrentHashMap<>();
    }

    public Thumbnail getThumbnail(String url){
        if(thumbnailCache.get(url).get() != null)

    }
}
