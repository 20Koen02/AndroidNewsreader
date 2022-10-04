package nl.vanwijngaarden.koen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import nl.vanwijngaarden.koen.ui.components.NRNavDrawer
import nl.vanwijngaarden.koen.ui.components.NRScaffold
import nl.vanwijngaarden.koen.ui.home.HomeScreen
import nl.vanwijngaarden.koen.ui.theme.Newsreader704841Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Newsreader704841Theme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )
                }

                val drawerState = rememberDrawerState(DrawerValue.Closed)
                NRNavDrawer(drawerState = drawerState) {
                    NRScaffold(drawerState = drawerState) {
                        HomeScreen(it)
                    }
                }
            }
        }
    }
}