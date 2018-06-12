package com.splendidbits.peacock.main

import android.app.Application
import androidx.multidex.MultiDex
import com.splendidbits.peacock.injection.*


class PeacockApplication : Application() {
    companion object {
        @JvmStatic
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        graph = DaggerAppComponent
                .builder()
                .componentModule(ComponentModule())
                .applicationModule(ApplicationModule(this))
                .dataModule(DataModule())
                .build()
    }
}

