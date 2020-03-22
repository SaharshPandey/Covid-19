package com.saharsh.covid_19.countryList.model

data class CountryDataResponse(
    val error: Boolean? = true,
    val statusCode: Int?,
    val message: String? = "",
    val data: CountryData
)

data class CountryData(
    val lastChecked: String? = "",
    val covid19Stats: List<SingleCountryData>? = emptyList()
)
