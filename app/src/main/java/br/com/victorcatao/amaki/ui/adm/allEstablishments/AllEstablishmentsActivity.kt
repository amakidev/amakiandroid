package br.com.victorcatao.amaki.ui.adm.allEstablishments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.util.Log
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.*
import br.com.victorcatao.amaki.ui.adm.newEstablishment.createNewEstablishmentIntent
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.ui.home.EstablishmentListAdapter
import br.com.victorcatao.amaki.utils.extensions.setup
import br.com.victorcatao.amaki.utils.extensions.showLongToast
import br.com.victorcatao.amaki.utils.extensions.str
import kotlinx.android.synthetic.main.activity_all_establishments.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.okButton

class AllEstablishmentsActivity : BaseActivity(), AllEstablishmentsContract.View {

    private val presenter: AllEstablishmentsContract.Presenter by lazy {
        val presenter = AllEstablishmentsPresenter()
        presenter.attachView(this)
        presenter
    }

    private val adapter by lazy {
        EstablishmentListAdapter(this,
                object : EstablishmentListAdapter.OnItemClickListener {
                    override fun onItemClicked(item: EstablishmentResponse) {
                        presenter.onClickEstablishment(item)
                    }
                },
                object : EstablishmentListAdapter.OnItemLongClickListener {
                    override fun onItemLongClicked(item: EstablishmentResponse) {
                        showDeleteAlert(item._id ?: "-")
                    }
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_establishments)
        setToolbar(str(R.string.all_establishments_title), true)

        setSwipeRefresh()
        setRecyclerView()
        setupListeners()
        presenter.loadEstablishments()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupListeners() {
        searchView.queryHint = "Pesquisar"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.onSearch(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })
    }

    fun showDeleteAlert(id: String) {
        alert {
            title = "ATENÇÃO!"
            message = "Tem certeza que deseja DELETAR este estabelecimento? Essa ação não poderá ser desfeita."
            okButton {
                presenter.onPressDeleteEstalbishment(id)
            }
            cancelButton {  }
        }.show()
    }

    private fun setRecyclerView() {
        recyclerview.setup(adapter)
    }

    override fun displayItems(item: List<EstablishmentResponse>) {
        adapter.mList = item
    }

    override fun openEstablishmentActivity(establishment: EstablishmentResponse) {
        startActivity(createNewEstablishmentIntent(establishment))
    }


    override fun displayError(msg: String?) {
        Log.e(TAG, msg)
        showLongToast(msg ?: "Erro desconhecido")
    }

    private fun setSwipeRefresh() {
        swiperefresh.setOnRefreshListener { presenter.loadEstablishments() }
    }

    override fun displayLoading(loading: Boolean) {
        swiperefresh.isRefreshing = loading
        swiperefresh.isEnabled = loading
    }

}


fun Context.createAllEstablishmentsIntent(extra: String? = null) =
        intentFor<AllEstablishmentsActivity>(Constants.EXTRA_URL to extra)