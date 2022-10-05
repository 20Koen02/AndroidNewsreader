package nl.vanwijngaarden.koen.ui.details

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailsScreen(
    sharedModel: SharedViewModel,
    drawerState: DrawerState,
    navController: NavController,
    id: String?
) {
    if (id == null) {
        navController.navigateUp()
    }

    val article by sharedModel.getDetailArticle(id!!, navController).collectAsState()

    // Article should be available because it's checked in the shared viewmodel
    require(article != null)

    DetailsScaffold(
        navController = navController,
        article = article!!
    ) {
        DetailsContent(article!!)
    }
}