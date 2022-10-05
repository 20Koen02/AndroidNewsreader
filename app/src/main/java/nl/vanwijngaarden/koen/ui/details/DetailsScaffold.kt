package nl.vanwijngaarden.koen.ui.details

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.models.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScaffold(
    navController: NavController,
    article: Article,
    content: @Composable (innerPadding: PaddingValues) -> Unit  // Padding determined by scaffold
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
    }

    val context = LocalContext.current

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "${article.title} - ${article.url}")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.GoBack),
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        context.startActivity(shareIntent)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = stringResource(R.string.Share),
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(article.url))
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.OpenInBrowser,
                            contentDescription = stringResource(R.string.OpenInBrowser),
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = stringResource(R.string.AddToFavorites),
                            tint = Color.White
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
            )
        },

        content = content
    )
}