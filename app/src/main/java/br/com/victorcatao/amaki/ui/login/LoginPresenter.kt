package br.com.victorcatao.amaki.ui.login

import br.com.victorcatao.amaki.data.local.model.UserDataSigninModel
import br.com.victorcatao.amaki.data.repositories.LoginRepository
import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import io.reactivex.disposables.CompositeDisposable

class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private var repository = LoginRepository
    private var disposable = CompositeDisposable()

    override fun onLoginClicked(userData: UserDataSigninModel) {
        view?.displayLoading(true)
        tryToLogin(userData)
    }

    private fun tryToLogin(userData: UserDataSigninModel) {
        repository.singIn(userData)
                .singleSubscribe(
                        onSuccess = {
                            view?.displayLoading(false)
                            view?.onLoginSucceeded(it)
                        },
                        onError = {
                            view?.displayLoading(false)
                            view?.displayError(it.message)
                        }
                )
    }

    override fun onRegisterClicked() {
        view?.openRegister()
    }

    override fun onForgotPasswordClicked() {
        view?.openForgotPassword()
    }

    override fun attachView(mvpView: LoginContract.View?) {
        this.view = mvpView
    }

    override fun detachView() {
        this.view = null
        disposable.dispose()
    }
}
