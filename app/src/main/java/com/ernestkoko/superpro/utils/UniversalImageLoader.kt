package com.ernestkoko.superpro.utils

import android.content.Context
import android.util.Log
import com.ernestkoko.superpro.R
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer

class UniversalImageLoader(private val context: Context) {
    private val TAG = "UniversalImageLoader"
    private val defaultImage = R.drawable.ic_product_image

    fun getConfig(): ImageLoaderConfiguration{
        Log.d(TAG, "getConfig: Returning image loader configuration");
        // UNIVERSAL IMAGE LOADER SETUP
        val defaultOptions = DisplayImageOptions.Builder()
            .showImageOnLoading(defaultImage) // resource or drawable
            .showImageForEmptyUri(defaultImage) // resource or drawable
            .showImageOnFail(defaultImage) // resource or drawable
            .cacheOnDisk(true).cacheInMemory(true)
            .cacheOnDisk(true).resetViewBeforeLoading(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .displayer(FadeInBitmapDisplayer(300)).build()
        val config  = ImageLoaderConfiguration.Builder(context)
            .defaultDisplayImageOptions(defaultOptions)
            .memoryCache(WeakMemoryCache())
            .diskCacheSize(100 * 1024 * 1024)
            .build()
        return config
    }
}