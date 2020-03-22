package com.saharsh.covid_19.countryList

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.saharsh.covid_19.R
import com.saharsh.covid_19.countryList.model.CountryDataResponse
import com.saharsh.covid_19.countryList.model.SingleCountryData
import com.saharsh.covid_19.countryList.viewmodel.CountryListViewModel
import kotlinx.android.synthetic.main.activity_country_list.*
import kotlinx.android.synthetic.main.activity_country_list.errorScreen
import kotlinx.android.synthetic.main.error_screen.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CountryListActivity : AppCompatActivity(), CountryListAdapter.Listener {

    var countryListData: MutableList<SingleCountryData> = ArrayList()
    var countryListAdapter: CountryListAdapter = CountryListAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)
        init()
        setUpViewModel()
        setUpRecyclerView()
    }

    private fun init() {
        back.setOnClickListener { finish() }
        tryAgain.setOnClickListener { setUpViewModel() }
        iv_search.setOnClickListener {
            et_search.hideKeyboard()
        }
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchSpecific()
            }

        })
    }

    private fun searchSpecific() {
        if (!countryListData.isNullOrEmpty()) {
            val searchListData: MutableList<SingleCountryData> = ArrayList()
            countryListData.forEach {
                if(it.country!!.toLowerCase().contains(et_search.text.toString().toLowerCase())) {
                    searchListData.add(it)
                }
            }
            countryListAdapter.setItems(searchListData)
        }
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = countryListAdapter
        countryListAdapter.setListener(this)
    }

    private fun setUpViewModel() {
        val countryListViewModel =
            ViewModelProviders.of(this@CountryListActivity).get(CountryListViewModel::class.java)
        loadingScreen.visibility = View.VISIBLE
        countryListViewModel.fetchCountryDataList()

        countryListViewModel.responseLiveData.observe(
            this,
            Observer<CountryDataResponse> { result ->
                run {
                    countryListData.clear()
                    if (result.statusCode == 200 && result.data?.covid19Stats != null) {
                        countryListData.addAll(result.data.covid19Stats)
                    }
                }
                loadingScreen.visibility = View.GONE
                if(!countryListData.isNullOrEmpty()) {
                    searchSpecific()
                    errorScreen.visibility = View.GONE
                } else {
                    errorScreen.visibility = View.VISIBLE
                }
            })

    }

    override fun onClick(countryData: SingleCountryData) {
        /*country_name.text = countryData.country
        val arrayList: ArrayList<PieEntry> = ArrayList()
        arrayList.add(PieEntry(countryData.confirmed!!.toFloat(), 0))
        arrayList.add(PieEntry(countryData.recovered!!.toFloat(), 1))
        arrayList.add(PieEntry(countryData.deaths!!.toFloat(), 2))

        val dataSet = PieDataSet(arrayList, "Covid-19 Results")

        val colorFirst = ContextCompat.getColor(this, R.color.confirmed)
        val colorSecond = ContextCompat.getColor(this, R.color.recovered)
        val colorThird = ContextCompat.getColor(this, R.color.deaths)

        confirm_percentage.text = getFormattedNumber(countryData.confirmed.toLong())
        recovered_percentage.text = getFormattedNumber(countryData.recovered.toLong())
        death_percentage.text = getFormattedNumber(countryData.deaths.toLong())

        dataSet.setDrawValues(false)
        dataSet.colors = mutableListOf(colorFirst, colorSecond, colorThird)
        pieChart.data = PieData(dataSet)
        pieChart.legend.isEnabled = false
        pieChart.setDrawSliceText(false)
        pieChart.description = null
        pieChart.holeRadius = 70f

        pieChart.animateXY(2000, 2000)*/
    }

    private fun getFormattedNumber(number: Long): String? {
        return NumberFormat.getNumberInstance(Locale.US).format(number)
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
