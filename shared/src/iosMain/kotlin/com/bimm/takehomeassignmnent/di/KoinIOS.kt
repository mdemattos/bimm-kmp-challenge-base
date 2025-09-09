package com.bimm.takehomeassignmnent.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoinIos(appDeclaration: KoinAppDeclaration = {}): org.koin.core.KoinApplication =
    startKoin {
        appDeclaration()
        modules(
            sharedModule,
            platformModule
        )
    }

@Suppress("unused")
fun doInitKoin(): org.koin.core.KoinApplication = initKoinIos()