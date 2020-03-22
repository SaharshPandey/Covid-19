package com.saharsh.covid_19.countryList.model

data class CountryDataResponse(
    val error: Boolean? = true,
    val statusCode: Int? = 0,
    val message: String? = "",
    val data: CountryData? = CountryData.EMPTY
) {
    companion object {
        val EMPTY = CountryDataResponse()
    }
}

data class CountryData(
    val lastChecked: String? = "",
    val covid19Stats: List<SingleCountryData>? = emptyList()
) {
    companion object {
        val EMPTY = CountryData()
    }
}
