package com.prueba.tinderswipe.ui.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prueba.tinderswipe.network.repository.DragonBallRepository
import com.prueba.tinderswipe.utils.Constants.DARK_MODE_KEY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class HomeViewModel(
    private val context: Context,
    private val dragonBallRepository: DragonBallRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun update(update: HomeState.() -> HomeState) {
        _state.value = _state.value.update()
    }

    init {
        loadCharacters()
        update {
            copy(
                darkMode = context.getSharedPreferences(
                    DARK_MODE_KEY,
                    Context.MODE_PRIVATE
                ).getBoolean(DARK_MODE_KEY, false)
            )
        }
    }

    fun switchTheme() {
        update { copy(darkMode = !darkMode) }
        context.getSharedPreferences(DARK_MODE_KEY, Context.MODE_PRIVATE).edit()
            .putBoolean(DARK_MODE_KEY, _state.value.darkMode).apply()
    }

    private fun loadCharacters() {
        update { copy(isLoaded = true) }
        viewModelScope.launch {
            try {
                val characters = dragonBallRepository.getCharacters() ?: return@launch
                update { copy(character = characters.items) }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "loadCharacters: ${e.message}")
            }finally {
                update { copy(isLoaded = false) }
            }
        }
    }
}