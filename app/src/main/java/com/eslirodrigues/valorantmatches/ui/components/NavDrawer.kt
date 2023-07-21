package com.eslirodrigues.valorantmatches.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavDrawer(
    drawerState: DrawerState,
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { drawerContent() }
    ) {
        content()
    }
}


@Composable
fun DrawerContent(
    itemsList: List<String>,
    currentItem: String,
    onItemClick: (String) -> Unit
) {
    ModalDrawerSheet {
        Column(modifier = Modifier.fillMaxWidth().background(Color.Black).padding(12.dp)) {
            itemsList.forEach { navDrawerItem ->
                NavigationDrawerItem(
                    label = {
                        Text(text = navDrawerItem, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    },
                    selected = navDrawerItem == currentItem,
                    onClick = { onItemClick(navDrawerItem) }
                )
            }
        }
    }
}