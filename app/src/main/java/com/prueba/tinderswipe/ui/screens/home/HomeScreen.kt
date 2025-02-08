package com.prueba.tinderswipe.ui.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import coil3.compose.rememberAsyncImagePainter
import com.prueba.tinderswipe.R
import com.prueba.tinderswipe.network.model.DragonBallCharacter
import com.prueba.tinderswipe.ui.components.Card
import com.prueba.tinderswipe.utils.performSwipeAnimation
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    onSwitchTheme: () -> Unit
) = Screen(
    onSwitchTheme = onSwitchTheme
)

@Composable
private fun Screen(
    viewModel: HomeViewModel = koinViewModel(),
    onSwitchTheme: () -> Unit,
) {
    val swipeOffset = remember { Animatable(0f) }
    val density = LocalDensity.current
    val screenWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val state by viewModel.state.collectAsState()
    Scaffold(
        containerColor = colorScheme.background,
        content = { paddingValues ->
            Content(
                paddingValues,
                state,
                viewModel,
                onSwipeLeft = { viewModel.update { copy(currentIndex = currentIndex + 1) } },
                onSwipeRight = { viewModel.update { copy(currentIndex = currentIndex + 1) } },
                swipeOffset = swipeOffset,
                screenWidthPx = screenWidthPx
            )
        },
        bottomBar = {
            BottomBar(
                onSwipeLeft = { viewModel.update { copy(currentIndex = currentIndex + 1) } },
                onSwipeRight = { viewModel.update { copy(currentIndex = currentIndex + 1) } },
                swipeOffset = swipeOffset,
                screenWidthPx = screenWidthPx,
                state = state
            )
        },
        topBar = {
            TopBar(
                homeViewModel = viewModel,
                state = state,
                onSwitchTheme = onSwitchTheme
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    homeViewModel: HomeViewModel,
    state: HomeState,
    onSwitchTheme: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        actions = {
            IconButton(
                onClick = {
                    onSwitchTheme()
                    homeViewModel.switchTheme()
                }
            ) {
                Icon(
                    painter = painterResource(
                        if (!state.darkMode) R.drawable.baseline_sunny_24 else R.drawable.baseline_mode_night_24
                    ),
                    contentDescription = "DarkMode",
                    modifier = Modifier.size(30.dp),
                    tint = if (!state.darkMode) Color.Black else Color.White
                )
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
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
fun BottomBar(
    swipeOffset: Animatable<Float, AnimationVector1D>,
    screenWidthPx: Float,
    state: HomeState,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val color = if (!state.darkMode) Color.Black else Color.White
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
                color = color,
                modifier = Modifier.size(40.dp),
                onClick = { /*TODO*/ }
            )

            BottomIcon(
                icon = R.drawable.baseline_close_24,
                contentDescription = "Close",
                color = color,
                modifier = Modifier.size(60.dp),
                onClick = {
                    coroutineScope.launch {
                        performSwipeAnimation(
                            swipeOffset = swipeOffset,
                            direction = -1f,
                            screenWidthPx = screenWidthPx,
                            onSwipeAction = onSwipeLeft
                        )
                    }
                }
            )

            BottomIcon(
                icon = R.drawable.baseline_star_24,
                contentDescription = "Star",
                color = color,
                modifier = Modifier.size(40.dp),
                onClick = { /*TODO*/ }
            )

            BottomIcon(
                icon = R.drawable.baseline_favorite_24,
                contentDescription = "Heart",
                color = color,
                modifier = Modifier.size(60.dp),
                onClick = {
                    coroutineScope.launch {
                        performSwipeAnimation(
                            swipeOffset = swipeOffset,
                            direction = 1f,
                            screenWidthPx = screenWidthPx,
                            onSwipeAction = onSwipeRight
                        )
                    }
                }
            )

            BottomIcon(
                icon = R.drawable.baseline_bolt_24,
                contentDescription = "Bolt",
                color = color,
                modifier = Modifier.size(40.dp),
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
    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = CircleShape
            )
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            modifier = modifier.padding(2.dp),
            tint = colorScheme.background,
        )
    }
}

@Composable
private fun SwappableCard(
    dragonBallCharacter: DragonBallCharacter,
    nextDragonBallCharacter: DragonBallCharacter? = null,
    state: HomeState,
    viewModel: HomeViewModel,
    swipeOffset: Animatable<Float, AnimationVector1D>,
    screenWidthPx: Float,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (nextDragonBallCharacter != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Card(
                    image = rememberAsyncImagePainter(nextDragonBallCharacter.image),
                    character = nextDragonBallCharacter
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
                        1f - (abs(swipeOffset.value) / screenWidthPx / 1.5f).coerceIn(
                            0f,
                            1f
                        )
                }
        ) {
            Card(
                showLike = state.showLike,
                showDislike = state.showDislike,
                image = rememberAsyncImagePainter(dragonBallCharacter.image),
                character = dragonBallCharacter
            )
        }
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    state: HomeState,
    viewModel: HomeViewModel,
    swipeOffset: Animatable<Float, AnimationVector1D>,
    screenWidthPx: Float,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoaded) {
            CircularProgressIndicator()
        } else if (state.currentIndex < (state.character?.size ?: 0)) {
            val currentCard = state.character?.getOrNull(state.currentIndex)
            val nextPerson = state.character?.getOrNull(state.currentIndex + 1)

            currentCard?.let {
                SwappableCard(
                    dragonBallCharacter = it,
                    nextDragonBallCharacter = nextPerson,
                    state = state,
                    viewModel = viewModel,
                    swipeOffset = swipeOffset,
                    screenWidthPx = screenWidthPx,
                    onSwipeLeft = { onSwipeLeft() },
                    onSwipeRight = { onSwipeRight() },
                )
            }
        } else {
            Text(
                text = "No hay más personas.",
                color = if (!state.darkMode) Color.Black else Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
@Preview
private fun Preview() = HomeScreen {}