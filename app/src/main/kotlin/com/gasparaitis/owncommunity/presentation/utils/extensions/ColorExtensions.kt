package com.gasparaitis.owncommunity.presentation.utils.extensions

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

fun @receiver:ColorInt Int.darken(ratio: Float = 0.2f): Int =
    ColorUtils.blendARGB(this, Color.BLACK, ratio)
