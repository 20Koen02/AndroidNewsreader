package nl.vanwijngaarden.koen.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.Screen
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(
    drawerState: DrawerState,
    navController: NavController,
    selectedItem: MutableState<Int>,
    content: @Composable () -> Unit
) {

    val context = LocalContext.current
    val dataStore = ApplicationPreferences(context)
    val storedToken by dataStore.getToken.collectAsState("")
    val loggedIn = !storedToken.isNullOrEmpty()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(/*modifier =  Modifier.width(304.dp) */) {
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                Row(
                    modifier = Modifier
                        .height(64.dp)
                        .padding(NavigationDrawerItemDefaults.ItemPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_newspaper_fill0_wght400_grad0_opsz48),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(32.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        stringResource(R.string.Title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Divider(
                    modifier = Modifier
                        .alpha(0.5F)
                        .padding(bottom = 16.dp)
                )
                NavItem(
                    0,
                    selectedItem,
                    drawerState,
                    Icons.Default.Home,
                    stringResource(R.string.Articles),
                    navController,
                    Screen.HomeScreen.route
                )
                if (loggedIn) NavItem(
                    1,
                    selectedItem,
                    drawerState,
                    Icons.Default.Favorite,
                    stringResource(R.string.Favorites),
                    navController,
                    Screen.FavoritesScreen.route
                )
                NavItem(
                    2,
                    selectedItem,
                    drawerState,
                    Icons.Default.Settings,
                    stringResource(R.string.Settings),
                    navController,
                    Screen.SettingsScreen.route
                )
                if (!loggedIn) NavItem(
                    3,
                    selectedItem,
                    drawerState,
                    Icons.Default.Login,
                    stringResource(R.string.Login),
                    navController,
                    Screen.LoginScreen.route
                )
                if (loggedIn) NavItem(
                    3,
                    selectedItem,
                    drawerState,
                    Icons.Default.Logout,
                    stringResource(R.string.Logout),
                    navController,
                    Screen.LoginScreen.route,
                    true
                )
            }
        },
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
                content = content
            )
        }
    )
}