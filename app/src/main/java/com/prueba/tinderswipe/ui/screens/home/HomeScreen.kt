package com.prueba.tinderswipe.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.prueba.tinderswipe.R
import com.prueba.tinderswipe.ui.components.Card
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun HomeScreen() = Screen()

@Composable
private fun Screen() {
    val viewModel = HomeViewModel()
    Scaffold(
        content = { paddingValues ->
            Content(
                paddingValues,
                viewModel.state.collectAsState().value,
                viewModel
            )
        },
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

@Composable
private fun SwappableCard(
    person: HomeViewModel.Persons,
    nextPerson: HomeViewModel.Persons? = null,
    state: HomeViewModel.HomeState,
    viewModel: HomeViewModel,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
) {
    val swipeOffset = remember { Animatable(0f) }
    val density = LocalDensity.current
    val screenWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val coroutineScope = rememberCoroutineScope()

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

                                viewModel.update {
                                    copy(
                                        showLike = newOffset > partialThreshold,
                                        showDislike = newOffset < -partialThreshold
                                    )
                                }
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
                                viewModel.update {
                                    copy(
                                        showLike = false,
                                        showDislike = false
                                    )
                                }
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
                showLike = state.showLike,
                showDislike = state.showDislike,
            )
        }
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    state: HomeViewModel.HomeState,
    viewModel: HomeViewModel
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        if (currentIndex < state.persons.size) {
            val currentCard = state.persons[currentIndex]
            val nextPerson = state.persons.getOrNull(currentIndex + 1)

            SwappableCard(
                person = currentCard,
                nextPerson = nextPerson,
                state = state,
                viewModel = viewModel,
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
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
@Preview
private fun Preview() = HomeScreen()