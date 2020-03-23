package com.saharsh.covid_19.countryList.backend

import com.saharsh.covid_19.countryList.model.CountryDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface  CountryListInterface {
    @GET("/statistics")
    fun fetch(@Header("x-rapidapi-host") host:String,
              @Header("x-rapidapi-key") apiKey:String): Call<CountryDataResponse>
}
