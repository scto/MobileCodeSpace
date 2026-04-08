package com.mobilecodespace.feature.onboarding

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecodespace.app.MCSConstants
import com.mobilecodespace.core.utils.ArchiveUtils
import com.mobilecodespace.core.utils.Environment
import com.mobilecodespace.core.utils.FileUtils
import com.mobilecodespace.core.utils.PermissionsUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val application: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow<OnboardingUiState>(OnboardingUiState.PermissionsRequired)
    val uiState: StateFlow<OnboardingUiState> = _uiState

    fun checkPermissions(context: Context) {
        if (PermissionsUtils.hasStoragePermission(context)) {
            _uiState.value = OnboardingUiState.Downloading
            startDownload()
        } else {
            _uiState.value = OnboardingUiState.PermissionsRequired
        }
    }

    fun startDownload() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val is64 = FileUtils.isAarch64()

                    // 1. Download & Extraktion von Cmdline Tools
                    downloadAndExtract(MCSConstants.CMDLINE_TOOLS_URL, Environment.BIN_DIR.absolutePath, "Installiere Tools", 25)

                    // 2. Download & Extraktion von Scripts
                    downloadAndExtract(MCSConstants.SCRIPTS_URL, Environment.SCRIPTS.absolutePath, "Installiere Skripte", 50)

                    // 3. Download & Extraktion von Ubuntu Distro
                    val ubuntuUrl = if (is64) MCSConstants.UBUNTU_ARM64_URL else MCSConstants.UBUNTU_ARMHF_URL
                    downloadAndExtract(ubuntuUrl, Environment.ROOTFS.absolutePath, "Installiere Ubuntu", 75)

                    // 4. Download & Extraktion von Bootstrap
                    val bootstrapUrl = if (is64) MCSConstants.BOOTSTRAP_AARCH64_URL else MCSConstants.BOOTSTRAP_ARM_URL
                    downloadAndExtract(bootstrapUrl, Environment.HOME.absolutePath, "Installiere Bootstrap", 95)
                    
                    completeSetup()
                } catch (e: Exception) {
                    e.printStackTrace()
                    _uiState.value = OnboardingUiState.Error(e.message ?: "Ein unbekannter Fehler ist aufgetreten.")
                }
            }
        }
    }

    private suspend fun downloadAndExtract(url: String, targetDir: String, message: String, progress: Int) {
        _uiState.value = OnboardingUiState.Progress(message, progress)
        val tempFile = File(application.cacheDir, "download_temp.zip")
        try {
            withContext(Dispatchers.IO) {
                URL(url).openStream().use { input ->
                    FileOutputStream(tempFile).use { output ->
                        input.copyTo(output)
                    }
                }
            }
            
            if (!ArchiveUtils.extract(tempFile.absolutePath, targetDir)) {
                throw Exception("Fehler beim Entpacken von $url")
            }
        } finally {
            if (tempFile.exists()) tempFile.delete()
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
    object PermissionsRequired : OnboardingUiState()
    object Downloading : OnboardingUiState()
    data class Progress(val message: String, val percent: Int) : OnboardingUiState()
    object Completed : OnboardingUiState()
    data class Error(val message: String) : OnboardingUiState()
}
