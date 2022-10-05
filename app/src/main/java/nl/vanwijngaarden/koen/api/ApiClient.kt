package nl.vanwijngaarden.koen.api

import android.util.Log
import nl.vanwijngaarden.koen.api.responses.GetArticlesResponse
import retrofit2.Response

class ApiClient(
    private val newsreaderService: NewsreaderService
) {
    suspend fun getArticles(): CustomResponse<GetArticlesResponse> {
        Log.i("NETWORKCALL", "getArticles")
        return safeCall { newsreaderService.getArticles() }
    }

    suspend fun getArticleById(id: Int): CustomResponse<GetArticlesResponse> {
        Log.i("NETWORKCALL", "getArticlesById")
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