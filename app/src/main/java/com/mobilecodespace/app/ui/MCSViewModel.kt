package com.mobilecodespace.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecodespace.feature.onboarding.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MCSViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    
    private val _isSetupComplete = MutableStateFlow(false)
    val isSetupComplete: StateFlow<Boolean> = _isSetupComplete

    init {
        checkSetupStatus()
    }

    private fun checkSetupStatus() {
        viewModelScope.launch {
            dataStoreManager.isSetupComplete.collectLatest { complete ->
                _isSetupComplete.value = complete
            }
        }
    }
}
