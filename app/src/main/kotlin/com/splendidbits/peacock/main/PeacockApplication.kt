package com.splendidbits.peacock.main

import android.app.Application
import android.content.SharedPreferences
import com.github.javiersantos.piracychecker.PiracyChecker
import com.github.javiersantos.piracychecker.enums.InstallerID
import com.splendidbits.peacock.BuildConfig
import com.splendidbits.peacock.injection.*
import javax.inject.Inject


class PeacockApplication : Application() {
    @Inject
    lateinit var securePreferences: SharedPreferences

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
        graph.inject(this)

        if (!BuildConfig.DEBUG) {
            PiracyChecker(this)
                    .enableInstallerId(InstallerID.GOOGLE_PLAY)
                    .enableUnauthorizedAppsCheck(true)
                    .enableDebugCheck(true)
                    .enableEmulatorCheck(true)
                    .start()
        }
    }
}

