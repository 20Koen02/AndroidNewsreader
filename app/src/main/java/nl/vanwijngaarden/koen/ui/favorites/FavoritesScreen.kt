package nl.vanwijngaarden.koen.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import nl.vanwijngaarden.koen.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FavoritesScreen(
    drawerState: DrawerState
) {
    FavoritesScaffold(drawerState = drawerState) {
        Column(modifier = Modifier
            .consumedWindowInsets(it)
            .padding(it)) {
            Text(stringResource(R.string.Favorites))
        }
    }
}