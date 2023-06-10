package com.kursatmemis.odev_4

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.shashank.sony.fancytoastlib.FancyToast

/**
 * Bu Activity, MainActivity üzerinde çalışan listview'daki bir habere tıklandığında haber detayının
 * gösterilmesinden sorumludur.
 *
 * MainActivity'den gönderilen 'href' bilgisi, intent kullanılarak alınmış ve bu href kullanılarak
 * webview üzerinde çalışacak olan internet sitesi set edilmiştir..
 *
 * Ayrıca WebViewClient class'ındaki temel methodlar override edilmiş ve bu methodların içeriği
 * FancyToast kütüphanesi kullanılarak kullanıcıya gerekli bilgilerin gösterilmesi için doldurulmuştur.
 *
 * Son olarak onBackPressed methodu override edilerek, kullanıcının web sayfasında gezinirken
 * back tuşuna bastığında mevcut activity'nin direkt olarak destroy edilmesi engellenmiş ve eğer
 * internet sitesinde daha önce gezinilen sayfa varsa o sayfaya geri dönmesi sağlanmıştır.
 */

class DetailActivity : AppCompatActivity() {
    private lateinit var newsDetailWebView: WebView
    private var isPageLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        newsDetailWebView = findViewById(R.id.newsDetailWebView)
        newsDetailWebView.settings.javaScriptEnabled = true
        newsDetailWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (!isPageLoaded) {
                    isPageLoaded = true
                    FancyToast.makeText(
                        this@DetailActivity,
                        "Haber başarıyla yüklendi!",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.INFO,
                        true
                    ).show()
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                FancyToast.makeText(
                    this@DetailActivity,
                    "Haber yüklenmesi sırasında bir sorun oluştu!",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR,
                    true
                ).show()
            }
        }
        val href = intent.getStringExtra("news_href")
        newsDetailWebView.loadUrl(href!!)
    }
    override fun onBackPressed() {
        if (newsDetailWebView.canGoBack()) {
            newsDetailWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}