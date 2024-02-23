package com.gasparaitis.owncommunity.presentation.shared.composables.button

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.presentation.utils.modifier.noRippleClickable
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors

@Composable
fun BookmarkIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier =
            Modifier
                .then(modifier)
                .noRippleClickable(onClick),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_bookmark),
            tint = Colors.PureWhite,
            contentDescription = "Favorite",
        )
    }
}
