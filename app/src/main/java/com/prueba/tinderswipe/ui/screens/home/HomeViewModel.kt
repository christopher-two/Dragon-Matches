package com.prueba.tinderswipe.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.tinderswipe.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    data class Persons(
        @DrawableRes val image: Int,
        val name: String,
        val age: Int,
    )

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
    )

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun update(update: HomeState.() -> HomeState) {
        _state.value = _state.value.update()
    }
}