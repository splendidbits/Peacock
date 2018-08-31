package com.splendidbits.peacock.injection

import android.content.Context
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.mklimek.sslutilsandroid.SslUtils
import com.splendidbits.peacock.R
import com.squareup.picasso.LruCache
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import im.ene.toro.exoplayer.BaseMeter
import im.ene.toro.exoplayer.ExoCreator
import im.ene.toro.exoplayer.MediaSourceBuilder
import im.ene.toro.exoplayer.ToroExo
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Protocol
import javax.inject.Singleton
import javax.net.ssl.SSLSession


@Module
class ComponentModule {
    @Provides
    @Singleton
    fun provideOkHttpBuilder(context: Context): OkHttpClient.Builder {
        val userAgent = context.getString(R.string.user_agent)

        return OkHttpClient().newBuilder()
                .followRedirects(true)
                .addInterceptor({ chain ->
                    val request = chain.request()
                            .newBuilder()
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .header("User-Agent", userAgent)
                            .build()

                    chain.proceed(request)
                })
    }

    @Provides
    @Singleton
    fun provideOkHttpDownloader(context: Context, okHttpClientBuilder: OkHttpClient.Builder): OkHttp3Downloader {
        val lol1 = SslUtils.getSslContextForCertificateFile(context, "cert2.cert")

        okHttpClientBuilder
                .sslSocketFactory(lol1.getSocketFactory())
                .protocols(listOf(Protocol.HTTP_1_1))
                .hostnameVerifier({ url: String, session: SSLSession ->
                    session.peerHost.contains(context.getString(R.string.latest_feed_images_url))
                })

        return OkHttp3Downloader(okHttpClientBuilder.build())
    }

    @Provides
    @Singleton
    fun providePicassoBuilder(context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso {
        val picassoBuilder = Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .loggingEnabled(false)
                .memoryCache(LruCache(context))

        return picassoBuilder.build()
    }

    @Provides
    @Singleton
    fun provideExoLoadControl(): DefaultLoadControl {
        return DefaultLoadControl(DefaultAllocator(true,
                DefaultLoadControl.DEFAULT_MIN_BUFFER_MS))
    }

    @Provides
    @Singleton
    fun provideExoBandwidthMeter(): BaseMeter<BandwidthMeter, TransferListener<Any>> {
        val transferListener  = object : TransferListener<Any> {
            override fun onTransferStart(source: Any?, dataSpec: DataSpec?) {
            }

            override fun onTransferEnd(source: Any?) {
            }

            override fun onBytesTransferred(source: Any?, bytesTransferred: Int) {
            }
        }

        return BaseMeter(DefaultBandwidthMeter(), transferListener)
    }

    @Provides
    @Singleton
    fun provideExoDataSourceFactory(context: Context): DataSource.Factory {
        val userAgent = context.getString(R.string.user_agent)
        return DefaultHttpDataSourceFactory(userAgent, null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true)
    }

    @Provides
    @Singleton
    fun provideExoCache(context: Context): Cache {
        val maxCacheBytes: Long = 128 * 1000 * 1000
        return SimpleCache(context.cacheDir, LeastRecentlyUsedCacheEvictor(maxCacheBytes))
    }

    @Provides
    @Singleton
    fun provideExoCreator(context: Context, loadControl: DefaultLoadControl,
                          meter: BaseMeter<BandwidthMeter, TransferListener<Any>>,
                          dataSourceFactory: DataSource.Factory, cache: Cache): ExoCreator {

        val toroExo = ToroExo.with(context)
        val toroConfig = toroExo.defaultConfig
                .newBuilder()
                .setLoadControl(loadControl)
                .setCache(cache)
                .setMeter(meter)
                .setDataSourceFactory(dataSourceFactory)
                .setMediaSourceBuilder(MediaSourceBuilder.DEFAULT)
                .setExtensionMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER)

        return toroExo.getCreator(toroConfig.build())
    }
}