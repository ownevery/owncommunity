package com.gasparaitis.owncommunity.presentation.shared.composables.story

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.gasparaitis.owncommunity.presentation.utils.modifier.noRippleClickable
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.gasparaitis.owncommunity.presentation.utils.theme.defaultGradientBrush

@Composable
fun StoryProfileImage(
    onClick: () -> Unit,
    image: Painter,
    shouldShowBorder: Boolean,
    modifier: Modifier = Modifier,
) {
    val gradientBorder =
        Modifier
            .border(width = 2.dp, brush = defaultGradientBrush(), shape = CircleShape)
    val blackBorder =
        Modifier
            .border(width = 2.dp, color = Colors.PureBlack, shape = CircleShape)
    Image(
        modifier =
            Modifier
                .padding(bottom = 8.dp)
                .then(if (shouldShowBorder) gradientBorder else Modifier)
                .padding(2.dp)
                .size(40.dp)
                .clip(CircleShape)
                .then(if (shouldShowBorder) blackBorder else Modifier)
                .then(modifier)
                .noRippleClickable(onClick),
        contentScale = ContentScale.Crop,
        painter = image,
        contentDescription = "Story profile image",
    )
}
