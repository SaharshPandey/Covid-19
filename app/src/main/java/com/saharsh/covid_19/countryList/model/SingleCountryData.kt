package com.saharsh.covid_19.countryList.model

data class SingleCountryData(val province: String? = "",
                             val country: String? = "",
                             val lastUpdate: String? = "",
                             val confirmed: String? = "",
                             val deaths: String? = "",
                             val recovered: String? = "")
