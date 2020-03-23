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
import com.saharsh.covid_19.countryList.model.CountryData
import kotlinx.android.synthetic.main.country_list_item.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class CountryListAdapter(val context: Context) :
    RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {
    var countryListData: List<CountryData> = ArrayList()
    private var listener: Listener = Listener.NO_OP
    private var lastPos: Int = -1

    fun setItems(countryListData: List<CountryData>) {
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
        holder.itemView.tv_confirmed.text =
            getFormattedNumber(countryData.cases?.active?.toLong() ?: 0)
        holder.itemView.tv_recovered.text =
            getFormattedNumber(countryData.cases?.recovered?.toLong() ?: 0)
        holder.itemView.tv_death.text = getFormattedNumber(countryData.deaths?.total?.toLong() ?: 0)

        if(countryData.cases?.new?.substring(1)?.toInt() ?: 0 == 0) {
            holder.itemView.tv_confirmed_new.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
        }
        holder.itemView.tv_confirmed_new.text =
            "(+".plus(getFormattedNumber(countryData.cases?.new?.substring(1)?.toLong() ?: 0) + ")")

        if(countryData.deaths?.new?.substring(1)?.toInt() ?: 0 == 0) {
            holder.itemView.tv_death_new.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
        }
        holder.itemView.tv_death_new.text = "(+".plus(
            getFormattedNumber(
                countryData.deaths?.new?.substring(1)?.toLong() ?: 0
            ) + ")"
        )

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
        when (countryName) {
            "USA" -> {
                "US"
            }
            "UK" -> {
                Locale.getISOCountries().find { Locale("", it).displayCountry == "United Kingdom" }
            }
            "S.-Korea" -> {
                Locale.getISOCountries().find { Locale("", it).displayCountry == "South Korea" }
            }
            "Saudi-Arabia" -> {
                Locale.getISOCountries().find { Locale("", it).displayCountry == "Saudi Arabia" }
            }
            else -> {
                Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }
            }
        }

    private fun getFormattedNumber(number: Long): String? {
        return NumberFormat.getNumberInstance(Locale.US).format(number)
    }

    class CountryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener {
        fun onClick(countryData: CountryData)

        companion object {
            val NO_OP: Listener = object : Listener {
                override fun onClick(countryData: CountryData) {
                    // NO OP
                }
            }
        }
    }
}
