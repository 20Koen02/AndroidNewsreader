package nl.vanwijngaarden.koen.api

import nl.vanwijngaarden.koen.api.responses.GetArticlesResponse
import nl.vanwijngaarden.koen.api.responses.LoginUserResponse
import nl.vanwijngaarden.koen.api.responses.RegisterUserResponse
import retrofit2.Response

class ApiClient(
    private val newsreaderService: NewsreaderService
) {
    suspend fun getArticles(authToken: String?): CustomResponse<GetArticlesResponse> {
        return safeCall { newsreaderService.getArticles(authToken = authToken?.ifEmpty { null }) }
    }

    suspend fun getArticleById(authToken: String?, id: Int): CustomResponse<GetArticlesResponse> {
        return safeCall { newsreaderService.getArticleById(id, authToken = authToken?.ifEmpty { null }) }
    }

    suspend fun getLikedArticles(authToken: String?): CustomResponse<GetArticlesResponse> {
        return safeCall { newsreaderService.getLikedArticles(authToken = authToken?.ifEmpty { null }) }
    }

    suspend fun registerUser(
        username: String,
        password: String
    ): CustomResponse<RegisterUserResponse> {
        return safeCall { newsreaderService.registerUser(UserRequestBody(username, password)) }
    }

    suspend fun loginUser(username: String, password: String): CustomResponse<LoginUserResponse> {
        return safeCall { newsreaderService.loginUser(UserRequestBody(username, password)) }
    }

    suspend fun likeArticle(authToken: String?, id: Int): CustomResponse<Void> {
        return safeCall { newsreaderService.likeArticle(id, authToken = authToken?.ifEmpty { null }) }
    }

    suspend fun removeLikeArticle(authToken: String?, id: Int): CustomResponse<Void> {
        return safeCall { newsreaderService.removeLikeArticle(id, authToken = authToken?.ifEmpty { null }) }
    }

    private inline fun <T> safeCall(apiFn: () -> Response<T>): CustomResponse<T> {
        return try {
            CustomResponse.success(apiFn.invoke())
        } catch (e: Exception) {
            CustomResponse.failure(e)
        }
    }
}