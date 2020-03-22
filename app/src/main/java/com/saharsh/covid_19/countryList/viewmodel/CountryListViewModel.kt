package com.saharsh.covid_19.countryList.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.saharsh.covid_19.countryList.CountryListRepository
import com.saharsh.covid_19.countryList.model.CountryDataResponse

class CountryListViewModel: ViewModel() {
    private var countryListRepository: CountryListRepository = CountryListRepository()
    lateinit var responseLiveData: LiveData<CountryDataResponse>

    fun fetchCountryDataList() {
        responseLiveData = countryListRepository.fetchCountryListData()
    }
}
