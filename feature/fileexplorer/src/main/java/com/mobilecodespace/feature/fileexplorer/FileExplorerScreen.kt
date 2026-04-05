package com.mobilecodespace.feature.fileexplorer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class FileItem(
    val name: String, 
    val isDirectory: Boolean, 
    val children: List<FileItem> = emptyList(),
    var isExpanded: Boolean = false
)

@Composable
fun FileExplorerScreen(onFileClick: (String) -> Unit = {}) {
    // Initialer State für die Baumansicht
    var rootItems by remember {
        mutableStateOf(
            listOf(
                FileItem("src", true, listOf(FileItem("MainActivity.kt", false))),
                FileItem("build.gradle.kts", false),
                FileItem("README.md", false),
                FileItem(".gitignore", false)
            )
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("File Explorer", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(rootItems) { item ->
                FileRow(item, onFileClick) {
                    // Toggle expand state
                    rootItems = toggleExpanded(rootItems, item)
                }
            }
        }
    }
}

fun toggleExpanded(items: List<FileItem>, target: FileItem): List<FileItem> {
    return items.map {
        if (it == target) {
            it.copy(isExpanded = !it.isExpanded)
        } else if (it.isDirectory) {
            it.copy(children = toggleExpanded(it.children, target))
        } else {
            it
        }
    }
}

@Composable
fun FileRow(item: FileItem, onFileClick: (String) -> Unit, onToggle: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable { 
                    if (item.isDirectory) onToggle() 
                    else onFileClick(item.name) 
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (item.isDirectory) (if (item.isExpanded) "📂 " else "📁 ") + item.name else "📄 ${item.name}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (item.isDirectory && item.isExpanded) {
            Column(modifier = Modifier.padding(start = 16.dp)) {
                item.children.forEach { child ->
                    FileRow(child, onFileClick, onToggle)
                }
            }
        }
    }
}
