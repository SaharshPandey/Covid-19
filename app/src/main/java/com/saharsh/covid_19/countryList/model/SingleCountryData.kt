package com.saharsh.covid_19.countryList.model

data class CasesStatus(
    val new: String? = "",
    val active: Int? = 0,
    val critical: Int? = 0,
    val recovered: Int? = 0,
    val total: Int? = 0
) {
    companion object {
        val EMPTY = CasesStatus()
    }
}

data class DeathStatus(
    val new: String? = "",
    val total: Int? = 0
) {
    companion object {
        val EMPTY = DeathStatus()
    }
}
