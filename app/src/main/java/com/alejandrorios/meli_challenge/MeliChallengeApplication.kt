package com.alejandrorios.meli_challenge

import android.app.Application
import com.alejandrorios.meli_challenge.di.appModule
import com.alejandrorios.meli_challenge.di.dataModule
import com.alejandrorios.meli_challenge.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MeliChallengeApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MeliChallengeApplication)
            modules(appModule, domainModule, dataModule)
        }
    }
}
