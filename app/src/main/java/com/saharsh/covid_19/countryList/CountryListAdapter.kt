package com.saharsh.covid_19.countryList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saharsh.covid_19.AppConstants
import com.saharsh.covid_19.R
import com.saharsh.covid_19.countryList.model.SingleCountryData
import kotlinx.android.synthetic.main.country_list_item.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class CountryListAdapter(val context: Context) :
    RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {
    var countryListData: List<SingleCountryData> = ArrayList()
    private var listener: Listener = Listener.NO_OP
    private var lastPos: Int = -1

    fun setItems(countryListData: List<SingleCountryData>) {
        this.countryListData = countryListData
        notifyDataSetChanged()
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.country_list_item, parent, false)
        return CountryListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryListData.size
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        val countryData = countryListData[position]
        holder.itemView.countryName.text = countryData.country
        holder.itemView.province.text = countryData.province
        holder.itemView.tv_confirmed.text = getFormattedNumber(countryData.confirmed!!.toLong())
        holder.itemView.tv_recovered.text = getFormattedNumber(countryData.recovered!!.toLong())
        holder.itemView.tv_death.text = getFormattedNumber(countryData.deaths!!.toLong())

        holder.itemView.itemView.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.bg_color
            )
        )
        holder.itemView.countryName.setTextColor(ContextCompat.getColor(context, R.color.black))

        holder.itemView.itemView.setOnClickListener {
            listener.onClick(countryData)
        }
        Glide.with(context)
            .load(String.format(AppConstants.flagUrl, getCountryCode(countryData.country!!)))
            .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_flag))
            .into(holder.itemView.iv_map)
    }

    private fun getCountryCode(countryName: String) =
        if(countryName == "US") {
            countryName
        } else {
            Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }
        }

    private fun getFormattedNumber(number: Long): String? {
        return NumberFormat.getNumberInstance(Locale.US).format(number)
    }

    class CountryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener {
        fun onClick(countryData: SingleCountryData)

        companion object {
            val NO_OP: Listener = object : Listener {
                override fun onClick(countryData: SingleCountryData) {
                    // NO OP
                }
            }
        }
    }
}
