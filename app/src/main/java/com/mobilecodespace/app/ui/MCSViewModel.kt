package com.mobilecodespace.app.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MCSViewModel @Inject constructor() : ViewModel() {
    
    private val _isSetupComplete = MutableStateFlow(false)
    val isSetupComplete: StateFlow<Boolean> = _isSetupComplete

    init {
        checkSetupStatus()
    }

    private fun checkSetupStatus() {
        // TODO: Implementiere echte Prüfung:
        // 1. Prüfe MANAGE_EXTERNAL_STORAGE Permission
        // 2. Prüfe ob PRoot Binary existiert
        // 3. Prüfe ob Rootfs existiert
        // Für den Anfang setzen wir es auf false, um das Onboarding zu zeigen
        _isSetupComplete.value = false
    }
}
