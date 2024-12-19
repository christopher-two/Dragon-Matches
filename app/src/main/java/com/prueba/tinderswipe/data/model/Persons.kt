package com.prueba.tinderswipe.data.model

import androidx.annotation.DrawableRes

data class Persons(
    @DrawableRes val image: Int,
    val name: String,
    val age: Int,
)
