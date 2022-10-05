package nl.vanwijngaarden.koen.ui.home

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    sharedModel: SharedViewModel = viewModel(),
    drawerState: DrawerState,
    ) {
    HomeScaffold(drawerState = drawerState, sharedModel = sharedModel) {
        ArticlesList(innerPadding = it, sharedModel = sharedModel)
    }
}