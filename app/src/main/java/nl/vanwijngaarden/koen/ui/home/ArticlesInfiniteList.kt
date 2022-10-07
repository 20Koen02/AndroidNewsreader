package nl.vanwijngaarden.koen.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences
import nl.vanwijngaarden.koen.ui.components.ArticlesListItem
import nl.vanwijngaarden.koen.ui.components.PlaceholderArticles
import nl.vanwijngaarden.koen.ui.util.OnBottomReached
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArticlesList(
    innerPadding: PaddingValues,
    sharedModel: SharedViewModel,
    navController: NavController
) {
    val articles by sharedModel.articlesState.collectAsState()
    val failedState by sharedModel.failedArticlesState.collectAsState()
    val failedMoreArticlesState by sharedModel.failedMoreArticlesState.collectAsState()
    val listState = rememberLazyListState()

    val context = LocalContext.current
    val dataStore = ApplicationPreferences(context)
    val token by dataStore.getToken.collectAsState(null)

    if (!failedState) {
        if (articles.isNotEmpty()) {
            SwipeRefresh(
                state = SwipeRefreshState(false),
                onRefresh = { sharedModel.refreshArticles(authToken = token) },
                modifier = Modifier
                    .consumedWindowInsets(innerPadding)
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    state = listState,
                ) {
                    items(articles) { ArticlesListItem(it, sharedModel, navController) }
                    items(1) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (failedMoreArticlesState) {
                                IconButton(onClick = {
                                    sharedModel.loadMoreArticles(token)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = stringResource(R.string.RefreshButton)
                                    )
                                }
                            } else {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .size(24.dp),
                                    strokeWidth = 3.dp
                                )
                            }
                        }
                    }
                }

                listState.OnBottomReached(buffer = 2) {
                    sharedModel.loadMoreArticles(token)
                }
            }
        } else {
            // Network request loading...
            PlaceholderArticles(innerPadding)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                stringResource(R.string.ArticlesListNetworkError),
                textAlign = TextAlign.Center
            )
        }
    }
}