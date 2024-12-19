package com.prueba.tinderswipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.prueba.tinderswipe.ui.screens.home.HomeScreen
import com.prueba.tinderswipe.ui.theme.TinderSwipeTheme
import com.prueba.tinderswipe.utils.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkMode by remember {
                mutableStateOf(
                    this.getSharedPreferences(
                        Constants.DARK_MODE_KEY,
                        MODE_PRIVATE
                    ).getBoolean(Constants.DARK_MODE_KEY, false)
                )
            }
            TinderSwipeTheme(
                darkTheme = darkMode
            ) {
                HomeScreen(
                    onSwitchTheme = {
                        darkMode = !darkMode
                    }
                )
            }
        }
    }
}