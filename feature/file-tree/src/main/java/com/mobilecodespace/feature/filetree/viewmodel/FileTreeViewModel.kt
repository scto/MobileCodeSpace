package com.mobilecodespace.feature.filetree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecodespace.feature.filetree.model.FileAttributes
import com.mobilecodespace.feature.filetree.model.FileNode
import com.mobilecodespace.feature.filetree.model.FileTreeConfig
import com.mobilecodespace.feature.filetree.model.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FileTreeViewModel @Inject constructor() : ViewModel() {

    private val _nodes = MutableStateFlow<List<FileNode>>(emptyList())
    val nodes: StateFlow<List<FileNode>> = _nodes

    fun loadFiles(path: String, config: FileTreeConfig = FileTreeConfig()) {
        viewModelScope.launch {
            _nodes.value = withContext(Dispatchers.IO) {
                val root = File(path)
                if (root.exists() && root.isDirectory) {
                    buildTree(root, config)
                } else {
                    emptyList()
                }
            }
        }
    }

    private fun buildTree(file: File, config: FileTreeConfig): List<FileNode> {
        return file.listFiles()
            ?.filter { if (!config.showHidden) !it.isHidden else true }
            ?.map { child ->
                val children = if (child.isDirectory) buildTree(child, config) else emptyList()
                FileNode(
                    file = child,
                    children = children,
                    attributes = FileAttributes(
                        isHidden = child.isHidden,
                        lastModified = child.lastModified(),
                        size = child.length()
                    )
                )
            }
            ?.sortedWith(getComparator(config.sortOrder)) ?: emptyList()
    }

    private fun getComparator(sortOrder: SortOrder): Comparator<FileNode> {
        return when (sortOrder) {
            SortOrder.NAME -> compareBy { it.file.name }
            SortOrder.DATE -> compareBy { it.file.lastModified() }
            SortOrder.SIZE -> compareBy { it.file.length() }
            SortOrder.TYPE -> compareBy { it.file.extension }
        }
    }
}
