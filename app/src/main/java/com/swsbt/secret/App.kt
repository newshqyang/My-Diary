package com.swsbt.secret

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.swsbt.secret.helper.utils.BaseUtil
import com.swsbt.secret.model.local.AppDatabase
import io.ysq.crasher.helper.util.CrashHandler
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        BaseUtil.init(this)

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