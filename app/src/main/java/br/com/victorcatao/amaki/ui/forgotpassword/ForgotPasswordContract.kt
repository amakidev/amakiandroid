package br.com.victorcatao.amaki.ui.forgotpassword

import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface ForgotPasswordContract {

    interface View : BaseView<Presenter> {
        fun displayMessage(msg: String?)
    }

    interface Presenter : BasePresenter<View>
}