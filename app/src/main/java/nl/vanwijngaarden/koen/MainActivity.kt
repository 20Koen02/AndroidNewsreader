package nl.vanwijngaarden.koen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import nl.vanwijngaarden.koen.ui.components.NavDrawer
import nl.vanwijngaarden.koen.ui.details.DetailsScreen
import nl.vanwijngaarden.koen.ui.favorites.FavoritesScreen
import nl.vanwijngaarden.koen.ui.home.HomeScreen
import nl.vanwijngaarden.koen.ui.theme.Newsreader704841Theme
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val sharedModel: SharedViewModel by viewModels()

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

                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                NavDrawer(drawerState = drawerState, navController = navController) {
                    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
                            composable(Screen.HomeScreen.route) {
                                HomeScreen(sharedModel = sharedModel, drawerState = drawerState, navController)
                            }
                            composable(Screen.FavoritesScreen.route) {
                                FavoritesScreen(sharedModel = sharedModel, drawerState = drawerState)
                            }
                            composable(Screen.DetailsScreen.route + "/{id}") {
                                DetailsScreen(sharedModel = sharedModel, drawerState = drawerState, navController, it.arguments?.getString("id"))
                            }
                        }

                }
            }
        }
    }
}

sealed class Screen(val route:String){
    object HomeScreen:Screen("home")
    object FavoritesScreen:Screen("favorites")
    object DetailsScreen:Screen("details")
}