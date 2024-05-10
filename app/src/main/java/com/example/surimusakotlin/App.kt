package com.example.surimusakotlin

import android.app.Application
import com.example.surimusakotlin.di.appModule
import com.example.surimusakotlin.di.dataModule
import com.example.surimusakotlin.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}