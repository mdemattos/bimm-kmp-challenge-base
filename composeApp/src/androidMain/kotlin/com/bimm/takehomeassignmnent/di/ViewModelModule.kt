package com.bimm.takehomeassignmnent.di

import com.bimm.takehomeassignmnent.presentation.viewmodel.SakeShopDetailViewModel
import com.bimm.takehomeassignmnent.presentation.viewmodel.SakeShopListViewModel
import org.koin.core.module.dsl.*
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SakeShopListViewModel(get()) }
    viewModel { SakeShopDetailViewModel(get()) }
}