package nl.vanwijngaarden.koen.models

import android.annotation.SuppressLint
import nl.vanwijngaarden.koen.api.responses.GetArticlesResponse
import java.text.SimpleDateFormat
import java.util.*

data class Article(
    val categories: List<Category>,
    val feed: Int,
    val id: Int,
    val image: String,
    val isLiked: Boolean,
    val publishDate: Date,
    val related: List<String>,
    val summary: String,
    val title: String,
    val url: String
) {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun fromResponse(data: GetArticlesResponse): List<Article> {
            return data.results.map { a ->
                Article(
                    categories = Category.fromResponse(a.categories),
                    feed = a.feed,
                    id = a.id,
                    image = a.image,
                    isLiked = a.isLiked,
                    publishDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(a.publishDate)!!,
                    related = a.related,
                    summary = a.summary,
                    title = a.title,
                    url = a.url
                )
            }
        }
    }
}

