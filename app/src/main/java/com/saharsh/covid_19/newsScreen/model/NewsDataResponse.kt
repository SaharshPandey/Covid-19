package com.saharsh.covid_19.newsScreen.model

data class NewsDataResponse(val status: String? = "",
                            val totalResults: Int? = 0,
                            val articles: List<NewsData>? = emptyList()) {
    companion object {
        val EMPTY = NewsDataResponse()
    }
}
data class NewsData(val author: String? = "",
                    val title: String? = "",
                    val description: String? = "",
                    val url: String? = "",
                    val urlToImage: String? = "",
                    val publishedAt: String? = "",
                    val content: String? = "")
