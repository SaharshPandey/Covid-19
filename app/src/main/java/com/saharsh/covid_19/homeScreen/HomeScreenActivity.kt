package com.saharsh.covid_19.homeScreen

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.saharsh.covid_19.AppConstants
import com.saharsh.covid_19.R
import com.saharsh.covid_19.countryList.CountryListActivity
import com.saharsh.covid_19.newsScreen.NewsActivity
import com.saharsh.covid_19.preventionScreen.PreventionActivity
import com.saharsh.covid_19.reportScreen.ReportActivity
import com.saharsh.covid_19.symptomsScreen.SymptomsActivity
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        init()
    }
    private fun init() {
        ll1.setOnClickListener { startActivity(Intent(this@HomeScreenActivity, SymptomsActivity::class.java)) }
        ll2.setOnClickListener { startActivity(Intent(this@HomeScreenActivity, PreventionActivity::class.java)) }
        ll3.setOnClickListener { startActivity(Intent(this@HomeScreenActivity, ReportActivity::class.java)) }
        ll4.setOnClickListener { startActivity(Intent(this@HomeScreenActivity, CountryListActivity::class.java)) }
        newsLayout.setOnClickListener { startActivity(Intent(this@HomeScreenActivity, NewsActivity::class.java)) }
        visitWHO.setOnClickListener {  startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.whoUrl)))
        }
    }
}
