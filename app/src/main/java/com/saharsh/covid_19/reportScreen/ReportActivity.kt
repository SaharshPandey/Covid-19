package com.saharsh.covid_19.reportScreen

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
    private var totalRecovered: Long = 0
    private var totalActive: Long = 0
    private var totalDeaths: Long = 0
    private var totalCases: Long = 0
    private var totalNewCases: Long = 0
    private var totalNewDeaths: Long = 0

    private var totalIndiansRecovered: Long = 0
    private var totalIndiansActive: Long = 0
    private var totalIndiansDeaths: Long = 0
    private var totalIndiansCases: Long = 0
    private var totalIndiansNewCases: Long = 0
    private var totalIndiansNewDeaths: Long = 0
    private var state = TYPE.FIRST


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        setPieChart(totalActive, totalRecovered, totalDeaths, totalCases)
        setUpViewModel()
        setUpListeners()
    }

    private fun init() {
        if(state == TYPE.WORLD) {
            tv_activeCases.text = getFormattedNumber(totalActive)

            if(totalNewCases.toInt() == 0) {
                tv_activeCasesNew.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
            }
            tv_activeCasesNew.text = "(+".plus(getFormattedNumber(totalNewCases) + ")")

            tv_recoveredCases.text = getFormattedNumber(totalRecovered)
            tv_totalCases.text = getFormattedNumber(totalCases)

            tv_deathsCases.text = getFormattedNumber(totalDeaths)
            if(totalNewDeaths.toInt() == 0) {
                tv_deathsCasesNew.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
            }
            tv_deathsCasesNew.text = "(+".plus(getFormattedNumber(totalNewDeaths) + ")")

            setPieChart(totalActive, totalRecovered, totalDeaths, totalCases)
        } else if (state == TYPE.INDIA) {
            tv_activeCases.text = getFormattedNumber(totalIndiansActive)

            if(totalIndiansNewCases.toInt() == 0) {
                tv_activeCasesNew.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
            }
            tv_activeCasesNew.text = "(+".plus(getFormattedNumber(totalIndiansNewCases) + ")")

            tv_recoveredCases.text = getFormattedNumber(totalIndiansRecovered)
            tv_totalCases.text = getFormattedNumber(totalIndiansCases)

            tv_deathsCases.text = getFormattedNumber(totalIndiansDeaths)

            if(totalIndiansNewDeaths.toInt() == 0) {
                tv_deathsCasesNew.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
            }
            tv_deathsCasesNew.text = "(+".plus(getFormattedNumber(totalIndiansNewDeaths) + ")")

            setPieChart(totalIndiansActive, totalIndiansRecovered, totalIndiansDeaths, totalIndiansCases)
        }
    }

    private fun setUpListeners() {
        back.setOnClickListener { finish() }
        bt_world.setOnClickListener {
            if(state != TYPE.WORLD) {
                state = TYPE.WORLD
                stateView(bt_world, bt_india)
                init()
            }
        }

        bt_india.setOnClickListener {
            if(state != TYPE.INDIA) {
                state = TYPE.INDIA
                stateView(bt_india, bt_world)
                init()
            }
        }

        tryAgain.setOnClickListener { setUpViewModel() }
    }

    private fun stateView(selected: TextView, deselected: TextView) {
        selected.backgroundTintList = ContextCompat.getColorStateList(this, R.color.colorAccent)
        selected.setTextColor(ContextCompat.getColor(this, R.color.white))
        if (state == TYPE.WORLD) {
            deselected.background = ContextCompat.getDrawable(this, R.drawable.right_curve_bg)
            deselected.backgroundTintList = null
        } else if (state == TYPE.INDIA) {
            deselected.background = ContextCompat.getDrawable(this, R.drawable.left_curve_bg)
            deselected.backgroundTintList = null
        }
        deselected.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))
    }

    private fun setPieChart(totalActive: Long, totalRecovered: Long, totalDeaths: Long, totalCases: Long) {
        val arrayList: ArrayList<PieEntry> = ArrayList()
        arrayList.add(PieEntry(totalActive.toFloat(), "Active", 0))
        arrayList.add(PieEntry(totalRecovered.toFloat(), "Recovered", 1))
        arrayList.add(PieEntry(totalDeaths.toFloat(), "Deaths", 2))

        val dataSet = PieDataSet(arrayList, "Covid-19 Results")


        val colorFirst = ContextCompat.getColor(this, R.color.confirmed)
        val colorSecond = ContextCompat.getColor(this, R.color.recovered)
        val colorThird = ContextCompat.getColor(this, R.color.deaths)

        try {
            val confirmPercentage = totalActive.toDouble() / totalCases.toDouble() * 100
            val recoveredPercentage = totalRecovered.toDouble() / totalCases.toDouble() * 100
            val deathsPercentage = totalDeaths.toDouble() / totalCases.toDouble() * 100

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
                    if (!result.response.isNullOrEmpty()) {
                        for (data in result.response) {
                            if(data.country?.toLowerCase()?.trim().equals("india")){
                                totalIndiansRecovered += data.cases?.recovered ?: 0
                                totalIndiansActive += data.cases?.active ?: 0
                                totalIndiansDeaths += data.deaths?.total ?: 0
                                totalIndiansCases += data.cases?.total ?: 0
                                totalIndiansNewCases += data.cases?.new?.substring(1)?.toInt() ?: 0
                                totalIndiansNewDeaths += data.deaths?.new?.substring(1)?.toInt() ?: 0
                            }
                            errorScreen.visibility = View.GONE
                            totalRecovered += data.cases?.recovered ?: 0
                            totalActive += data.cases?.active ?: 0
                            totalDeaths += data.deaths?.total ?: 0
                            totalCases += data.cases?.total ?: 0
                            totalNewCases += data.cases?.new?.substring(1)?.toInt() ?: 0
                            totalNewDeaths += data.deaths?.new?.substring(1)?.toInt() ?: 0
                        }
                    } else {
                        errorScreen.visibility = View.VISIBLE
                    }
                }
                totalRecovered /= 2
                totalActive /= 2
                totalDeaths /= 2
                totalCases /= 2
                totalNewCases /= 2
                totalNewDeaths /= 2

                maskLoader.visibility = View.GONE
                statusLayout.visibility = View.VISIBLE
                selector.visibility = View.VISIBLE
                bt_world.isEnabled = true
                bt_india.isEnabled = true
                bt_world.callOnClick()
            })
    }

    private fun getFormattedNumber(number: Long): String? {
        return NumberFormat.getNumberInstance(Locale.US).format(number)
    }

    enum class TYPE {
        FIRST,
        WORLD,
        INDIA
    }
}
