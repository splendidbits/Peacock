package com.splendidbits.peacock.main

import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import com.github.javiersantos.piracychecker.PiracyChecker
import com.github.javiersantos.piracychecker.enums.InstallerID
import com.splendidbits.peacock.BuildConfig
import com.splendidbits.peacock.R
import com.splendidbits.peacock.injection.*
import com.squareup.leakcanary.LeakCanary
import javax.inject.Inject


class PeacockApplication : MultiDexApplication() {
    @Inject
    lateinit var securePreferences: SharedPreferences

    companion object {
        @JvmStatic
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

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
                    .enableGooglePlayLicensing(getString(R.string.app_licensing_key))
                    .start()
        }
    }
}

