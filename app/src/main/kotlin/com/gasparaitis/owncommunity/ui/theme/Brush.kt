package com.gasparaitis.owncommunity.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

@Composable
fun slightDarkGradientBrush(): Brush = Brush.verticalGradient(
    0f to Colors.Transparent,
    0.8f to Colors.PureBlack.copy(alpha = 0.6f),
    0.9f to Colors.PureBlack.copy(alpha = 0.9f),
)

@Composable
fun defaultGradientBrush() = Brush.horizontalGradient(listOf(Colors.Gradient1, Colors.Gradient2))
