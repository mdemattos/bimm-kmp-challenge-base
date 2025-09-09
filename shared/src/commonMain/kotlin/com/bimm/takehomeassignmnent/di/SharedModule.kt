package com.bimm.takehomeassignmnent.di

import com.bimm.takehomeassignmnent.data.repository.SakeShopRepositoryImpl
import com.bimm.takehomeassignmnent.data.source.LocalSakeShopDataSource
import com.bimm.takehomeassignmnent.data.source.SakeShopDataSource
import com.bimm.takehomeassignmnent.domain.repository.SakeShopRepository
import com.bimm.takehomeassignmnent.domain.usecase.GetSakeShopByIdUseCase
import com.bimm.takehomeassignmnent.domain.usecase.GetSakeShopsUseCase
import org.koin.dsl.module

val sharedModule = module {
    single<SakeShopDataSource> { LocalSakeShopDataSource(get()) }
    single<SakeShopRepository> { SakeShopRepositoryImpl(get()) }
    single { GetSakeShopsUseCase(get()) }
    single { GetSakeShopByIdUseCase(get()) }
}