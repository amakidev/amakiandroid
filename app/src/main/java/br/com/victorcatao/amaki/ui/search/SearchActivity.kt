package br.com.victorcatao.amaki.ui.search

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.SearchModel
import br.com.victorcatao.amaki.data.remote.models.SearchResult
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.utils.extensions.setup
import br.com.victorcatao.amaki.utils.extensions.str
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.intentFor


class SearchActivity : BaseActivity(), SearchContract.View {

    private val presenter: SearchContract.Presenter by lazy {
        SearchPresenter().apply {
            attachView(this@SearchActivity)
        }
    }

    private val adapter by lazy {
        SearchListAdapter(this,
                object : SearchListAdapter.OnItemClickListener {
                    override fun onItemClicked(item: SearchModel) {
                        presenter.onClickItem(item)
                    }
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setToolbar(str(R.string.search_title), true)

        setupListeners()
        setupRecycler()
        presenter.setData(intent.getSerializableExtra(Constants.EXTRA_URL) as List<SearchModel>, intent.getBooleanExtra(Constants.EXTRA_ACTIVITY_TITLE, false))
    }

    private fun setupListeners() {
        okButton.setOnClickListener {
            finishForResult()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    fun setupRecycler() {
        recyclerview.setup(adapter)
    }

    override fun showOptions(options: List<SearchModel>) {
        adapter.mList = options
    }

    override fun finishForResult() {
        val i = Intent()
        i.putExtra(Constants.EXTRA_SEARCH, SearchResult(adapter.mList))
        setResult(100, i)
        finish()
    }
}

fun Context.createSearchIntent(extra: List<SearchModel>? = null, multipleChoices: Boolean) =
        intentFor<SearchActivity>(Constants.EXTRA_URL to extra, Constants.EXTRA_ACTIVITY_TITLE to multipleChoices)

