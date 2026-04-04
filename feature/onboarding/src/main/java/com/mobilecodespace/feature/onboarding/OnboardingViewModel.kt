package com.mobilecodespace.feature.onboarding

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecodespace.core.data.proot.PRootManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val application: Application
) : ViewModel() {

    private val prootManager = PRootManager(application)
    private val _uiState = MutableStateFlow<OnboardingUiState>(OnboardingUiState.Permissions)
    val uiState: StateFlow<OnboardingUiState> = _uiState

    fun requestPermissions() {
        // Berechtigungen wurden erteilt, weiter zum Download
        _uiState.value = OnboardingUiState.Downloading
    }

    fun startDownload() {
        viewModelScope.launch {
            _uiState.value = OnboardingUiState.Installing
            withContext(Dispatchers.IO) {
                try {
                    // Installation von PRoot und Setup des Rootfs
                    prootManager.installProotBinary()
                    prootManager.setupRootfs()
                    completeSetup()
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Fehlerbehandlung könnte hier ergänzt werden
                }
            }
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
