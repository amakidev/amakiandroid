package br.com.victorcatao.amaki.ui.loadingactivity

import br.com.victorcatao.amaki.data.remote.models.PostsResponse
import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface LoadingContract {

    interface View : BaseView<Presenter> {
        fun displayItem(item: PostsResponse)
        fun displayError(msg: String?)
        fun displayLoading(loading: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun loadItem()
    }

}