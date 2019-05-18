package br.com.victorcatao.amaki.ui.login

import br.com.victorcatao.amaki.data.local.model.UserDataSigninModel
import br.com.victorcatao.amaki.data.remote.models.LoginResponse
import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        fun displayLoading(loading: Boolean)
        fun openRegister()
        fun openForgotPassword()
        fun onLoginSucceeded(it: LoginResponse)
        fun displayError(message: String?)
    }

    interface Presenter : BasePresenter<View> {
        fun onLoginClicked(userData: UserDataSigninModel)
        fun onRegisterClicked()
        fun onForgotPasswordClicked()
    }
}

