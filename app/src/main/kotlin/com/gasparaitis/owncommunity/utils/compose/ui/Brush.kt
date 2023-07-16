package com.gasparaitis.owncommunity.utils.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import com.gasparaitis.owncommunity.utils.compose.ui.theme.Colors

@Composable
fun slightDarkGradientBrush(): Brush = Brush.verticalGradient(
    0f to Colors.Transparent,
    0.8f to Colors.PureBlack.copy(alpha = 0.6f),
    0.9f to Colors.PureBlack.copy(alpha = 0.9f),
)
