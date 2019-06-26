package com.sunil.kumar.ui

sealed class UiState {

    object Loading : UiState()
    object Error : UiState()
    object DataLoaded : UiState()
    object Empty : UiState()
}