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
                    // 1. Installation von PRoot
                    prootManager.installProotBinary()
                    
                    // 2. Setup des Rootfs
                    prootManager.setupRootfs()
                    
                    // 3. TODO: Download & Extraktion von OpenJDK
                    // Hier würde die Logik für den Download und die Installation von OpenJDK folgen
                    
                    // 4. TODO: Download & Extraktion von Android Build-Tools
                    // Hier würde die Logik für den Download und die Installation der Build-Tools folgen
                    
                    completeSetup()
                } catch (e: Exception) {
                    e.printStackTrace()
                    _uiState.value = OnboardingUiState.Error(e.message ?: "Ein unbekannter Fehler ist aufgetreten.")
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
    data class Error(val message: String) : OnboardingUiState()
}
