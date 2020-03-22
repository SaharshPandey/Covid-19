package com.saharsh.covid_19.newsScreen.backend

import com.saharsh.covid_19.newsScreen.model.NewsDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {
    @GET("/v2/top-headlines")
    fun fetch(@Query("q") search:String,
              @Query("language") language:String,
              @Query("apiKey") apiKey:String): Call<NewsDataResponse>
}

