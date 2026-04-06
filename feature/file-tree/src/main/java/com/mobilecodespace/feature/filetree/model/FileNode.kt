package com.mobilecodespace.feature.filetree.model

import java.io.File

data class FileNode(
    val file: File,
    val children: List<FileNode> = emptyList(),
    val isExpanded: Boolean = false,
    val attributes: FileAttributes = FileAttributes()
)

data class FileAttributes(
    val isHidden: Boolean = false,
    val lastModified: Long = 0L,
    val size: Long = 0L
)

data class FileNodeList(
    val nodes: List<FileNode>
)
