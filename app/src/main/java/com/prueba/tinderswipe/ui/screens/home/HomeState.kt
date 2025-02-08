package com.prueba.tinderswipe.ui.screens.home

import com.prueba.tinderswipe.network.model.DragonBallCharacter

data class HomeState(
    var character: List<DragonBallCharacter>? = null,
    var showLike: Boolean = false,
    var showDislike: Boolean = false,
    var darkMode: Boolean = false,
    var currentIndex: Int = 0,
    var isLoaded: Boolean = false,
)
