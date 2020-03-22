package com.saharsh.covid_19.newsScreen.backend

import com.saharsh.covid_19.AppConstants
import com.saharsh.covid_19.newsScreen.model.NewsDataResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NewsApiFetcher {
    private var newApiInterface: NewsApiInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        newApiInterface = retrofit.create(NewsApiInterface::class.java)
    }

    fun fetchNewsApi(language: String, search: String): Call<NewsDataResponse> {
        return newApiInterface.fetch(search, language, AppConstants.newsApiKey)
    }
}
