package com.saharsh.covid_19.newsScreen.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.saharsh.covid_19.newsScreen.backend.NewsApiFetcher
import com.saharsh.covid_19.newsScreen.model.NewsDataResponse
import retrofit2.Call
import retrofit2.Response

class NewsDataRepository {
    val newsDataResponse: MutableLiveData<NewsDataResponse> = MutableLiveData()

    fun fetchCountryListData(language: String, search: String): MutableLiveData<NewsDataResponse> {
        NewsApiFetcher().fetchNewsApi(language,search)
            .enqueue(object : retrofit2.Callback<NewsDataResponse> {
                override fun onFailure(call: Call<NewsDataResponse>, t: Throwable) {
                    newsDataResponse.value = NewsDataResponse.EMPTY
                    Log.d("on Failure :", "retrofit error")
                }

                override fun onResponse(
                    call: Call<NewsDataResponse>,
                    response: Response<NewsDataResponse>
                ) {
                    newsDataResponse.value = response.body()
                    Log.d("on Response :", "retrofit success")
                }
            })
        return newsDataResponse
    }
}
