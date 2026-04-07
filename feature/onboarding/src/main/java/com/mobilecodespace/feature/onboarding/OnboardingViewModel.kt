package com.mobilecodespace.feature.onboarding

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecodespace.core.utils.ArchiveUtils
import com.mobilecodespace.core.utils.Environment
import com.mobilecodespace.core.utils.FileUtils
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

    private val _uiState = MutableStateFlow<OnboardingUiState>(OnboardingUiState.Permissions)
    val uiState: StateFlow<OnboardingUiState> = _uiState

    // Basis-URL für Downloads
    private val baseUrl = "https://github.com/scto/MobileCodeSpace-Packages/releases/download"

    fun requestPermissions() {
        _uiState.value = OnboardingUiState.Downloading
        startDownload()
    }

    private fun startDownload() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val is64 = FileUtils.isAarch64()

                    // 1. Download & Extraktion von Cmdline Tools
                    downloadAndExtract("$baseUrl/cmdline/cmdline.zip", Environment.BIN_DIR.absolutePath, "Installiere Tools", 25)

                    // 2. Download & Extraktion von Scripts
                    downloadAndExtract("$baseUrl/scripts/scripts.zip", Environment.SCRIPTS.absolutePath, "Installiere Skripte", 50)

                    // 3. Download & Extraktion von Ubuntu Distro
                    val ubuntuUrl = if (is64) "$baseUrl/ubuntu/ubuntu-arm64.tar.gz" else "$baseUrl/ubuntu/ubuntu-armhf.tar.gz"
                    downloadAndExtract(ubuntuUrl, Environment.ROOTFS.absolutePath, "Installiere Ubuntu", 75)

                    // 4. Download & Extraktion von Bootstrap
                    val bootstrapUrl = if (is64) "$baseUrl/bootstrap/bootstrap-aarch64.zip" else "$baseUrl/bootstrap/bootstrap-arm.zip"
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
    object Permissions : OnboardingUiState()
    object Downloading : OnboardingUiState()
    data class Progress(val message: String, val percent: Int) : OnboardingUiState()
    object Completed : OnboardingUiState()
    data class Error(val message: String) : OnboardingUiState()
}
