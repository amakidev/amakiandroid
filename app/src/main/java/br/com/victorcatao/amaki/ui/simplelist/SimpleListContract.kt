package br.com.victorcatao.amaki.ui.simplelist

import br.com.victorcatao.amaki.data.remote.models.PostsResponse
import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface SimpleListContract {

    interface View : BaseView<Presenter> {
        fun displayItems(items: List<PostsResponse>)
        fun displayError(msg: String?)
        fun displayLoading(loading: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun loadItems()
    }

}