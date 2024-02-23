package com.gasparaiciukas.owncommunity.presentation.main.bottomnavigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class BottomNavigationViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(BottomNavigationState.EMPTY)
    val uiState = _uiState.asStateFlow()

    init {
        onCreated()
    }

    private fun onCreated() {}

    fun onEvent(action: BottomNavigationState.Event) {
        when (action) {
            BottomNavigationState.OnHomeIconRepeatClick -> onHomeIconRepeatClick()
        }
    }

    fun onEventHandled(event: BottomNavigationState.Event?) {
        _uiState.update { state ->
            if (state.event == event) {
                state.copy(
                    event = null,
                )
            } else {
                state
            }
        }
    }

    private fun onHomeIconRepeatClick() {
        _uiState.update { state ->
            state.copy(
                event = BottomNavigationState.OnHomeIconRepeatClick,
            )
        }
    }
}
