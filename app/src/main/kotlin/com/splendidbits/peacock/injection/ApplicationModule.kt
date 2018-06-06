package com.splendidbits.peacock.injection

import android.content.Context
import com.splendidbits.peacock.adapter.TrendingRecyclerAdapter
import com.splendidbits.peacock.main.PeacockApplication
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(private var application: PeacockApplication) {

    @Provides
    @Singleton
    fun provideApplication(): PeacockApplication {
        return application
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    fun provideRecyclerAdapter(context: Context, picassoBuilder: Picasso.Builder): TrendingRecyclerAdapter {
        return TrendingRecyclerAdapter(context, picassoBuilder)
    }
}
