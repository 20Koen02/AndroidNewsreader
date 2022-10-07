package nl.vanwijngaarden.koen.ui.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScaffold(
    sharedModel: SharedViewModel,
    drawerState: DrawerState,
    content: @Composable (innerPadding: PaddingValues) -> Unit  // Padding determined by scaffold
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isLoading by sharedModel.loadingState.collectAsState()

    val scope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()

    val context = LocalContext.current
    val dataStore = ApplicationPreferences(context)
    val savedTheme by dataStore.getTheme.collectAsState(0)
    val token by dataStore.getToken.collectAsState(null)

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
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.Title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                actions = {
                    IconButton(
                        onClick = {
                            sharedModel.refreshArticles(authToken = token)
                        },
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = stringResource(R.string.RefreshButton)
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },

        content = content
    )
}