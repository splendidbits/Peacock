package com.splendidbits.peacock.injection

import android.content.Context
import com.mklimek.sslutilsandroid.SslUtils
import com.splendidbits.peacock.BuildConfig
import com.splendidbits.peacock.R
import com.squareup.picasso.LruCache
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Protocol
import javax.inject.Singleton
import javax.net.ssl.SSLSession




@Module
class ComponentModule {
    private val userAgent = "peacock/${BuildConfig.VERSION_NAME}"

    @Provides
    @Singleton
    fun provideOkHttpBuilder(context: Context): OkHttpClient.Builder {
        return OkHttpClient()
                .newBuilder()
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
        val lol1 = SslUtils.getSslContextForCertificateFile(context, "1strings.xml")

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
}