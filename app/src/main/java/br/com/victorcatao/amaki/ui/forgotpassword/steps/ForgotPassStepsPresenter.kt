package br.com.victorcatao.amaki.ui.forgotpassword.steps


class ForgotPassStepsPresenter : ForgotPassStepsContract.Presenter {

    private var view: ForgotPassStepsContract.View? = null

    override fun onSendEmailClicked() {
        //TODO: LOGIC TO RESET PASSWORD AND SEND A MESSAGE WITH THE CALL RESPONSE

        view?.displayMessage(null)
    }


    override fun attachView(mvpView: ForgotPassStepsContract.View?) {
        this.view = mvpView
    }

    override fun detachView() {
        this.view = null
    }
}