package com.bimm.takehomeassignmnent.di

import com.bimm.takehomeassignmnent.data.source.ResourceReader
import org.koin.dsl.module

val platformModule = module {
    single<ResourceReader> { ResourceReader() }
}