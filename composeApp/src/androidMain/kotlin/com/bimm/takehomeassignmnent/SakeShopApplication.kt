package com.bimm.takehomeassignmnent

import android.app.Application
import com.bimm.takehomeassignmnent.di.platformModule
import com.bimm.takehomeassignmnent.di.sharedModule
import com.bimm.takehomeassignmnent.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SakeShopApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidContext(this@SakeShopApplication)
            modules(
                sharedModule,
                platformModule,
                viewModelModule
            )
        }
    }
}