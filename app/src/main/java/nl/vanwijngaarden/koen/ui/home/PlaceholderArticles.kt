package nl.vanwijngaarden.koen.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import nl.vanwijngaarden.koen.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlaceholderArticles(innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .consumedWindowInsets(innerPadding)
            .padding(innerPadding),
        userScrollEnabled = false
    ) {
        items(10) { i ->
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp, 75.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ),
                    )
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            stringResource(R.string.LoremIpsumTitle),
                            fontWeight = FontWeight(500),
                            fontSize = 14.sp,
                            lineHeight = 18.sp,
                            modifier = Modifier.placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ),
                        )
                    }
                }
                Divider(modifier = Modifier.alpha(0.5F))
            }
        }
    }
}