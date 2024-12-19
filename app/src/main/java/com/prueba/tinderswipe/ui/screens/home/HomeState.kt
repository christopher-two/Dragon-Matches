package com.prueba.tinderswipe.ui.screens.home

import com.prueba.tinderswipe.R
import com.prueba.tinderswipe.data.model.Persons

data class HomeState(
    var persons: List<Persons> = mutableListOf(
        Persons(
            image = R.drawable.men1,
            name = "John Doe",
            age = 25
        ),
        Persons(
            image = R.drawable.men2,
            name = "Jane Smith",
            age = 30
        ),
        Persons(
            image = R.drawable.women1,
            name = "Bob Johnson",
            age = 35
        ),
        Persons(
            image = R.drawable.women2,
            name = "Alice Brown",
            age = 28
        ),
    ),
    var showLike: Boolean = false,
    var showDislike: Boolean = false,
    var darkMode: Boolean = false
)
