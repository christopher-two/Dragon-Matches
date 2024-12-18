package com.prueba.tinderswipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.prueba.tinderswipe.ui.screens.home.HomeScreen
import com.prueba.tinderswipe.ui.theme.TinderSwipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TinderSwipeTheme {
                HomeScreen()
            }
        }
    }
}