package nl.vanwijngaarden.koen.api

import nl.vanwijngaarden.koen.api.responses.GetArticlesResponse
import retrofit2.Response

class ApiClient(
    private val newsreaderService: NewsreaderService
) {
    suspend fun getArticles(): CustomResponse<GetArticlesResponse> {
        return safeCall { newsreaderService.getArticles() }
    }

    suspend fun getArticleById(id: Int): CustomResponse<GetArticlesResponse> {
        return safeCall { newsreaderService.getArticleById(id) }
    }

    private inline fun <T> safeCall(apiFn: () -> Response<T>): CustomResponse<T> {
        return try {
            CustomResponse.success(apiFn.invoke())
        } catch (e: Exception) {
            CustomResponse.failure(e)
        }
    }
}