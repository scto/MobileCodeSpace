package com.mobilecodespace.feature.filetree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecodespace.feature.filetree.model.FileAttributes
import com.mobilecodespace.feature.filetree.model.FileNode
import com.mobilecodespace.feature.filetree.model.FileNodeList
import com.mobilecodespace.feature.filetree.model.FileTreeConfig
import com.mobilecodespace.feature.filetree.model.ListType
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

    private val _nodes = MutableStateFlow(FileNodeList(emptyList()))
    val nodes: StateFlow<FileNodeList> = _nodes

    private val _config = MutableStateFlow(FileTreeConfig())
    val config: StateFlow<FileTreeConfig> = _config

    private var allNodes: List<FileNode> = emptyList()

    fun updateConfig(newConfig: FileTreeConfig, currentPath: String) {
        _config.value = newConfig
        loadFiles(currentPath, newConfig)
    }

    fun loadFiles(path: String, config: FileTreeConfig = _config.value) {
        _config.value = config
        viewModelScope.launch {
            val tree = withContext(Dispatchers.IO) {
                val root = File(path)
                if (root.exists() && root.isDirectory) {
                    when (config.listType) {
                        ListType.FILE_LIST -> buildTree(root, config)
                        ListType.PACKAGE_LIST -> buildPackageTree(root, config)
                        ListType.MODULE_LIST -> buildModuleTree(root, config)
                    }
                } else {
                    emptyList()
                }
            }
            allNodes = tree
            _nodes.value = FileNodeList(tree)
        }
    }

    fun filterFiles(query: String) {
        if (query.isBlank()) {
            _nodes.value = FileNodeList(allNodes)
        } else {
            _nodes.value = FileNodeList(filterTree(allNodes, query))
        }
    }

    private fun filterTree(nodes: List<FileNode>, query: String): List<FileNode> {
        return nodes.mapNotNull { node ->
            val matches = node.file.name.contains(query, ignoreCase = true)
            val filteredChildren = filterTree(node.children, query)
            
            if (matches || filteredChildren.isNotEmpty()) {
                node.copy(
                    children = filteredChildren,
                    isExpanded = if (query.isNotBlank()) true else node.isExpanded
                )
            } else {
                null
            }
        }
    }

    fun toggleExpansion(node: FileNode) {
        _nodes.value = FileNodeList(toggleNodeExpansion(_nodes.value.nodes, node))
    }

    private fun toggleNodeExpansion(nodes: List<FileNode>, target: FileNode): List<FileNode> {
        return nodes.map { node ->
            if (node.file == target.file) {
                node.copy(isExpanded = !node.isExpanded)
            } else if (node.children.isNotEmpty()) {
                node.copy(children = toggleNodeExpansion(node.children, target))
            } else {
                node
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

    private fun buildPackageTree(file: File, config: FileTreeConfig): List<FileNode> = buildTree(file, config)
    private fun buildModuleTree(file: File, config: FileTreeConfig): List<FileNode> = buildTree(file, config)

    private fun getComparator(sortOrder: SortOrder): Comparator<FileNode> {
        return when (sortOrder) {
            SortOrder.NAME -> compareBy { it.file.name }
            SortOrder.DATE -> compareBy { it.file.lastModified() }
            SortOrder.SIZE -> compareBy { it.file.length() }
            SortOrder.TYPE -> compareBy { it.file.extension }
        }
    }
}
