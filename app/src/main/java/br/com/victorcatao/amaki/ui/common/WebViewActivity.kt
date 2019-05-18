package br.com.victorcatao.amaki.ui.common


import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.utils.helpers.FeedbackHelper
import kotlinx.android.synthetic.main.activity_webview.*
import org.jetbrains.anko.intentFor

class WebViewActivity : BaseActivity() {
    private lateinit var mTitle: String
    private lateinit var mUrl: String
    private lateinit var mFeedbackHelper: FeedbackHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        setFeedbacks()
        getExtras()
        loadWebView()
        setToolbar(mTitle, true)
    }

    private fun setFeedbacks() {
        mFeedbackHelper = FeedbackHelper(this, webviewContainer, {
            webview.reload()
        })
    }

    private fun getExtras() {
        mTitle = intent.getStringExtra(Constants.EXTRA_ACTIVITY_TITLE)
        mUrl = intent.getStringExtra(Constants.EXTRA_URL)
    }

    private fun loadWebView() {
        webview.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mFeedbackHelper.dismissFeedback()

            }
        }
        webview.loadUrl(mUrl)
        mFeedbackHelper.startLoading()

    }
}

fun Context.createWebViewIntent(activityTitle: String, url: String) =
        intentFor<WebViewActivity>(Constants.EXTRA_ACTIVITY_TITLE to activityTitle,
                Constants.EXTRA_URL to url)
