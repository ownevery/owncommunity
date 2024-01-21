package com.gasparaitis.owncommunity.presentation.utils.extensions

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette

val Drawable.palette get() = Palette.from(this.toBitmap()).generate()

val Drawable.verticalBackgroundGradientBrush: Brush get() {
    val color = palette.getLightMutedColor(android.graphics.Color.BLACK)
    return Brush.verticalGradient(
        colors =
            listOf(
                Color(color),
                Color(color.darken(0.8f)),
            ),
    )
}
