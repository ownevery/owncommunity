package com.gasparaitis.owncommunity.presentation.utils.modifier

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext

fun Modifier.storyPointerInput(
    onFirstHalfTap: () -> Unit,
    onSecondHalfTap: () -> Unit,
    onHold: (released: Boolean) -> Unit,
): Modifier = composed {
    val screenMiddle = LocalContext.current.resources.displayMetrics.widthPixels / 2
    var shouldRelease = false
    Modifier.then(
        pointerInput(Unit) {
            detectTapGestures(
                onTap = { offset ->
                    if (offset.x < screenMiddle) onFirstHalfTap() else onSecondHalfTap()
                },
                onPress = {
                    awaitRelease()
                    if (shouldRelease) {
                        onHold.invoke(true)
                        shouldRelease = false
                    }
                },
                onLongPress = {
                    onHold.invoke(false)
                    shouldRelease = true
                }
            )
        }
    )
}
