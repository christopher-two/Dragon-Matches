package com.prueba.tinderswipe.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Transformation(
    val deletedAt: String?,
    val id: Int,
    val image: String,
    val ki: String,
    val name: String
)