package nl.vanwijngaarden.koen.ui.home

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    sharedModel: SharedViewModel,
    drawerState: DrawerState,
    navController: NavController
    ) {
    HomeScaffold(drawerState = drawerState, sharedModel = sharedModel) {
        ArticlesList(innerPadding = it, sharedModel = sharedModel, navController)
    }
}