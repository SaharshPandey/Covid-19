package com.saharsh.covid_19.countryList.model

data class CountryDataResponse(
    val results: Int? = 0,
    val response: List<CountryData>? = emptyList()
) {
    companion object {
        val EMPTY = CountryDataResponse()
    }
}

data class CountryData(
    val country: String? = "",
    val cases: CasesStatus? = CasesStatus.EMPTY,
    val deaths: DeathStatus? = DeathStatus.EMPTY
) {
    companion object {
        val EMPTY = CountryData()
    }
}
