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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(
    drawerState: DrawerState,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            drawerContent()
        },
    ) {
        content()
    }
}


@Composable
fun DrawerContent(
    itemsList: Collection<String>,
    currentItem: String,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(12.dp),
    ) {

        itemsList.forEach { navDrawerItem ->
            val backgroundColor = if (navDrawerItem == currentItem) MaterialTheme.colorScheme.onPrimaryContainer else Color.Transparent
            val textIconColor = if (navDrawerItem == currentItem) MaterialTheme.colorScheme.primary else Color.LightGray
            TextButton(
                modifier = Modifier
                    .background(backgroundColor, shape = CircleShape)
                    .padding(vertical = 8.dp),
                onClick = {
                    onItemClick(navDrawerItem)
                }
            ) {
                Text(
                    text = navDrawerItem,
                    fontSize = 16.sp, color = textIconColor,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}