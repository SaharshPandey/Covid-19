package com.saharsh.covid_19.reportScreen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.saharsh.covid_19.R
import com.saharsh.covid_19.countryList.model.CountryDataResponse
import com.saharsh.covid_19.countryList.viewmodel.CountryListViewModel
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.activity_report.errorScreen
import kotlinx.android.synthetic.main.error_screen.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class ReportActivity : AppCompatActivity() {
    var totalRecovered: Long = 0
    var totalConfirmed: Long = 0
    var totalDeaths: Long = 0
    var totalCases: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        setPieChart()
        setUpViewModel()
    }

    private fun init() {
        tv_confirmedCases.text = getFormattedNumber(totalConfirmed)
        tv_recoveredCases.text = getFormattedNumber(totalRecovered)
        tv_totalCases.text = getFormattedNumber(totalCases)
        tv_deathsCases.text = getFormattedNumber(totalDeaths)
        back.setOnClickListener { finish() }
        tryAgain.setOnClickListener { setUpViewModel() }
    }

    private fun setPieChart() {
        val arrayList: ArrayList<PieEntry> = ArrayList()
        arrayList.add(PieEntry(totalConfirmed.toFloat(), "Confirmed", 0))
        arrayList.add(PieEntry(totalRecovered.toFloat(), "Recovered",1))
        arrayList.add(PieEntry(totalDeaths.toFloat(), "Deaths", 2))

        val dataSet = PieDataSet(arrayList, "Covid-19 Results")

        val colorFirst = ContextCompat.getColor(this, R.color.confirmed)
        val colorSecond = ContextCompat.getColor(this, R.color.recovered)
        val colorThird = ContextCompat.getColor(this, R.color.deaths)

        try {
            val confirmPercentage = totalConfirmed.toDouble()/totalCases.toDouble()*100
            val recoveredPercentage = totalRecovered.toDouble()/totalCases.toDouble()*100
            val deathsPercentage = totalDeaths.toDouble()/totalCases.toDouble()*100

            confirm_percentage.text = String.format("%.2f", confirmPercentage).plus("%")
            recovered_percentage.text = String.format("%.2f", recoveredPercentage).plus("%")
            death_percentage.text = String.format("%.2f", deathsPercentage).plus("%")
        } catch (e: Exception) {
            e.printStackTrace()
        }


        dataSet.setDrawValues(false)
        dataSet.colors = mutableListOf(colorFirst, colorSecond, colorThird)
        pieChart.data = PieData(dataSet)
        pieChart.legend.isEnabled = false


        pieChart.setDrawSliceText(false)
        pieChart.description = null
        pieChart.holeRadius = 70f

        pieChart.animateXY(2000, 2000)
    }

    private fun setUpViewModel() {
        val countryListViewModel =
            ViewModelProviders.of(this@ReportActivity).get(CountryListViewModel::class.java)
        countryListViewModel.fetchCountryDataList()
        maskLoader.visibility = View.VISIBLE

        countryListViewModel.responseLiveData.observe(
            this,
            Observer<CountryDataResponse> { result ->
                run {
                    if (result.statusCode == 200 && result.data?.covid19Stats != null) {
                        for (data in result.data.covid19Stats) {
                            errorScreen.visibility = View.GONE
                            totalRecovered += data.recovered!!.toLong()
                            totalConfirmed += data.confirmed!!.toLong()
                            totalDeaths += data.deaths!!.toLong()
                            totalCases = totalRecovered + totalDeaths + totalConfirmed
                        }
                    } else {
                        errorScreen.visibility = View.VISIBLE
                    }
                }
                maskLoader.visibility = View.GONE
                pie_hint.visibility = View.VISIBLE
                init()
                setPieChart()
            })
    }
    private fun getFormattedNumber(number: Long): String? {
        return NumberFormat.getNumberInstance(Locale.US).format(number)
    }
}
