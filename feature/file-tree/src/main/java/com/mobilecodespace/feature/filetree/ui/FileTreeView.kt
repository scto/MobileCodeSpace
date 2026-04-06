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
            FileRow(node = node, onClick = onFileClick)
        }
    }
}

@Composable
fun FileRow(node: FileNode, onClick: (File) -> Unit) {
    Text(
        text = node.file.name,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(node.file) }
    )
}
