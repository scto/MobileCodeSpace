package com.mobilecodespace.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilecodespace.core.ui.components.MCSCard

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val projects by viewModel.recentProjects.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Open or create Your next project", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Android Code Studio", style = MaterialTheme.typography.bodyMedium)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Quick Actions
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                MCSCard(modifier = Modifier.weight(1f)) { Text("New Project", modifier = Modifier.padding(16.dp)) }
                MCSCard(modifier = Modifier.weight(1f)) { Text("Open", modifier = Modifier.padding(16.dp)) }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                MCSCard(modifier = Modifier.weight(1f)) { Text("SDK Manager", modifier = Modifier.padding(16.dp)) }
                MCSCard(modifier = Modifier.weight(1f)) { Text("Plugins", modifier = Modifier.padding(16.dp)) }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(text = "Recent Projects", style = MaterialTheme.typography.titleLarge)
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Recent Projects
        LazyColumn {
            items(projects) { project ->
                MCSCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = project.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = project.path, style = MaterialTheme.typography.bodySmall)
                        Text(text = "Updated: ${project.lastUpdated}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
