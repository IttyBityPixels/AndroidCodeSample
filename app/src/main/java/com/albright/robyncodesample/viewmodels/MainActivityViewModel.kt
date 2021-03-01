package com.albright.robyncodesample.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albright.robyncodesample.api.ArticleSearch
import com.albright.robyncodesample.data.model.ArticleSnippet
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    val articles: MutableLiveData<List<ArticleSnippet>> by lazy {
        MutableLiveData<List<ArticleSnippet>>()
    }

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            try {
                val response = ArticleSearch.retrofitService.getSevenDayPopularArticles()

                if (response.status == "OK" && response.numResults > 0)
                    articles.value = response.results.take(5)
            } catch (e: Exception) { }
        }
    }
}