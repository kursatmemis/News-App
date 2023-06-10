package com.kursatmemis.odev_4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.kursatmemis.odev_4.adapters.NewsAdapter
import com.kursatmemis.odev_4.models.News
import com.kursatmemis.odev_4.results.Result

/**
 * Bu Activity, haber başlıklarının ve haber'e ait bir görselin, listview üzerinde
 * gösterilmesinden sorumludur. (Image içerikleri Glide kütüphanesi kullanılarak doldurulmuştur.)
 *
 * Custom bir ArrayAdapter oluşturmak için NewsAdapter class'ı, ArrayAdapter class'ını extend etmiş
 * ve getView methodu override edilerek içeriği doldurulmuştur.
 *
 * Ayrıca, DetailActivity'de kullanılması için 'href' bilgisi bir intent ile hedef Activity'e
 * gönderilmiştir.
 */


class MainActivity : AppCompatActivity() {
    private lateinit var newsListView: ListView
    private var dataSourceNewsListView = mutableListOf<News>() // DataSource for ArrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsListView = findViewById(R.id.newsListView)

        val adapter = NewsAdapter(this, dataSourceNewsListView)
        newsListView.adapter = adapter

        val result = Result()
        val run = Runnable {
            val newsList = result.news()
            runOnUiThread {
                dataSourceNewsListView.addAll(newsList)
                adapter.notifyDataSetChanged()
            }
        }
        Thread(run).start()

        newsListView.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra("news_href", dataSourceNewsListView.get(i).href)
            startActivity(intent)
        }
    }
}