package com.mobilecodespace.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Sidepanel(
    modifier: Modifier = Modifier,
    topContent: @Composable () -> Unit = {},
    mainContent: @Composable () -> Unit = {},
    bottomTabs: List<String> = emptyList(),
    selectedTabIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(300.dp)
    ) {
        // Oberer Bereich: Menü-Einträge
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            topContent()
        }

        // Hauptbereich: Dynamischer Slot
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            mainContent()
        }

        // Unterer Bereich: Dynamische Tabs
        if (bottomTabs.isNotEmpty()) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                bottomTabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        text = { Text(text = title) }
                    )
                }
            }
        }
    }
}
