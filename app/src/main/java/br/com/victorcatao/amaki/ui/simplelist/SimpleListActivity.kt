package br.com.victorcatao.amaki.ui.simplelist

import android.content.Context
import android.os.Bundle
import android.util.Log
import br.com.victorcatao.amaki.utils.extensions.*
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.PostsResponse
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.ui.loadingactivity.createLoadingActivityIntent
import br.com.victorcatao.amaki.utils.extensions.startActivitySlideTransition
import kotlinx.android.synthetic.main.activity_swiped_recycler_view.*
import org.jetbrains.anko.*

class SimpleListActivity : BaseActivity(), SimpleListContract.View {

    private val presenter: SimpleListContract.Presenter by lazy {
        val presenter = SimpleListPresenter()
        presenter.attachView(this)
        presenter
    }

    private val adapter by lazy {
        SimpleListAdapter(this,
                object : SimpleListAdapter.OnItemClickListener {
                    override fun onItemClicked(item: PostsResponse) {
                        //todo implement view click
                        startActivitySlideTransition(createLoadingActivityIntent())
                    }
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swiped_recycler_view)

        //todo set activity title here
        setToolbar(str(R.string.simple_list_title), false)

        setRecyclerView()

        presenter.loadItems()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun displayItems(items: List<PostsResponse>) {
        adapter.mList = items
    }

    override fun displayError(msg: String?) {
        Log.e(TAG, str(R.string.snack_error_msg))
        showSnack(swipedRecyclerViewCoordinator,
                msg ?: str(R.string.snack_error_msg),
                str(R.string.snack_error_try_again_msg), {
                presenter.loadItems()
        })
    }

    private fun setRecyclerView() {
        swiperefresh.setOnRefreshListener { presenter.loadItems() }
        recyclerview.setup(adapter)
    }

    override fun displayLoading(loading: Boolean) {
        swiperefresh.isRefreshing = loading
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //todo finish with animation if needed
//        finishWithSlideTransition()
    }
}

//todo change the name of this method according to the name of the activity
fun Context.createSimpleListIntent(extra: String? = null) =
        intentFor<SimpleListActivity>(Constants.EXTRA_URL to extra)
