package nl.vanwijngaarden.koen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.vanwijngaarden.koen.api.Network
import nl.vanwijngaarden.koen.models.Article


class SharedViewModel : ViewModel() {
    private val apiClient = Network.apiClient

    // Articles. Empty = not loaded or failed
    private val _articlesState = MutableStateFlow(emptyList<Article>())
    val articlesState: StateFlow<List<Article>> = _articlesState

    // Next Articles Batch Id. Loaded on successful articles request
    private val _nextId = MutableStateFlow<Int?>(null)
    private val _loadingMore = MutableStateFlow<Boolean>(false)

    // Failed boolean
    private val _failedArticlesState = MutableStateFlow(false)
    val failedArticlesState: StateFlow<Boolean> = _failedArticlesState

    // Failed boolean
    private val _failedMoreArticlesState = MutableStateFlow(false)
    val failedMoreArticlesState: StateFlow<Boolean> = _failedMoreArticlesState

    // True when refreshArticles is running
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    // Detail
    private val _detailArticleState = MutableStateFlow<Article?>(null)
    val detailArticle: StateFlow<Article?> = _detailArticleState

    // like fail
    private val _failedLikeState = MutableStateFlow(false)
    val failedLikeState: StateFlow<Boolean> = _failedLikeState

    // favorites page
    private val _favoriteArticlesState = MutableStateFlow(emptyList<Article>())
    val favoriteArticlesState: StateFlow<List<Article>> = _favoriteArticlesState

    private val _failedFavoriteArticlesState = MutableStateFlow(false)
    val failedFavoriteArticlesState: StateFlow<Boolean> = _failedFavoriteArticlesState


    fun setDetailArticle(article: Article) {
        _detailArticleState.value = article
    }

    fun getDetailArticle(id: String, navController: NavController): StateFlow<Article?> {
        // extra check
        if (detailArticle.value == null || detailArticle.value!!.id != id.toInt()) {
            navController.navigateUp()
        }
        return detailArticle
    }

    fun refreshArticles(authToken: String?) {
        viewModelScope.launch {
            _loadingState.value = true
            _nextId.value = null

            val response = apiClient.getArticles(authToken ?: "")
            if (articlesState.value.isEmpty()) _failedArticlesState.value = !response.isSuccessful
            if (response.isSuccessful) {
                _articlesState.value = Article.fromResponse(response.body)

                // load favorites page with some favorites so it's prefilled
                if (_favoriteArticlesState.value.isEmpty()) {
                    _favoriteArticlesState.value = _articlesState.value.filter { it.isLiked.value }
                }
                _nextId.value = response.body.nextId
                _failedArticlesState.value = false
            }

            _loadingState.value = false
        }
    }

    fun loadMoreArticles(authToken: String?) {
        if (_nextId.value != null && !_loadingMore.value) {
            _loadingMore.value = true
            viewModelScope.launch {
                val response = apiClient.getArticleById(authToken, _nextId.value!!)
                _failedMoreArticlesState.value = !response.isSuccessful
                if (response.isSuccessful) {
                    _articlesState.value =
                        _articlesState.value + Article.fromResponse(response.body)
                    _nextId.value = response.body.nextId
                    _failedMoreArticlesState.value = false
                }
                _loadingMore.value = false
            }
        }
    }


    fun likeArticle(authToken: String?, id: Int) {
        _failedLikeState.value = false
        viewModelScope.launch {
            val response = apiClient.likeArticle(authToken, id)
            if (response.isSuccessful) {
                _detailArticleState.value!!.isLiked.value = true
            } else {
                _failedLikeState.value = true
            }
        }
    }

    fun removeLikeArticle(authToken: String?, id: Int) {
        _failedLikeState.value = false
        viewModelScope.launch {
            val response = apiClient.removeLikeArticle(authToken, id)
            if (response.isSuccessful) {
                _detailArticleState.value!!.isLiked.value = false
            } else {
                _failedLikeState.value = true
            }
        }
    }

    fun resetLikeFail() {
        _failedLikeState.value = false
    }

    fun refreshFavoriteArticles(authToken: String?) {
        viewModelScope.launch {
            _loadingState.value = true

            val response = apiClient.getLikedArticles(authToken)
            if (favoriteArticlesState.value.isEmpty()) _failedFavoriteArticlesState.value =
                !response.isSuccessful
            if (response.isSuccessful) {
                _favoriteArticlesState.value = Article.fromResponse(response.body)
                _failedFavoriteArticlesState.value = false
            }

            _loadingState.value = false
        }
    }
}