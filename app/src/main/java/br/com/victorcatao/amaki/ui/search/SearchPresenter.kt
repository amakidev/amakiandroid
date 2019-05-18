package br.com.victorcatao.amaki.ui.search

import br.com.victorcatao.amaki.data.remote.models.SearchModel

class SearchPresenter : SearchContract.Presenter {

    private var view: SearchContract.View? = null
    private var mList: List<SearchModel>? = null
    private var multipleChoices: Boolean = false
    
    override fun attachView(mvpView: SearchContract.View?) {
        this.view = mvpView
    }

    override fun detachView() {
        this.view = null
    }

    override fun setData(list: List<SearchModel>, multipleChoices: Boolean) {
        this.mList = list
        this.multipleChoices = multipleChoices
        view?.showOptions(list)
    }

    override fun onClickItem(item: SearchModel) {
        if(multipleChoices) {
            val selectedItem = mList?.first { it._id == item._id }
            selectedItem!!.isChecked = !(selectedItem!!.isChecked)
        } else {
            mList?.forEach { it.isChecked = false }
            mList?.first { it._id == item._id }?.isChecked = true
            view?.finishForResult()
        }
        view?.showOptions(mList!!)
    }

}