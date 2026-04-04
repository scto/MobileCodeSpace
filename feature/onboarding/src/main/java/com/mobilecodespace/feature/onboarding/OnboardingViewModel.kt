package com.mobilecodespace.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<OnboardingUiState>(OnboardingUiState.Permissions)
    val uiState: StateFlow<OnboardingUiState> = _uiState

    fun requestPermissions() {
        // In einer echten Implementierung würde hier die Berechtigungsanfrage gestartet.
        // Wir simulieren den Erfolg und gehen zum Download über.
        _uiState.value = OnboardingUiState.Downloading
    }

    fun startDownload() {
        viewModelScope.launch {
            _uiState.value = OnboardingUiState.Installing
            // Simulation des Downloads und der Installation
            kotlinx.coroutines.delay(2000)
            completeSetup()
        }
    }

    private fun completeSetup() {
        viewModelScope.launch {
            dataStoreManager.setSetupComplete(true)
            _uiState.value = OnboardingUiState.Completed
        }
    }
}

sealed class OnboardingUiState {
    object Permissions : OnboardingUiState()
    object Downloading : OnboardingUiState()
    object Installing : OnboardingUiState()
    object Completed : OnboardingUiState()
}
