package nl.vanwijngaarden.koen.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FavoritesScreen(
    drawerState: DrawerState,
    sharedModel: SharedViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val dataStore = ApplicationPreferences(context)
    val token by dataStore.getToken.collectAsState(null)
    if (!token.isNullOrEmpty()) sharedModel.refreshFavoriteArticles(token)

    FavoritesScaffold(drawerState = drawerState, sharedModel = sharedModel) {
        ArticlesFavoritesList(it, sharedModel, navController)
    }
}