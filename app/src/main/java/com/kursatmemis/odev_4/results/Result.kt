package com.kursatmemis.odev_4.results

import android.util.Log
import com.kursatmemis.odev_4.models.News
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * Bu class, Jsoup kütüphanesi kullanarak url'de belirtilen internet sitesine gider,
 * oradaki haber başlıklarını, resim bağlantılarını ve haber detay bağlantılarını alır.
 *
 * href, title ve src bilgilerinin null veya boş olup olmadığını kontrol eder, eğer hiçbirisi
 * null veya boş değilse, bunları kullanarak bir News objesi oluşturur ve bu objeyi arr listesine
 * ekler. En nihayetinde bu arr listesini, custom olarak hazırlanan adapter'a dataSource olması için
 * MainActivity'e return eder.
 */

class Result {
    fun news(): MutableList<News> {
        val arr = mutableListOf<News>()
        val url = "https://www.haberler.com/"
        val doc: Document = Jsoup.connect(url).timeout(15000).get()
        val elements: Elements = doc.getElementsByAttribute("data-headlinenumber")
        for (item in elements) {

            val img = item.getElementsByTag("img").firstOrNull()
            val href = item.selectFirst("a")?.absUrl("href")
            val src = if (img?.hasAttr("data-src") == true) img.attr("data-src") else img?.attr("src") ?: ""
            val title = img?.attr("alt")

            if (!title.isNullOrBlank() && !href.isNullOrBlank() && !src.isNullOrBlank()) {
                val news = News(title, src, href)
                arr.add(news)
            }
        }
        return arr
    }
}