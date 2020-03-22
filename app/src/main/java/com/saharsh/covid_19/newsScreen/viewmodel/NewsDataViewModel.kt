package com.saharsh.covid_19.newsScreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.saharsh.covid_19.newsScreen.model.NewsDataResponse
import com.saharsh.covid_19.newsScreen.repository.NewsDataRepository

class NewsDataViewModel : ViewModel() {
    private var newsRepository: NewsDataRepository = NewsDataRepository()
    lateinit var responseLiveData: LiveData<NewsDataResponse>

    fun fetchNewsDataList(language: String, search: String) {
        responseLiveData = newsRepository.fetchCountryListData(language, search)
    }
}
