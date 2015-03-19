package com.fabriciooliveira.ubookteste.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by fabriciooliveira on 2/19/15.
 *
 * Classe responsavel por manter o cache das informacoes de imagens em disco
 */
public class BitMapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public BitMapCache(int maxSize){
        super(maxSize);

    }

    public BitMapCache(){
        this(getDefaultCacheSize());
    }

    public static int getDefaultCacheSize(){
        int memoriaMaxima = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int tamanhoCache = memoriaMaxima / 8;

        return tamanhoCache;
    }

    @Override
    public int sizeOf(String key, Bitmap value){
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
