package nl.vanwijngaarden.koen.api

import nl.vanwijngaarden.koen.api.responses.GetArticlesResponse
import nl.vanwijngaarden.koen.api.responses.LoginUserResponse
import nl.vanwijngaarden.koen.api.responses.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.*

data class UserRequestBody(
    val username: String,
    val password: String
)

interface NewsreaderService {
    @GET("Articles")
    suspend fun getArticles(
        @Query("count") count: Int? = 20,  // default=20
        @Query("feed") feed: Int? = null,
        @Query("feeds") feeds: String? = null,
        @Query("category") category: String? = null,
        @Header("x-authtoken") authToken: String? = null,
    ): Response<GetArticlesResponse>

    @GET("Articles/{id}")
    suspend fun getArticleById(
        @Path("id") id: Int,
        @Query("count") count: Int? = 20,  // default=1
        @Query("feed") feed: Int? = null,
        @Query("feeds") feeds: String? = null,
        @Query("category") category: String? = null,
        @Header("x-authtoken") authToken: String? = null,
    ): Response<GetArticlesResponse>

    @GET("Articles/liked")
    suspend fun getLikedArticles(
        @Header("x-authtoken") authToken: String? = null,
    ): Response<GetArticlesResponse>

    @PUT("Articles/{id}/like")
    suspend fun likeArticle(
        @Path("id") id: Int,
        @Header("x-authtoken") authToken: String? = null
    ): Response<Void>

    @DELETE("Articles/{id}/like")
    suspend fun removeLikeArticle(
        @Path("id") id: Int,
        @Header("x-authtoken") authToken: String? = null
    ): Response<Void>

    @POST("Users/register")
    suspend fun registerUser(@Body() username: UserRequestBody): Response<RegisterUserResponse>

    @POST("Users/login")
    suspend fun loginUser(@Body() username: UserRequestBody): Response<LoginUserResponse>
}