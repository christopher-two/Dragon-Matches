package com.prueba.tinderswipe.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prueba.tinderswipe.R

@Composable
fun HomeScreen() = Screen()

@Composable
private fun Screen() {
    Scaffold(
        content = { paddingValues -> Content(paddingValues) },
        bottomBar = { BottomBar() },
        topBar = { TopBar() },
        containerColor = Color(0xFFF5F5F5)
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
        containerColor = Color.Transparent,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomIcon(
                icon = R.drawable.baseline_refresh_24,
                contentDescription = "Refresh",
                color = Color.Yellow,
                modifier = Modifier.size(24.dp),
                onClick = { /*TODO*/ }
            )

            BottomIcon(
                icon = R.drawable.baseline_close_24,
                contentDescription = "Close",
                color = Color.Red,
                modifier = Modifier.size(32.dp),
                onClick = { /*TODO*/ }
            )

            BottomIcon(
                icon = R.drawable.baseline_star_24,
                contentDescription = "Star",
                color = Color.Cyan,
                modifier = Modifier.size(24.dp),
                onClick = { /*TODO*/ }
            )

            BottomIcon(
                icon = R.drawable.baseline_favorite_24,
                contentDescription = "Heart",
                color = Color.Green,
                modifier = Modifier.size(32.dp),
                onClick = { /*TODO*/ }
            )

            BottomIcon(
                icon = R.drawable.baseline_bolt_24,
                contentDescription = "Bolt",
                color = Color.Magenta,
                modifier = Modifier.size(24.dp),
                onClick = { /*TODO*/ }
            )
        }
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
        modifier = Modifier.background(color = Color.White, shape = CircleShape)
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
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card()
    }
}

@Composable
private fun Card(
    @DrawableRes image: Int = R.drawable.men1,
    name: String = "john Doe",
    age: Int = 25,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(
                        elevation = 15.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    text = age.toString(),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() = HomeScreen()