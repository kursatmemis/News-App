package com.kursatmemis.odev_4.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kursatmemis.odev_4.R
import com.kursatmemis.odev_4.models.News

class NewsAdapter(
    val context: AppCompatActivity,
    val newsDataSource: MutableList<News>
) : ArrayAdapter<News>(context, R.layout.custom_news_design, newsDataSource) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val customNewsDesignLayout = inflater.inflate(R.layout.custom_news_design, null)
        val newsImageImageView = customNewsDesignLayout.findViewById<ImageView>(R.id.newsImageImageView)
        val newsTitleTextView = customNewsDesignLayout.findViewById<TextView>(R.id.newsTitleTextView)
        val news = newsDataSource.get(position)
        Glide.with(context).load(news.img).into(newsImageImageView)
        newsTitleTextView.text = news.title
        return customNewsDesignLayout
    }


}