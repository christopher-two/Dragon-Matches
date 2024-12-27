package com.prueba.tinderswipe.di

import com.prueba.tinderswipe.utils.ContextProvider
import org.koin.dsl.module

val appModule = module {
    single { ContextProvider.getContext() }
}