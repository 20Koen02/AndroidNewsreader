package nl.vanwijngaarden.koen.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.vanwijngaarden.koen.api.CustomResponse
import nl.vanwijngaarden.koen.api.Network
import nl.vanwijngaarden.koen.api.responses.GetArticlesResponse

class SharedViewModel : ViewModel() {
    private val apiClient = Network.apiClient

    private val _articlesLiveData = MutableLiveData<CustomResponse<GetArticlesResponse>>()
    val articlesLiveData: LiveData<CustomResponse<GetArticlesResponse>> = _articlesLiveData

    private val _loadingLD = MutableLiveData(false)
    val loadingLD: LiveData<Boolean> = _loadingLD

    fun refreshArticles() {
        viewModelScope.launch {
            Log.i("SharedViewModel", "Refreshing Articles")
            _loadingLD.value = true
            val response = apiClient.getArticles()
            _articlesLiveData.postValue(response)
            _loadingLD.value = false
        }
    }
}