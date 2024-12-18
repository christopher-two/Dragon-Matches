package com.prueba.tinderswipe.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    data class Persons(
        @DrawableRes val image: Int,
        val name: String,
        val age: Int,
    )
    data class HomeState(
        val persons: List<Persons> = emptyList(),
    )

    private val _state = MutableLiveData(HomeState())
    val state: LiveData<HomeState> = _state

    fun update(update: HomeState.() -> HomeState){
        _state.value = _state.value?.update()
    }
}