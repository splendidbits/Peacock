package com.splendidbits.peacock.injection

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.splendidbits.peacock.R
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
    fun provideRecyclerAdapter(context: Context, picasso: Picasso): TrendingRecyclerAdapter {
        return TrendingRecyclerAdapter(context, picasso)
    }

    @Provides
    @Singleton
    fun provideSecurePreferences(context: Context): SharedPreferences {
        val securePreferencesKey: String = context.getString(R.string.shared_preferences_secure)
        return context.getSharedPreferences(securePreferencesKey, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun ProvideResources(context: Context): Resources {
        return context.resources
    }
}
