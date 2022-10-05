package nl.vanwijngaarden.koen.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import nl.vanwijngaarden.koen.Screen
import nl.vanwijngaarden.koen.models.Article
import nl.vanwijngaarden.koen.viewmodels.SharedViewModel

@Composable
fun ArticlesListItem(
    article: Article,
    sharedModel: SharedViewModel,
    navController: NavController
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp).clickable {
        sharedModel.setDetailArticle(article)
        navController.navigate(Screen.DetailsScreen.route + "/${article.id}")
    }) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp, 75.dp)
                    .clip(RoundedCornerShape(4.dp)),
                model = article.image.replace("sqr256", "wd320"),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
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