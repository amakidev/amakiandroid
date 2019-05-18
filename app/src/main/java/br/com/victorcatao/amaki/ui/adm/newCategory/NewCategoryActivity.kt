package br.com.victorcatao.amaki.ui.adm.newCategory

import android.content.Context
import android.os.Bundle
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.utils.extensions.showLongToast
import br.com.victorcatao.amaki.utils.extensions.str
import kotlinx.android.synthetic.main.activity_new_category.*
import org.jetbrains.anko.intentFor

class NewCategoryActivity : BaseActivity(), NewCategoryContract.View {

    private val presenter: NewCategoryContract.Presenter by lazy {
        NewCategoryPresenter().apply {
            attachView(this@NewCategoryActivity)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_category)
        setToolbar(str(R.string.new_category_title), true)
        setupListerners()
        setupSwipeRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupListerners() {
        saveNewCategoryBtn.setOnClickListener { presenter.onClickSave(newCategoryEt.text.toString()) }
    }

    private fun setupSwipeRefresh() {
        swipeRefresh.isEnabled = false
    }

    override fun displayError(msg: String) {
        showLongToast(msg)
    }

    override fun showSuccessMessage() {
        showLongToast("Categoria criada com sucesso!")
        finish()
    }

    override fun displayLoading(loading: Boolean) {
        swipeRefresh.isRefreshing = loading
    }
}

fun Context.createNewCategoryIntent() = intentFor<NewCategoryActivity>()
