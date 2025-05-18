package com.swsbt.secret

import android.app.Application
import com.facebook.stetho.Stetho
import com.swsbt.secret.model.local.AppDatabase
import io.ysq.crasher.helper.util.CrashHandler
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    companion object {
        private var INSTANCE: App? = null
        fun getInstance(): App? {
            return INSTANCE
        }
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(appModule)
        }

        AppDatabase.init(this)

        CrashHandler.init(this)

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

}