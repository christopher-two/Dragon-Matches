package com.prueba.tinderswipe.network.model

import kotlinx.serialization.Serializable

@Serializable
data class DragonBallCharacter(
    val affiliation: String,
    val deletedAt: String?,
    val description: String,
    val gender: String,
    val id: Int,
    val image: String,
    val ki: String,
    val maxKi: String,
    val name: String,
    val race: String,
)