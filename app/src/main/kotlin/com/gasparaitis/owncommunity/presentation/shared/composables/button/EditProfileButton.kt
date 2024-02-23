package com.gasparaitis.owncommunity.presentation.shared.composables.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import com.gasparaitis.owncommunity.R
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors
import com.gasparaitis.owncommunity.presentation.utils.theme.TextStyles

@Composable
fun EditProfileButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        modifier =
            Modifier
                .then(modifier),
        onClick = onClick,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Colors.DarkBlack,
                contentColor = Colors.PureWhite,
            ),
        contentPadding = PaddingValues(),
        border = BorderStroke(width = 1.dp, color = Colors.LightGray),
    ) {
        ButtonText(
            text = stringResource(R.string.edit_profile_button_title),
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
private fun EditProfileButtonPreview() {
    Row {
        EditProfileButton(
            modifier = Modifier.size(width = 138.dp, height = 36.dp),
        )
    }
}
