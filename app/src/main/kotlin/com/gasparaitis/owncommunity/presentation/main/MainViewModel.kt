package com.gasparaitis.owncommunity.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _event = MutableSharedFlow<MainEvent>()
    val event = _event.asSharedFlow()

    init {
        onCreated()
    }

    private fun onCreated() {}

    fun onEvent(action: MainEvent) {
        when (action) {
            MainEvent.OnHomeIconRepeatClick -> onHomeIconRepeatClick()
        }
    }

    private fun onHomeIconRepeatClick() {
        viewModelScope.launch {
            _event.emit(MainEvent.OnHomeIconRepeatClick)
        }
    }
}
