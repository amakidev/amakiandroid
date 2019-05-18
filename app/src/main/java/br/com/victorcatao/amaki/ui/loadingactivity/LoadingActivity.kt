package br.com.victorcatao.amaki.ui.loadingactivity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import br.com.victorcatao.amaki.utils.extensions.str
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.PostsResponse
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.utils.extensions.finishWithSlideTransition
import br.com.victorcatao.amaki.utils.extensions.showSnack
import kotlinx.android.synthetic.main.activity_loading.*
import org.jetbrains.anko.intentFor

class LoadingActivity : BaseActivity(), LoadingContract.View {

    private val presenter: LoadingContract.Presenter by lazy {
        val presenter = LoadingPresenter()
        presenter.attachView(this)
        presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //todo set your layout, use the activity_loading as a base
        setContentView(R.layout.activity_loading)

        //todo set activity title here
        setToolbar(str(R.string.loading_title), true)

        setSwipeRefresh()

        presenter.loadItem()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun displayItem(item: PostsResponse) {
        setData(item)
    }

    private fun setData(item: PostsResponse) {
        //todo set the data to your activity here
        val text = TextView(this)
        text.text = item.body
        container.addView(text)
    }

    override fun displayError(msg: String?) {
        Log.e(TAG, msg)
        showSnack(loadingViewCoordinator,
                msg ?: str(R.string.snack_error_msg),
                str(R.string.snack_error_try_again_msg), {
                    presenter.loadItem()
                })
    }

    private fun setSwipeRefresh() {
        swiperefresh.setOnRefreshListener { presenter.loadItem() }
    }

    override fun displayLoading(loading: Boolean) {
        swiperefresh.isRefreshing = loading
        swiperefresh.isEnabled = loading
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //todo finish with animation if necessary
        finishWithSlideTransition()
    }
}

//todo change the name of this method according to the name of the activity
fun Context.createLoadingActivityIntent() = intentFor<LoadingActivity>()

