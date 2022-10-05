package nl.vanwijngaarden.koen.api

import nl.vanwijngaarden.koen.api.responses.GetArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsreaderService {
    @GET("Articles")
    suspend fun getArticles(
        @Query("count") count: Int? = null,  // default=20
        @Query("feed") feed: Int? = null,
        @Query("feeds") feeds: String? = null,
        @Query("category") category: String? = null,
    ): Response<GetArticlesResponse>

    @GET("Articles/{id}")
    suspend fun getArticleById(
        @Path("id") id: Int,
        @Query("count") count: Int? = 20,  // default=1
        @Query("feed") feed: Int? = null,
        @Query("feeds") feeds: String? = null,
        @Query("category") category: String? = null,
    ): Response<GetArticlesResponse>
}