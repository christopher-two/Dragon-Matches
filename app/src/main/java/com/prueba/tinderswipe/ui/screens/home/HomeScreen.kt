package com.prueba.tinderswipe.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prueba.tinderswipe.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun HomeScreen() = Screen()

@Composable
private fun Screen() {
    Scaffold(
        content = { paddingValues -> Content(paddingValues) },
        bottomBar = { BottomBar() },
        topBar = { TopBar() },
        containerColor = Color(0xFF000000)
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tinder_2),
                    contentDescription = "Logo",
                    modifier = Modifier.size(32.dp)
                )
            }
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
                modifier = Modifier.size(30.dp),
                onClick = { /*TODO*/ }
            )

            BottomIcon(
                icon = R.drawable.baseline_close_24,
                contentDescription = "Close",
                color = Color.Red,
                modifier = Modifier.size(60.dp),
                onClick = { /*TODO*/ }
            )

            BottomIcon(
                icon = R.drawable.baseline_star_24,
                contentDescription = "Star",
                color = Color.Cyan,
                modifier = Modifier.size(30.dp),
                onClick = { /*TODO*/ }
            )

            BottomIcon(
                icon = R.drawable.baseline_favorite_24,
                contentDescription = "Heart",
                color = Color.Green,
                modifier = Modifier.size(60.dp),
                onClick = { /*TODO*/ }
            )

            BottomIcon(
                icon = R.drawable.baseline_bolt_24,
                contentDescription = "Bolt",
                color = Color.Magenta,
                modifier = Modifier.size(30.dp),
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
        onClick = onClick,
        modifier = Modifier.background(color = Color(0x00FFFFFF), shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            modifier = modifier.padding(2.dp),
            tint = color,
        )
    }
}

data class Persons(
    @DrawableRes val image: Int,
    val name: String,
    val age: Int,
)

@Composable
private fun SwappableCard(
    person: Persons,
    nextPerson: Persons? = null,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
) {
    val swipeOffset = remember { Animatable(0f) }
    val density = LocalDensity.current
    val screenWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val coroutineScope = rememberCoroutineScope()

    var showLike by remember { mutableStateOf(false) }
    var showDislike by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (nextPerson != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Card(
                    image = nextPerson.image,
                    name = nextPerson.name,
                    age = nextPerson.age,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .offset {
                    IntOffset(
                        swipeOffset.value.roundToInt(),
                        0
                    )
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            coroutineScope.launch {
                                val newOffset = swipeOffset.value + dragAmount
                                swipeOffset.snapTo(newOffset)

                                val partialThreshold = screenWidthPx / 9

                                // Mostrar íconos según el desplazamiento
                                showLike = newOffset > partialThreshold
                                showDislike = newOffset < -partialThreshold

                                swipeOffset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            coroutineScope.launch {
                                when {
                                    swipeOffset.value > screenWidthPx / 3 -> {
                                        swipeOffset.animateTo(screenWidthPx)
                                        onSwipeRight()
                                        swipeOffset.snapTo(0f)
                                    }

                                    swipeOffset.value < -screenWidthPx / 3 -> {
                                        swipeOffset.animateTo(-screenWidthPx)
                                        onSwipeLeft()
                                        swipeOffset.snapTo(0f)
                                    }

                                    else -> {
                                        swipeOffset.animateTo(0f)
                                    }
                                }
                                // Ocultar íconos al finalizar el gesto
                                showLike = false
                                showDislike = false
                            }
                        },
                    )
                }
                .graphicsLayer {
                    rotationZ = (swipeOffset.value / 80f).coerceIn(-10f, 10f)
                    alpha =
                        1f - (kotlin.math.abs(swipeOffset.value) / screenWidthPx / 1.5f).coerceIn(
                            0f,
                            1f
                        )
                }
        ) {
            Card(
                image = person.image,
                name = person.name,
                age = person.age,
                showLike = showLike,
                showDislike = showDislike,
            )
        }

        // Mostrar el ícono del corazón
        if (showLike) {
            Icon(
                painter = painterResource(R.drawable.baseline_favorite_24), // Reemplaza con tu recurso de corazón
                contentDescription = "Like",
                tint = Color.Red,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            )
        }

        // Mostrar el ícono de la cruz
        if (showDislike) {
            Icon(
                painter = painterResource(R.drawable.baseline_close_24), // Reemplaza con tu recurso de cruz
                contentDescription = "Dislike",
                tint = Color.Gray,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun Content(paddingValues: PaddingValues) {
    val cards = listOf(
        Persons(R.drawable.men1, "John Doe", 25),
        Persons(R.drawable.men2, "Jane Smith", 30),
        Persons(R.drawable.women1, "Alice Brown", 40),
        Persons(R.drawable.women2, "Bob Johnson", 35),
        Persons(R.drawable.men1, "John Doe", 25),
        Persons(R.drawable.men2, "Jane Smith", 30),
        Persons(R.drawable.women1, "Alice Brown", 40),
        Persons(R.drawable.women2, "Bob Johnson", 35),
        Persons(R.drawable.men1, "John Doe", 25),
        Persons(R.drawable.men2, "Jane Smith", 30),
        Persons(R.drawable.women1, "Alice Brown", 40),
        Persons(R.drawable.women2, "Bob Johnson", 35),
        Persons(R.drawable.men1, "John Doe", 25),
        Persons(R.drawable.men2, "Jane Smith", 30),
        Persons(R.drawable.women1, "Alice Brown", 40),
        Persons(R.drawable.women2, "Bob Johnson", 35),
        Persons(R.drawable.men1, "John Doe", 25),
        Persons(R.drawable.men2, "Jane Smith", 30),
        Persons(R.drawable.women1, "Alice Brown", 40),
        Persons(R.drawable.women2, "Bob Johnson", 35),
        Persons(R.drawable.men1, "John Doe", 25),
        Persons(R.drawable.men2, "Jane Smith", 30),
        Persons(R.drawable.women1, "Alice Brown", 40),
        Persons(R.drawable.women2, "Bob Johnson", 35)
    )

    var currentIndex by remember { mutableIntStateOf(0) }
    var like by remember { mutableStateOf(false) }
    var dislike by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        if (currentIndex < cards.size) {
            val currentCard = cards[currentIndex]
            val nextPerson = cards.getOrNull(currentIndex + 1)

            SwappableCard(
                person = currentCard,
                nextPerson = nextPerson,
                onSwipeLeft = {
                    currentIndex += 1
                },
                onSwipeRight = {
                    currentIndex += 1
                },
            )
        } else {
            Text(
                text = "No hay más personas.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun Card(
    @DrawableRes image: Int = R.drawable.men1,
    name: String = "john Doe",
    age: Int = 25,
    showLike: Boolean = false,
    showDislike: Boolean = false,
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
                        elevation = 5.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentScale = ContentScale.Crop,
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