package nl.vanwijngaarden.koen.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArticlesList(
    innerPadding: PaddingValues,
    model: SharedViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        model.refreshArticles()
    }
    val articlesRes by model.articlesLiveData.observeAsState()

    articlesRes?.let { articles ->
        if (articles.isSuccessful) {
            // Is successful
            LazyColumn(
                modifier = Modifier.consumedWindowInsets(innerPadding),
                contentPadding = innerPadding
            ) {
                items(articles.body.results) { article ->
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Row(
                            modifier = Modifier.padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(100.dp, 75.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                model = article.image,
                                contentDescription = article.title,
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text(
                                    article.title,
                                    fontWeight = FontWeight(500),
                                    fontSize = 14.sp,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                        Divider(modifier = Modifier.alpha(0.5F))
                    }

                }
            }
        } else {
            // Network request failed
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
    } ?: run {
        // Network request loading...
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}