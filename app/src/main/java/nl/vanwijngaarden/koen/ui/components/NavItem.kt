package nl.vanwijngaarden.koen.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavItem(
    i: Int,
    selectedItem: MutableState<Int>,
    drawerState: DrawerState,
    icon: ImageVector,
    label: String,
    navController: NavController,
    destination: String
) {
    val scope = rememberCoroutineScope()

    NavigationDrawerItem(
        icon = {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        label = { Text(label) },
        selected = selectedItem.value == i,
        onClick = {
            scope.launch { drawerState.close() }
            selectedItem.value = i
            navController.navigate(destination)
        },
        modifier = Modifier.padding(end = 16.dp),
        shape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
    )
}