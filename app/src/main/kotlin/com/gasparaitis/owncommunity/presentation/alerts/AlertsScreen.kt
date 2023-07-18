package com.gasparaitis.owncommunity.presentation.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AlertsScreen(
    navigator: DestinationsNavigator,
    viewModel: AlertsViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsState()
    AlertsScreenContent(
        state = state.value,
        onAction = viewModel::onAction,
    )
    LaunchedEffect(Unit) {
        viewModel.navEvent.collect {
            navigator.navigate(it.destination)
        }
    }
}

@Composable
private fun AlertsScreenContent(
    state: AlertsState,
    onAction: (AlertsAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
    }
}
