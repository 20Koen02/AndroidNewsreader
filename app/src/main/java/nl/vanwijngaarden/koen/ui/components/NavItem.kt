package nl.vanwijngaarden.koen.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavItem(
    i: Int,
    selectedItem: MutableState<Int>,
    drawerState: DrawerState,
    icon: ImageVector,
    label: String,
    navController: NavController,
    destination: String,
    logoutBeforeNavigating: Boolean = false
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
            scope.launch {
                selectedItem.value = i
                navController.navigate(destination) {
                    popUpTo(0)
                }
                drawerState.close()
                if (logoutBeforeNavigating) {
                    val dataStore = ApplicationPreferences(context)
                    dataStore.saveToken("")
                }
            }
        },
        modifier = Modifier.padding(end = 16.dp),
        shape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
    )
}