package com.splendidbits.peacock.injection

import android.content.Context
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.squareup.picasso.LruCache
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
class ComponentModule {
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient().newBuilder()
                .followRedirects(true)
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpDownloader(context: Context): OkHttp3Downloader {
        return OkHttp3Downloader(context, Long.MAX_VALUE)
    }

    @Provides
    @Singleton
    fun providePicassoBuilder(context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso.Builder {
        return Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .loggingEnabled(false)
                .memoryCache(LruCache(context))
    }

    @Provides
    @Singleton
    fun provideExoPlayerDataSourceFactory(): DataSource.Factory {
        return DefaultHttpDataSourceFactory(
                "nbcnews-android",
                null /* listener */,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true)
    }
}