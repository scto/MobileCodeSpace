package com.mobilecodespace.feature.filetree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    root.listFiles()?.map { FileNode(it) }?.sortedWith(getComparator(config.sortOrder)) ?: emptyList()
                } else {
                    emptyList()
                }
            }
        }
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
