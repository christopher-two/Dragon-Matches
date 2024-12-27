package com.prueba.tinderswipe

import android.app.Application
import com.prueba.tinderswipe.di.appModule
import com.prueba.tinderswipe.di.viewModelModule
import com.prueba.tinderswipe.utils.ContextProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        ContextProvider.initialize(this@MainApplication.applicationContext)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(appModule, viewModelModule)
        }
    }
}