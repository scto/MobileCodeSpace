package com.mobilecodespace.feature.filetree.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilecodespace.feature.filetree.model.FileNode
import com.mobilecodespace.feature.filetree.viewmodel.FileTreeViewModel
import java.io.File

@Composable
fun FileTreeView(
    projectPath: String,
    onFileClick: (File) -> Unit,
    viewModel: FileTreeViewModel = viewModel()
) {
    val nodes by viewModel.nodes.collectAsState()

    LaunchedEffect(projectPath) {
        viewModel.loadFiles(projectPath)
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(nodes) { node ->
            FileNodeItem(node = node, onFileClick = onFileClick)
        }
    }
}

@Composable
fun FileNodeItem(node: FileNode, onFileClick: (File) -> Unit, depth: Int = 0) {
    var expanded by remember { mutableStateOf(node.isExpanded) }

    Column {
        Text(
            text = ("  ".repeat(depth)) + (if (node.file.isDirectory) "📁 " else "📄 ") + node.file.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    if (node.file.isDirectory) {
                        expanded = !expanded
                    } else {
                        onFileClick(node.file)
                    }
                }
        )
        if (expanded && node.file.isDirectory) {
            node.children.forEach { child ->
                FileNodeItem(node = child, onFileClick = onFileClick, depth = depth + 1)
            }
        }
    }
}
