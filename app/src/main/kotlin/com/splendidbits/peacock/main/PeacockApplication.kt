package com.splendidbits.peacock.main

import android.support.multidex.MultiDexApplication
import com.splendidbits.peacock.injection.*


class PeacockApplication : MultiDexApplication() {
    companion object {
        @JvmStatic
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerAppComponent
                .builder()
                .componentModule(ComponentModule())
                .applicationModule(ApplicationModule(this))
                .dataModule(DataModule())
                .build()
    }
}

