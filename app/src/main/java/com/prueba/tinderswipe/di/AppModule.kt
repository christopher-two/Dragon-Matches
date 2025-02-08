package com.prueba.tinderswipe.di

import com.prueba.tinderswipe.network.ktorClient
import com.prueba.tinderswipe.utils.ContextProvider
import io.ktor.client.HttpClient
import org.koin.dsl.module

val appModule = module {
    single { ContextProvider.getContext() }
    single<HttpClient> { ktorClient }
}