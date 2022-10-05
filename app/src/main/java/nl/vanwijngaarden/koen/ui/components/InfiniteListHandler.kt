package nl.vanwijngaarden.koen.ui.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*


@Composable
fun LazyListState.OnBottomReached(
    buffer : Int = 0,
    onLoadMore : () -> Unit
) {
    require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                return@derivedStateOf true

            // subtract buffer from the total items
            lastVisibleItem.index >=  layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(shouldLoadMore){
        snapshotFlow { shouldLoadMore.value }
            .collect { if (it) onLoadMore() }
    }
}