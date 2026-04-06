package com.mobilecodespace.feature.filetree.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilecodespace.feature.filetree.model.FileNode
import com.mobilecodespace.feature.filetree.model.FileTreeConfig
import com.mobilecodespace.feature.filetree.model.ListType
import com.mobilecodespace.feature.filetree.model.SortOrder
import com.mobilecodespace.feature.filetree.viewmodel.FileTreeViewModel
import java.io.File

@Composable
fun FileTreeView(
    projectPath: String,
    onFileClick: (File) -> Unit,
    viewModel: FileTreeViewModel = viewModel()
) {
    val nodes by viewModel.nodes.collectAsState()
    val config by viewModel.config.collectAsState()

    LaunchedEffect(projectPath) {
        viewModel.loadFiles(projectPath)
    }

    val flattenedNodes = remember(nodes) { flattenTree(nodes.nodes) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Toolbar für Konfiguration
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Sort: ")
                SortOrder.entries.forEach { order ->
                    TextButton(onClick = { viewModel.updateConfig(config.copy(sortOrder = order), projectPath) }) {
                        Text(text = order.name.take(1), color = if (config.sortOrder == order) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = config.showHidden,
                    onCheckedChange = { viewModel.updateConfig(config.copy(showHidden = it), projectPath) }
                )
                Text("Hidden")
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("View: ")
                ListType.entries.forEach { type ->
                    TextButton(onClick = { viewModel.updateConfig(config.copy(listType = type), projectPath) }) {
                        Text(text = type.name.take(3), color = if (config.listType == type) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(flattenedNodes) { (node, depth) ->
                FileNodeItem(
                    node = node,
                    depth = depth,
                    onFileClick = onFileClick,
                    onToggle = { viewModel.toggleExpansion(node) }
                )
            }
        }
    }
}

private fun flattenTree(nodes: List<FileNode>, depth: Int = 0): List<Pair<FileNode, Int>> {
    val result = mutableListOf<Pair<FileNode, Int>>()
    for (node in nodes) {
        result.add(node to depth)
        if (node.isExpanded && node.children.isNotEmpty()) {
            result.addAll(flattenTree(node.children, depth + 1))
        }
    }
    return result
}

@Composable
fun FileNodeItem(
    node: FileNode,
    depth: Int,
    onFileClick: (File) -> Unit,
    onToggle: () -> Unit
) {
    Text(
        text = ("  ".repeat(depth)) + (if (node.file.isDirectory) (if (node.isExpanded) "📂 " else "📁 ") else "📄 ") + node.file.name,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                if (node.file.isDirectory) {
                    onToggle()
                } else {
                    onFileClick(node.file)
                }
            }
    )
}
