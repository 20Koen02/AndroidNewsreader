package nl.vanwijngaarden.koen.ui.details

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScaffold(
    sharedModel: SharedViewModel,
    navController: NavController,
    content: @Composable (innerPadding: PaddingValues) -> Unit  // Padding determined by scaffold
) {
    val article by sharedModel.detailArticle.collectAsState()
    val isLiked by article!!.isLiked.collectAsState(false)

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
    }

    val context = LocalContext.current
    val dataStore = ApplicationPreferences(context)
    val token by dataStore.getToken.collectAsState(null)

    val likeFail by sharedModel.failedLikeState.collectAsState()

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "${article!!.title} - ${article!!.url}")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    if (likeFail) AlertDialog(
        onDismissRequest = { sharedModel.resetLikeFail() },
        title = {
            Text(
                if (isLiked) stringResource(R.string.removeLikeFailed)
                else stringResource(R.string.LikeFailed)
            )
        },
        text = {
            Text(
                if (isLiked) stringResource(R.string.RemoveLikeFailedMessage)
                else stringResource(R.string.LikeFailMessage)
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = stringResource(R.string.Error)
            )
        },
        confirmButton = {
            TextButton(onClick = { sharedModel.resetLikeFail() }) {
                Text(stringResource(R.string.Dismiss))
            }
        }
    )

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
                            imageVector = Icons.Default.ArrowBack,
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
                        CustomTabsIntent.Builder().build()
                            .launchUrl(context, Uri.parse(article!!.url))
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.OpenInBrowser,
                            contentDescription = stringResource(R.string.OpenInBrowser),
                            tint = Color.White
                        )
                    }
                    if (!token.isNullOrEmpty()) {
                        IconButton(onClick = {

                            if (isLiked) {
                                sharedModel.removeLikeArticle(authToken = token, article!!.id)
                            } else {
                                sharedModel.likeArticle(authToken = token, article!!.id)
                            }

                        }) {
                            Icon(
                                imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(R.string.AddToFavorites),
                                tint = Color.White
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
            )
        },

        content = content
    )
}