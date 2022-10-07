package nl.vanwijngaarden.koen.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import nl.vanwijngaarden.koen.R
import nl.vanwijngaarden.koen.datastore.ApplicationPreferences
import nl.vanwijngaarden.koen.ui.components.HtmlText
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel
import java.text.DateFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsContent(
    sharedModel: SharedViewModel
) {
    val article by sharedModel.detailArticle.collectAsState()

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .aspectRatio(1.33333f)
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = article!!.image.replace("sqr256", "wd640"),
                contentDescription = article!!.title,
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(colors = gradientColors))
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    "NU.nl  •  ${
                        DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT)
                            .format(article!!.publishDate)
                    }",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF808080)
                )
            }
            Text(
                article!!.title,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            HtmlText(
                article!!.summary,
                Modifier.padding(vertical = 8.dp),
                MaterialTheme.colorScheme.primary.hashCode()
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Row(
                verticalAlignment = Alignment.Bottom, modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = stringResource(R.string.Categories),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 2.dp)
                        .size(24.dp)
                )
                Text(
                    text = stringResource(R.string.Categories),
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            FlowRow(mainAxisSpacing = 8.dp, modifier = Modifier.padding(bottom = 16.dp)) {
                article!!.categories.map {
                    SuggestionChip(
                        onClick = { /*TODO*/ },
                        label = { Text(it.name) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
        }
    }
}

val gradientColors = listOf(
    Color(0x70000000),
    Color(0x50000000),
    Color(0x15000000),
    Color(0x00000000),
    Color(0x00000000),
    Color(0x00000000),
    Color(0x00000000)
)