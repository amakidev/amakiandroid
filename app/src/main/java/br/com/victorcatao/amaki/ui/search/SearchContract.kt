package br.com.victorcatao.amaki.ui.search

import br.com.victorcatao.amaki.data.remote.models.SearchModel
import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface SearchContract {

    interface View : BaseView<Presenter> {
        fun showOptions(options: List<SearchModel>)
        fun finishForResult()
    }

    interface Presenter : BasePresenter<View> {
        fun setData(list: List<SearchModel>, multipleChoices: Boolean)
        fun onClickItem(item: SearchModel)
    }
}