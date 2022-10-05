package nl.vanwijngaarden.koen.ui.details

import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailsScreen(
    sharedModel: SharedViewModel,
    drawerState: DrawerState,
    navController: NavController,
    id: String?
) {
    Log.i("detailsscreen", id?: "NO ID")
    if (id == null) {
        navController.navigateUp()
    }

    val article by sharedModel.getDetailArticle(id!!, navController).collectAsState()

    DetailsScaffold(drawerState = drawerState, navController = navController, sharedModel = sharedModel) {
        Column(modifier = Modifier
            .consumedWindowInsets(it)
            .padding(it)) {

            Text(id!!)

            if (article != null) {
                Text(article!!.title)
            }

        }
    }
}