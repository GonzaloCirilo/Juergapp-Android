package pe.com.redcups.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

/**
 * Custom caching class volley image loader.
 *
 * Sketch Project Studio
 * Created by Angga on 22/04/2016 23.29.
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public LruBitmapCache(int maxSize) {
        super(maxSize);
    }

    public LruBitmapCache(Context context) {
        this(getCacheSize(context));
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    /**
     * Returns a cache size equal to approximately three screens worth of images.
     *
     * @param context parent context
     * @return int size of cache
     */
    public static int getCacheSize(Context context) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }
}
