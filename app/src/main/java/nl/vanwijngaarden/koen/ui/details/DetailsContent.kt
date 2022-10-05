package nl.vanwijngaarden.koen.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import nl.vanwijngaarden.koen.models.Article
import java.text.DateFormat


@Composable
fun DetailsContent(
    article: Article
) {
    Column() {
        Box(
            modifier = Modifier
                .aspectRatio(1.33333f)
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = article.image.replace("sqr256", "wd640"),
                contentDescription = article.title,
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
                            .format(article.publishDate)
                    }",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF808080)
                )
            }
            Text(
                article.title,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                article.summary,
                style = MaterialTheme.typography.bodyLarge
            )
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
