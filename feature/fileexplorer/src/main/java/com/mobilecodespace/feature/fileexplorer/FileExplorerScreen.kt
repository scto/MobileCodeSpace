package com.mobilecodespace.feature.fileexplorer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class FileItem(val name: String, val isDirectory: Boolean, val children: List<FileItem> = emptyList())

@Composable
fun FileExplorerScreen(onFileClick: (String) -> Unit = {}) {
    // Mock data für die Baumansicht
    val rootItems = listOf(
        FileItem("src", true, listOf(FileItem("MainActivity.kt", false))),
        FileItem("build.gradle.kts", false),
        FileItem("README.md", false),
        FileItem(".gitignore", false)
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("File Explorer", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(rootItems) { item ->
                FileRow(item, onFileClick)
            }
        }
    }
}

@Composable
fun FileRow(item: FileItem, onFileClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { if (!item.isDirectory) onFileClick(item.name) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (item.isDirectory) "📁 ${item.name}" else "📄 ${item.name}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
