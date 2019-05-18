package br.com.victorcatao.amaki.ui.adm.categoriesList

import br.com.victorcatao.amaki.data.remote.models.CategoryResponse
import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface CategoriesListContract {

    interface View : BaseView<Presenter> {
        fun displayLoading(loading: Boolean)
        fun displayError(message: String?)
        fun displayItems(items: List<CategoryResponse>)
        fun displayDeleteSuccess()
    }

    interface Presenter : BasePresenter<View> {
        fun loadCategories()
        fun onPressDeleteCategory(id: String)
    }

}