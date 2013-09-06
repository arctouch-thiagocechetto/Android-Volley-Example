package com.technotalkative.volleyexamplesimple;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class ImageLruCache extends LruCache<String, Bitmap> implements ImageCache {

	public ImageLruCache(int maxSize) {
		super(maxSize);
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
