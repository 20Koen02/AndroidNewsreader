package nl.vanwijngaarden.koen.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.ui.components.InfiniteListHandler
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArticlesList(
    innerPadding: PaddingValues,
    sharedModel: SharedViewModel = viewModel()
) {
    val articles by sharedModel.articlesState.collectAsState()
    val failedState by sharedModel.failedState.collectAsState()
    val listState = rememberLazyListState()

    if (!failedState) {
        if (articles.isNotEmpty()) {
            SwipeRefresh(
                state = SwipeRefreshState(false),
                onRefresh = { sharedModel.refreshArticles() },
                modifier = Modifier
                    .consumedWindowInsets(innerPadding)
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    state = listState,
                ) {
                    items(articles) { ArticlesListItem(it) }
                    items(1) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(24.dp),
                                strokeWidth = 3.dp
                            )
                        }
                    }
                }

                InfiniteListHandler(listState = listState) {
                    sharedModel.loadMoreArticles()
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