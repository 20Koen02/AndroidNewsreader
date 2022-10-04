package nl.vanwijngaarden.koen.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable


@Composable
fun HomeScreen(
    innerPadding: PaddingValues  // Padding determined by scaffold
) {
    ArticlesList(innerPadding = innerPadding)
}