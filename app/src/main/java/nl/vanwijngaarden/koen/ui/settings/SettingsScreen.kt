package nl.vanwijngaarden.koen.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen(
    drawerState: DrawerState,
    sharedModel: SharedViewModel
) {
    SettingsScaffold(drawerState = drawerState, sharedModel = sharedModel) {
        Column(modifier = Modifier
            .consumedWindowInsets(it)
            .padding(it)) {
            SettingsContent()
        }
    }
}