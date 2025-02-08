package com.prueba.tinderswipe.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val ktorClient = HttpClient {
    install(ContentNegotiation) {
        json(
            json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
        )
    }
}