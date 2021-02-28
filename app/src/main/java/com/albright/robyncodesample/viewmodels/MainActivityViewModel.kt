package com.albright.robyncodesample.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albright.robyncodesample.api.ArticleSearch
import com.albright.robyncodesample.data.model.ArticleSearchResult
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    val articles: MutableLiveData<ArticleSearchResult?> by lazy {
        MutableLiveData<ArticleSearchResult?>()
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