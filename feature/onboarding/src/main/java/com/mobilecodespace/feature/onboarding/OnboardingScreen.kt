package com.mobilecodespace.feature.onboarding

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mobilecodespace.core.ui.components.MCSButton
import com.mobilecodespace.core.ui.components.MCSProgressBar

@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Initialer Check
    LaunchedEffect(Unit) {
        viewModel.checkPermissions(context)
    }

    // Permission launcher für Standard-Berechtigungen (Legacy)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.checkPermissions(context)
            }
        }
    )

    // Launcher für MANAGE_EXTERNAL_STORAGE (Android 11+)
    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { _ ->
            viewModel.checkPermissions(context)
        }
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val state = uiState) {
            is OnboardingUiState.PermissionsRequired -> {
                Text("Berechtigungen für Dateizugriff erforderlich")
                Spacer(modifier = Modifier.height(16.dp))
                MCSButton(text = "Berechtigungen erteilen", onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        intent.data = Uri.parse("package:${context.packageName}")
                        storagePermissionLauncher.launch(intent)
                    } else {
                        permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                })
            }
            is OnboardingUiState.Downloading -> {
                Text("Bereit zum Download...")
                Spacer(modifier = Modifier.height(16.dp))
                MCSButton(text = "Start Download", onClick = { viewModel.startDownload() })
            }
            is OnboardingUiState.Progress -> {
                Text(state.message)
                Spacer(modifier = Modifier.height(16.dp))
                MCSProgressBar(progress = state.percent / 100f)
            }
            is OnboardingUiState.Completed -> {
                Text("Setup abgeschlossen!")
            }
            is OnboardingUiState.Error -> {
                Text("Fehler: ${state.message}")
                Spacer(modifier = Modifier.height(16.dp))
                MCSButton(text = "Erneut versuchen", onClick = { viewModel.checkPermissions(context) })
            }
        }
    }
}
