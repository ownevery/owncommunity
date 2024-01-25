package com.gasparaitis.owncommunity.presentation.shared.composables.search

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.gasparaitis.owncommunity.presentation.utils.theme.Colors

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onTextFieldClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: (@Composable () -> Unit)? = null,
    cursorBrush: SolidColor = SolidColor(MaterialTheme.colorScheme.primary),
    textStyle: TextStyle = LocalTextStyle.current,
    backgroundColor: Color = Colors.Gray,
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        cursorBrush = cursorBrush,
        textStyle = textStyle,
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            Row(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .background(backgroundColor),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Box(
                    modifier = Modifier.weight(1f),
                ) {
                    if (value.isEmpty()) placeholderText?.invoke()
                    innerTextField()
                }
                if (trailingIcon != null) {
                    trailingIcon()
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        },
    )
    LaunchedEffect(interactionSource.collectIsPressedAsState()) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Release) {
                onTextFieldClick()
            }
        }
    }
}
