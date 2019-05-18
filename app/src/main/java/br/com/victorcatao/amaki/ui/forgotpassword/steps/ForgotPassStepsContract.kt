package br.com.victorcatao.amaki.ui.forgotpassword.steps

import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface ForgotPassStepsContract {

    interface View : BaseView<Presenter> {
        fun displayMessage(msg: String?)
    }

    interface Presenter : BasePresenter<View> {
        fun onSendEmailClicked()
    }
}