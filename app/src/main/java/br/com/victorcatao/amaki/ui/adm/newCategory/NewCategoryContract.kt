package br.com.victorcatao.amaki.ui.adm.newCategory

import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface NewCategoryContract {

    interface View : BaseView<Presenter> {
        fun displayError(msg: String)
        fun displayLoading(loading: Boolean)
        fun showSuccessMessage()
    }

    interface Presenter : BasePresenter<View> {
        fun onClickSave(category: String)
    }
    
}