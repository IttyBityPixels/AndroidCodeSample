package com.albright.robyncodesample.api

import com.albright.robyncodesample.data.model.ArticleSearchResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl("https://api.nytimes.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ArticleSearchService {
    @GET("svc/mostpopular/v2/viewed/1.json?api-key=X4mvftFx8StLy3iYMn0SU28Nt0eOKRdt")
    suspend fun getSevenDayPopularArticles(): ArticleSearchResult
}

object ArticleSearch {
    val retrofitService: ArticleSearchService by lazy { retrofit.create(ArticleSearchService::class.java) }
}