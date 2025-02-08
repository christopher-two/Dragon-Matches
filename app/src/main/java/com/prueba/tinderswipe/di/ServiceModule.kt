package com.prueba.tinderswipe.di

import com.prueba.tinderswipe.network.service.DragonBallService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val ServiceModule = module {
    singleOf(::DragonBallService)
}