package nl.vanwijngaarden.koen.api.responses

import com.squareup.moshi.Json

data class GetArticlesByIdResponse(
    @Json(name = "NextId")
    val nextId: Int,
    @Json(name = "Results")
    val results: List<Result>
) {
    data class Result(
        @Json(name = "Categories")
        val categories: List<Category>,
        @Json(name = "Feed")
        val feed: Int,
        @Json(name = "Id")
        val id: Int,
        @Json(name = "Image")
        val image: String,
        @Json(name = "IsLiked")
        val isLiked: Boolean,
        @Json(name = "PublishDate")
        val publishDate: String,
        @Json(name = "Related")
        val related: List<Any>,
        @Json(name = "Summary")
        val summary: String,
        @Json(name = "Title")
        val title: String,
        @Json(name = "Url")
        val url: String
    ) {
        data class Category(
            @Json(name = "Id")
            val id: Int,
            @Json(name = "Name")
            val name: String
        )
    }
}