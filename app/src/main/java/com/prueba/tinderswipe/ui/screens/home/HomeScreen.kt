package com.prueba.tinderswipe.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prueba.tinderswipe.R

@Composable
fun HomeScreen() = Screen()

@Composable
private fun Screen() {
    Scaffold(
        content = { paddingValues -> Content(paddingValues) },
        bottomBar = { BottomBar() },
        topBar = { TopBar() },
        containerColor = Color.White
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            Image(
                painter = painterResource(id = R.drawable.tinder_2),
                contentDescription = "Logo",
                modifier = Modifier.size(32.dp)
            )
        }
    )
}

@Composable
fun BottomBar() {
    BottomAppBar(
        containerColor = Color.Transparent
    ) {
        BottomIcon(
            icon = R.drawable.baseline_refresh_24,
            contentDescription = "Heart",
            color = Color.Yellow,
            modifier = Modifier.weight(1f),
            onClick = { /*TODO*/ }
        )

        BottomIcon(
            icon = R.drawable.baseline_close_24,
            contentDescription = "Close",
            color = Color.Red,
            modifier = Modifier.weight(1f),
            onClick = { /*TODO*/ }
        )

        BottomIcon(
            icon = R.drawable.baseline_star_24,
            contentDescription = "Star",
            color = Color.Cyan,
            modifier = Modifier.weight(1f),
            onClick = { /*TODO*/ }
        )

        BottomIcon(
            icon = R.drawable.baseline_favorite_24,
            contentDescription = "Heart",
            color = Color.Green,
            modifier = Modifier.weight(1f),
            onClick = { /*TODO*/ }
        )

        BottomIcon(
            icon = R.drawable.baseline_bolt_24,
            contentDescription = "Bolt",
            color = Color.White,
            modifier = Modifier.weight(1f),
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
private fun BottomIcon(
    @DrawableRes icon: Int,
    contentDescription: String,
    color: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = { onClick() },
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            tint = color,
            modifier = modifier
        )
    }
}

@Composable
private fun Content(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
    }
}

@Composable
@Preview
private fun Preview() = HomeScreen()