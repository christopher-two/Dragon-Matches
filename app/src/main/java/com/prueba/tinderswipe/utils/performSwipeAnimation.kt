package com.prueba.tinderswipe.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D

suspend fun performSwipeAnimation(
    swipeOffset: Animatable<Float, AnimationVector1D>,
    direction: Float,
    screenWidthPx: Float,
    onSwipeAction: () -> Unit
) {
    swipeOffset.animateTo(screenWidthPx * direction) // Realiza la animación
    onSwipeAction() // Ejecuta la acción asociada (e.g., swipe izquierdo o derecho)
    swipeOffset.snapTo(0f) // Resetea el estado de la tarjeta
}
