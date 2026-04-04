package com.mobilecodespace.feature.editor.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun EditorToolbar(
    onSave: () -> Unit,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onFormat: () -> Unit,
    onSelectAll: () -> Unit,
    onCut: () -> Unit,
    onCopy: () -> Unit,
    onPaste: () -> Unit
) {
    Row {
        IconButton(onClick = onSave) { Icon(Icons.Default.Save, contentDescription = "Speichern") }
        IconButton(onClick = onUndo) { Icon(Icons.Default.Undo, contentDescription = "Rückgängig") }
        IconButton(onClick = onRedo) { Icon(Icons.Default.Refresh, contentDescription = "Wiederholen") }
        IconButton(onClick = onFormat) { Icon(Icons.Default.Edit, contentDescription = "Formatieren") }
        IconButton(onClick = onSelectAll) { Icon(Icons.Default.SelectAll, contentDescription = "Alles auswählen") }
        IconButton(onClick = onCut) { Icon(Icons.Default.ContentCut, contentDescription = "Ausschneiden") }
        IconButton(onClick = onCopy) { Icon(Icons.Default.ContentCopy, contentDescription = "Kopieren") }
        IconButton(onClick = onPaste) { Icon(Icons.Default.ContentPaste, contentDescription = "Einfügen") }
    }
}
