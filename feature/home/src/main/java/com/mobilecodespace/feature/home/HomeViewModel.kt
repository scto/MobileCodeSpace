package com.mobilecodespace.feature.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    
    private val _recentProjects = MutableStateFlow<List<Project>>(emptyList())
    val recentProjects: StateFlow<List<Project>> = _recentProjects

    init {
        // Hier würde die Logik zum Laden der Projekte aus dem Dateisystem folgen
        // Beispiel-Daten für die UI-Entwicklung
        _recentProjects.value = listOf(
            Project("MyAndroidApp", "/storage/emulated/0/Projects/MyAndroidApp", "2024-05-20 10:00"),
            Project("ComposeLibrary", "/storage/emulated/0/Projects/ComposeLibrary", "2024-05-19 15:30")
        )
    }
}

data class Project(val name: String, val path: String, val lastUpdated: String)
