package com.gasparaiciukas.owncommunity.presentation.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (Lifecycle.Event) -> Unit,
) {
    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                onEvent(event)
            }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun OnStartEffect(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: () -> Unit,
) {
    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { source, event ->
                if (event == Lifecycle.Event.ON_START) {
                    onEvent()
                }
            }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
