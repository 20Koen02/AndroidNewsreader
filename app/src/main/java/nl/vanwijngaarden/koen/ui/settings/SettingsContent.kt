package nl.vanwijngaarden.koen.ui.settings

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences
import nl.vanwijngaarden.koen.ui.settings.content.SettingSegmentedButtonsItem
import nl.vanwijngaarden.koen.ui.settings.content.SettingToggleItem
import nl.vanwijngaarden.koen.ui.settings.content.SettingsCategory

@Composable
fun SettingsContent() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = ApplicationPreferences(context)
    val savedMaterialYou by dataStore.getMaterialYou.collectAsState(false)
    val savedTheme by dataStore.getTheme.collectAsState(0)

    val supportsMaterialYou = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    Column(Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
        SettingsCategory(stringResource(R.string.Appearance)) {
            SettingToggleItem(
                stringResource(R.string.MaterialYouSetting),
                stringResource(R.string.MaterialYouDesc),
                if (supportsMaterialYou) savedMaterialYou!! else false
            ) {
                if (supportsMaterialYou) {
                    scope.launch {
                        dataStore.saveMaterialYou(it)
                    }
                }
            }
            SettingSegmentedButtonsItem(
                stringResource(R.string.Theme),
                stringResource(R.string.ThemeDescription),
                savedTheme!!,
                listOf(
                    stringResource(R.string.AutomaticTheme),
                    stringResource(R.string.LightTheme),
                    stringResource(
                        R.string.DarkTheme
                    )
                )
            ) {
                scope.launch {
                    dataStore.saveTheme(it)
                }
            }
        }
    }
}

