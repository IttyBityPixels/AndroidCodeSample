package com.albright.robyncodesample.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albright.robyncodesample.api.ArticleSearch
import com.albright.robyncodesample.data.model.ArticleSearchResponse
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    val articles: MutableLiveData<ArticleSearchResponse?> by lazy {
        MutableLiveData<ArticleSearchResponse?>()
    }

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            try {
                articles.value = ArticleSearch.retrofitService.getSevenDayPopularArticles()
            } catch (e: Exception) {
                articles.value = null
            }
        }
    }
}