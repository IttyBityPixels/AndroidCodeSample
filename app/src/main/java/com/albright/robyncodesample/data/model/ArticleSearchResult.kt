package com.albright.robyncodesample.data.model

data class ArticleSearchResult(val articles: List<ArticleSnippet>)

data class ArticleSnippet(
    val id: Long,
    val url: String,
    val title: String,
    val abstract: String
)
