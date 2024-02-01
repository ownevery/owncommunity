package com.gasparaitis.owncommunity.presentation.main.window

import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.gasparaitis.owncommunity.presentation.main.MainActivity
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors

fun MainActivity.setDefaultSystemBars() {
    WindowCompat.getInsetsController(window, window.decorView).apply {
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        window.statusBarColor = Colors.Transparent.toArgb()
        window.navigationBarColor = Colors.Transparent.toArgb()
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
    }
}
