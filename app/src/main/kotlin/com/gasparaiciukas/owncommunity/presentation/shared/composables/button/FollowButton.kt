package com.gasparaiciukas.owncommunity.presentation.shared.composables.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gasparaiciukas.owncommunity.R
import com.gasparaiciukas.owncommunity.presentation.utils.theme.Colors
import com.gasparaiciukas.owncommunity.presentation.utils.theme.TextStyles

@Composable
fun FollowButton(
    isFollowed: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = Modifier.then(modifier),
        onClick = onClick,
        colors =
            ButtonDefaults.buttonColors(
                containerColor =
                    if (isFollowed) {
                        Colors.DarkBlack
                    } else {
                        Colors.SocialPink
                    },
                contentColor = Colors.PureWhite,
            ),
        contentPadding = PaddingValues(),
        border =
            if (isFollowed) {
                BorderStroke(width = 1.dp, color = Colors.LightGray)
            } else {
                null
            },
    ) {
        ButtonText(
            text =
                if (isFollowed) {
                    stringResource(R.string.follow_button_follow)
                } else {
                    stringResource(R.string.follow_button_following)
                },
        )
    }
}

@Composable
private fun ButtonText(text: String) {
    Text(
        text = text,
        style =
            TextStyles.secondary.copy(
                color = Colors.PureWhite,
                fontWeight = FontWeight.Bold,
                lineHeight = 16.sp,
            ),
    )
}

@Preview
@Composable
private fun FollowButtonPreview() {
    Row {
        FollowButton(
            isFollowed = false,
        )
        FollowButton(
            isFollowed = true,
        )
    }
}
