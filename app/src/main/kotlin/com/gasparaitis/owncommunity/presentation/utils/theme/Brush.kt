package com.gasparaitis.owncommunity.presentation.utils.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

@Composable
fun slightBottomDarkGradientBrush(): Brush = Brush.verticalGradient(
    0f to Colors.Transparent,
    0.8f to Colors.PureBlack.copy(alpha = 0.6f),
    0.9f to Colors.PureBlack.copy(alpha = 0.9f),
)

@Composable
fun slightTopDarkGradientBrush(): Brush = Brush.verticalGradient(
    0f to Colors.PureBlack.copy(alpha = 1f),
    0.2f to Colors.Transparent,
    1f to Colors.Transparent,
)

@Composable
fun defaultGradientBrush() = Brush.horizontalGradient(listOf(Colors.Gradient1, Colors.Gradient2))
