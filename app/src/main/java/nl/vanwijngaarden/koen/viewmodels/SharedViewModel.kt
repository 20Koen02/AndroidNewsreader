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

    init {
        refreshArticles()
    }

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

    fun refreshArticles() {
        viewModelScope.launch {
            _loadingState.value = true
            _nextId.value = null

            val response = apiClient.getArticles()
            if (articlesState.value.isEmpty()) _failedArticlesState.value = !response.isSuccessful
            if (response.isSuccessful) {
                _articlesState.value = Article.fromResponse(response.body)
                _nextId.value = response.body.nextId
                _failedArticlesState.value = false
            }

            _loadingState.value = false
        }
    }

    fun loadMoreArticles() {
        if (_nextId.value != null && !_loadingMore.value) {
            _loadingMore.value = true
            viewModelScope.launch {
                val response = apiClient.getArticleById(_nextId.value!!)
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
}