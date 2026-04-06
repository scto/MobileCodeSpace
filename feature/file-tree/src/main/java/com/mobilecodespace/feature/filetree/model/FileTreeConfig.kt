package com.mobilecodespace.feature.filetree.model

enum class SortOrder {
    DATE, SIZE, TYPE, NAME
}

enum class ListType {
    FILE_LIST, PACKAGE_LIST, MODULE_LIST
}

data class FileTreeConfig(
    val showHidden: Boolean = false,
    val sortOrder: SortOrder = SortOrder.NAME,
    val listType: ListType = ListType.FILE_LIST
)
