package com.gasparaiciukas.owncommunity.presentation.shared.composables.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.gasparaiciukas.owncommunity.R
import com.gasparaiciukas.owncommunity.presentation.utils.modifier.noRippleClickable
import com.gasparaiciukas.owncommunity.presentation.utils.theme.Colors

@Composable
fun ChatIconButton(
    modifier: Modifier = Modifier,
    isBadgeEnabled: Boolean = false,
    onClick: () -> Unit = {},
) {
    Box(
        modifier =
            Modifier
                .then(modifier)
                .noRippleClickable(onClick),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_message),
            tint = Colors.PureWhite,
            contentDescription = "Favorite",
        )
        if (!isBadgeEnabled) return@Box
        Icon(
            modifier =
                Modifier
                    .size(10.dp)
                    .zIndex(1f)
                    .align(Alignment.TopEnd),
            painter = painterResource(id = R.drawable.ic_alert_badge),
            tint = Color.Unspecified,
            contentDescription = "Alert badge",
        )
    }
}
