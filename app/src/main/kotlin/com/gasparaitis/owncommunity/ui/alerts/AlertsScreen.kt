package com.gasparaitis.owncommunity.ui.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AlertsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Alerts Screen")
    }
}
