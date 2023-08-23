package com.gasparaitis.owncommunity.presentation.utils.modifier

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import com.gasparaitis.owncommunity.presentation.utils.extensions.offsetForPage
import kotlin.math.absoluteValue
import kotlin.math.min

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.pagerCubeTransition(
    pagerState: PagerState,
    index: Int
) = then(
    graphicsLayer {
        val pageOffset = pagerState.offsetForPage(index)
        val offScreenRight = pageOffset < 0f
        val deg = 105f
        val interpolated = FastOutLinearInEasing.transform(pageOffset.absoluteValue)
        rotationY = min(interpolated * if (offScreenRight) deg else -deg, 90f)
        transformOrigin = TransformOrigin(
            pivotFractionX = if (offScreenRight) 0f else 1f,
            pivotFractionY = .5f
        )
    }
)
