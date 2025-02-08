package com.prueba.tinderswipe.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Characters(
    val items: List<DragonBallCharacter>,
    val links: Links,
    val meta: Meta
)