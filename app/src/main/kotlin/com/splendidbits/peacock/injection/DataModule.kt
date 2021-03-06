package com.splendidbits.peacock.injection

import android.content.Context
import androidx.room.Room
import com.mklimek.sslutilsandroid.SslUtils
import com.splendidbits.peacock.dao.ArticlesDao
import com.splendidbits.peacock.dao.NewsRepository
import com.splendidbits.peacock.service.NewsDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import javax.inject.Singleton
import javax.net.ssl.SSLSession

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): NewsDatabase {
        return Room.databaseBuilder(context, NewsDatabase::class.java, "peacock.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    fun provideTrendingDao(newsDatabase: NewsDatabase): ArticlesDao {
        return newsDatabase.trendingRoomDao()
    }

    @Provides
    fun provideTrendingRepository(context: Context, okHttpClientBuilder: OkHttpClient.Builder, articlesDao: ArticlesDao): NewsRepository {
        val feedSslContext = SslUtils.getSslContextForCertificateFile(context, "cert1.cert")

        okHttpClientBuilder
                .protocols(listOf(Protocol.HTTP_1_1))
                .addInterceptor({ chain ->
                    val request = chain.request()
                            .newBuilder()
                            .addHeader("Connection", "keep-alive")
                            .build()

                    chain.proceed(request)
                })
                .sslSocketFactory(feedSslContext.getSocketFactory())
                .hostnameVerifier({ _: String, session: SSLSession ->
                    session.peerHost.contains("devicestransform-stg.elasticbeanstalk.com")
                })

        return NewsRepository(context, okHttpClientBuilder.build(), articlesDao)
    }
}
