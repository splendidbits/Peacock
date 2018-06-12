package com.splendidbits.peacock.injection

import android.arch.persistence.room.Room
import android.content.Context
import com.mklimek.sslutilsandroid.SslUtils
import com.splendidbits.peacock.dao.ArticlesDao
import com.splendidbits.peacock.dao.NewsRepository
import com.splendidbits.peacock.service.NewsDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
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
        val feedSslContext = SslUtils.getSslContextForCertificateFile(context, "0strings.xml")

        okHttpClientBuilder
                .sslSocketFactory(feedSslContext.getSocketFactory())
                .hostnameVerifier({ url: String, session: SSLSession ->
                    session.peerHost.contains("devicestransform-stg.elasticbeanstalk.com")
                })

        return NewsRepository(context, okHttpClientBuilder.build(), articlesDao)
    }
}
