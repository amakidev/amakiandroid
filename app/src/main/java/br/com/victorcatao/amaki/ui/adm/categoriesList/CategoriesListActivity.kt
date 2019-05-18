package br.com.victorcatao.amaki.ui.adm.categoriesList

import android.content.Context
import android.os.Bundle
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.CategoryResponse
import br.com.victorcatao.amaki.data.remote.models.EstablishmentResponse
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.ui.home.EstablishmentListAdapter
import br.com.victorcatao.amaki.utils.extensions.setup
import br.com.victorcatao.amaki.utils.extensions.showLongToast
import br.com.victorcatao.amaki.utils.extensions.showToast
import br.com.victorcatao.amaki.utils.extensions.str
import kotlinx.android.synthetic.main.activity_categories_list.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.okButton


class CategoriesListActivity : BaseActivity(), CategoriesListContract.View {

    private val presenter: CategoriesListContract.Presenter by lazy {
        val presenter = CategoriesListPresenter()
        presenter.attachView(this)
        presenter
    }

    private val adapter by lazy {
        CategoriesListAdapter(this,
                object : CategoriesListAdapter.OnItemClickListener {
                    override fun onItemClicked(item: CategoryResponse) {
//                        openContact(item)
                    }
                },
                object : CategoriesListAdapter.OnItemLongClickListener {
                    override fun onItemLongClicked(item: CategoryResponse) {
                        showDeleteAlert(item._id ?: "-")
                    }
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_list)
        setToolbar("Categorias", true)

        setSwipeRefresh()
        setRecyclerView()
        setupListeners()
        presenter.loadCategories()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupListeners() {

    }

    private fun setRecyclerView() {
        recyclerview.setup(adapter)
    }

    override fun displayItems(items: List<CategoryResponse>) {
        adapter.mList = items
    }


    override fun displayError(msg: String?) {
        showLongToast(msg ?: "Erro desconhecido")
    }

    private fun setSwipeRefresh() {
        swiperefresh.setOnRefreshListener { presenter.loadCategories() }
    }

    override fun displayLoading(loading: Boolean) {
        swiperefresh.isRefreshing = loading
        swiperefresh.isEnabled = loading
    }


    fun showDeleteAlert(id: String) {
        alert {
            title = "ATENÇÃO!"
            message = "Tem certeza que deseja DELETAR esta categoria? Essa ação não poderá ser desfeita."
            okButton {
                presenter.onPressDeleteCategory(id)
            }
            cancelButton {  }
        }.show()
    }

    override fun displayDeleteSuccess() {
        showToast("Categoria deletada com sucesso!")
    }
}

fun Context.createCategoriesListIntent(extra: String? = null) =
        intentFor<CategoriesListActivity>(Constants.EXTRA_URL to extra)
