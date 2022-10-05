package nl.vanwijngaarden.koen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    // Failed boolean
    private val _failedState = MutableStateFlow(false)
    val failedState: StateFlow<Boolean> = _failedState

    // True when refreshArticles is running
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    init {
        refreshArticles()
    }

    fun refreshArticles() {
        viewModelScope.launch {
            _loadingState.value = true
            _nextId.value = null

            val response = apiClient.getArticles()
            if (articlesState.value.isEmpty()) _failedState.value = !response.isSuccessful
            if (response.isSuccessful) {
                _articlesState.value = Article.fromResponse(response.body)
                _nextId.value = response.body.nextId
            }

            _loadingState.value = false
        }
    }

    fun loadMoreArticles() {
        if (_nextId.value != null) {
            viewModelScope.launch {
                val response = apiClient.getArticleById(_nextId.value!!)
                if (response.isSuccessful) {
                    _articlesState.value = _articlesState.value + Article.fromResponse(response.body)
                    _nextId.value = response.body.nextId
                }
            }
        }
    }
}