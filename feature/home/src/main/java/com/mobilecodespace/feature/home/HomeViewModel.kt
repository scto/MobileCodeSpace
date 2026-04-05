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
        // Mock-Daten für die Anzeige
        _recentProjects.value = listOf(
            Project("MobileCodeSpace", "/storage/emulated/0/Projects/MCS", "10 min ago"),
            Project("ComposeDemo", "/storage/emulated/0/Projects/ComposeDemo", "2 hours ago")
        )
    }
}

data class Project(val name: String, val path: String, val lastUpdated: String)
