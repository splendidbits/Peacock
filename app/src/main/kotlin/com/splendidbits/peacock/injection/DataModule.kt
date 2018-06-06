package com.splendidbits.peacock.injection

import android.arch.persistence.room.Room
import android.content.Context
import com.splendidbits.peacock.dao.NewsRepository
import com.splendidbits.peacock.service.NewsDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton


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
    fun provideTrendingRepository(context: Context, okHttpClient: OkHttpClient): NewsRepository {
        return NewsRepository(context, okHttpClient)
    }
}
