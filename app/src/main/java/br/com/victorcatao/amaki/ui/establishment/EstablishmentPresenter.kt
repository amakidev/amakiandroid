package br.com.victorcatao.amaki.ui.establishment

class EstablishmentPresenter : EstablishmentContract.Presenter {

    private var view: EstablishmentContract.View? = null

    override fun attachView(mvpView: EstablishmentContract.View?) {
        this.view = mvpView
    }

    override fun detachView() {
        this.view = null
    }


}