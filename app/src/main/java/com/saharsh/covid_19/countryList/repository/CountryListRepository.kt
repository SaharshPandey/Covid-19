package com.saharsh.covid_19.countryList.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.saharsh.covid_19.countryList.backend.CountryListApiFetcher
import com.saharsh.covid_19.countryList.model.CountryDataResponse
import retrofit2.Call
import retrofit2.Response

class CountryListRepository {
    val countryDataResponse: MutableLiveData<CountryDataResponse> = MutableLiveData()

    fun fetchCountryListData(): MutableLiveData<CountryDataResponse> {
        CountryListApiFetcher().getCountryDataList()
            .enqueue(object : retrofit2.Callback<CountryDataResponse> {
                override fun onFailure(call: Call<CountryDataResponse>, t: Throwable) {
                    countryDataResponse.value = CountryDataResponse.EMPTY
                    Log.d("on Failure :", "retrofit error")
                }

                override fun onResponse(
                    call: Call<CountryDataResponse>,
                    response: Response<CountryDataResponse>
                ) {
                    countryDataResponse.value = response.body()
                    Log.d("on Response :", "retrofit success")
                }
            })
        return countryDataResponse
    }
}
