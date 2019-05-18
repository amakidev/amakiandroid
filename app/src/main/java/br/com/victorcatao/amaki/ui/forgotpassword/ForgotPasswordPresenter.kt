package br.com.victorcatao.amaki.ui.forgotpassword

class ForgotPasswordPresenter : ForgotPasswordContract.Presenter {

    private var view: ForgotPasswordContract.View? = null


    override fun attachView(mvpView: ForgotPasswordContract.View?) {
        view = mvpView
    }

    override fun detachView() {
        view = null
    }
}