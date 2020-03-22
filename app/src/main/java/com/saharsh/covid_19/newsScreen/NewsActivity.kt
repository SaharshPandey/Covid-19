package com.saharsh.covid_19.newsScreen

import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.saharsh.covid_19.R
import com.saharsh.covid_19.newsScreen.model.NewsData
import com.saharsh.covid_19.newsScreen.model.NewsDataResponse
import com.saharsh.covid_19.newsScreen.viewmodel.NewsDataViewModel
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_news.errorScreen
import kotlinx.android.synthetic.main.error_screen.*

class NewsActivity : AppCompatActivity(), NewsListAdapter.Listener {

    var newsListData: MutableList<NewsData> = ArrayList()
    var newsListAdapter: NewsListAdapter = NewsListAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setUpRecyclerView()
        setUpViewModel()
        init()
    }

    private fun init() {
        back.setOnClickListener { finish() }
        tryAgain.setOnClickListener { setUpViewModel() }
    }
    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = newsListAdapter
        newsListAdapter.setListener(this)
    }

    private fun setUpViewModel() {
        loadingScreen.visibility = View.VISIBLE
        val newListViewModel =
            ViewModelProviders.of(this@NewsActivity).get(NewsDataViewModel::class.java)
        newListViewModel.fetchNewsDataList("en","covid")

        newListViewModel.responseLiveData.observe(
            this,
            Observer<NewsDataResponse> { result ->
                run {
                    newsListData.clear()
                    if (result.articles != null) {
                        newsListData.addAll(result.articles)
                    }
                }
                loadingScreen.visibility = View.GONE
                if(!newsListData.isNullOrEmpty()) {
                    errorScreen.visibility = View.GONE
                    newsListAdapter.setItems(newsListData)
                } else {
                    errorScreen.visibility = View.VISIBLE
                }
            })

    }

    override fun onClick(newsData: NewsData) {
        //Sending Intent to Browser...
        if(Patterns.WEB_URL.matcher(newsData.url.toString()).matches())
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(newsData.url)))
    }
}
