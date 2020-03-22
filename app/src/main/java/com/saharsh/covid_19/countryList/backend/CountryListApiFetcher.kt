package com.saharsh.covid_19.countryList.backend

import com.saharsh.covid_19.AppConstants
import com.saharsh.covid_19.countryList.model.CountryDataResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CountryListApiFetcher {
    private var countryListInterface: CountryListInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://covid-19-coronavirus-statistics.p.rapidapi.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        countryListInterface = retrofit.create(CountryListInterface::class.java)
    }

    fun getCountryDataList(): Call<CountryDataResponse> {
        return countryListInterface.fetch(AppConstants.host, AppConstants.key)
    }
}
