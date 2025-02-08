package com.prueba.tinderswipe.network.model

import kotlinx.serialization.Serializable

@Serializable
data class OriginPlanet(
    val deletedAt: String?,
    val description: String,
    val id: Int,
    val image: String,
    val isDestroyed: Boolean,
    val name: String
)