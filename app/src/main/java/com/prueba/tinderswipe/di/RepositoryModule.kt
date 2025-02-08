package com.prueba.tinderswipe.di

import com.prueba.tinderswipe.network.repository.DragonBallRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val RepositoryModule = module {
    factoryOf(::DragonBallRepository)
}