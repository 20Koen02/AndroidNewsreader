package nl.vanwijngaarden.koen.ui.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScaffold(
    sharedModel: SharedViewModel,
    drawerState: DrawerState,
    content: @Composable (innerPadding: PaddingValues) -> Unit  // Padding determined by scaffold
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val scope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current
    val dataStore = ApplicationPreferences(context)
    val savedTheme by dataStore.getTheme.collectAsState(0)
    val darkTheme = when (savedTheme) {
        0 -> isSystemInDarkTheme()
        1 -> false
        2 -> true
        else -> false
    }
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !darkTheme
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.Settings),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(R.string.OpenMenu)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },

        content = content
    )
}