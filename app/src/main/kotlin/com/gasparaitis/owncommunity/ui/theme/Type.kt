package com.gasparaitis.owncommunity.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gasparaitis.owncommunity.R

val hkGrotesk = FontFamily(
    Font(R.font.hkgrotesk_medium, FontWeight.Medium),
    Font(R.font.hkgrotesk_semibold, FontWeight.SemiBold),
    Font(R.font.hkgrotesk_bold, FontWeight.Bold),
)

object FontSizes {
    val tertiary = 12.sp
    val secondary = 14.sp
    val body = 16.sp
    val title = 18.sp
}

object TextStyles {
    val title: TextStyle = TextStyle(
        color = Colors.PureWhite,
        fontFamily = hkGrotesk,
        fontSize = FontSizes.title,
        fontWeight = FontWeight.Bold,
    )
    val body: TextStyle = TextStyle(
        color = Colors.PureWhite,
        fontFamily = hkGrotesk,
        fontSize = FontSizes.body,
        fontWeight = FontWeight.Medium,
    )
    val secondary: TextStyle = TextStyle(
        color = Colors.PureWhite,
        fontFamily = hkGrotesk,
        fontSize = FontSizes.secondary,
        fontWeight = FontWeight.Medium,
    )
    val tertiary: TextStyle = TextStyle(
        color = Colors.PureWhite,
        fontFamily = hkGrotesk,
        fontSize = FontSizes.tertiary,
        fontWeight = FontWeight.Medium,
    )
}

