package com.saharsh.covid_19.newsScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saharsh.covid_19.R
import com.saharsh.covid_19.newsScreen.model.NewsData
import kotlinx.android.synthetic.main.news_list_item.view.*

class NewsListAdapter(val context: Context) :
    RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    var newsDataList: List<NewsData> = ArrayList()
    private var listener: Listener = Listener.NO_OP

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setItems(newsDataList: List<NewsData>) {
        this.newsDataList = newsDataList
        notifyDataSetChanged()
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.news_list_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsDataList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        if (!newsDataList[position].title.isNullOrEmpty() && !newsDataList[position].urlToImage.isNullOrEmpty()) {
            Glide.with(context).load(newsDataList[position].urlToImage)
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_newspaper))
                .into(holder.itemView.news_image)
            holder.itemView.news_title.text = newsDataList[position].title
            holder.itemView.news_time.text = newsDataList[position].publishedAt?:""
        }
        holder.itemView.setOnClickListener {
            listener.onClick(newsDataList[position])
        }
    }

   /* @SuppressLint("SimpleDateFormat")
    private fun formatDateTime(time: String): String {
        val dataTime = time.substring(0, 18)
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat("dd MMM, hh:mm:ss a")
        if (dataTime.isNotEmpty()) {
            val date: Date = inputFormat.parse(dataTime)
            val formattedDate: String = outputFormat.format(date)
            return formattedDate
        }
        return time
    }
*/
    interface Listener {
        fun onClick(newsData: NewsData)

        companion object {
            val NO_OP: Listener = object : Listener {
                override fun onClick(newsData: NewsData) {
                    // NO OP
                }
            }
        }
    }

}
