package br.com.victorcatao.amaki.ui.login

import android.os.Bundle
import br.com.victorcatao.amaki.BuildConfig
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.local.model.UserDataSigninModel
import br.com.victorcatao.amaki.data.remote.models.LoginResponse
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.ui.forgotpassword.createForgotPasswordIntent
import br.com.victorcatao.amaki.utils.extensions.notImplementedFeature
import br.com.victorcatao.amaki.utils.extensions.showErrorToast
import br.com.victorcatao.amaki.utils.extensions.showToast
import br.com.victorcatao.amaki.utils.extensions.startActivitySlideTransition
import br.com.victorcatao.amaki.utils.validations.IsEmail
import br.com.victorcatao.amaki.utils.validations.IsLengthValid
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(), LoginContract.View {

    private val presenter: LoginContract.Presenter by lazy {
        LoginPresenter().apply {
            attachView(this@LoginActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListeners()

        if (BuildConfig.DEBUG) {
            loginEmailEt.text = "user@gmail.com"
            loginPassEt.text = "12345678"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setListeners() {
        loginSigninBtn.setOnClickListener {
            validateFields().let { msg ->
                if (!msg.isEmpty()) showToast(msg)
                else {
                    val userdata = UserDataSigninModel(loginEmailEt.text.toString(),
                            loginPassEt.text.toString())
                    presenter.onLoginClicked(userdata)
                }
            }
        }
        loginRegisterBtn.setOnClickListener { presenter.onRegisterClicked() }
        loginForgotPassBtn.setOnClickListener { presenter.onForgotPasswordClicked() }
    }

    private fun validateFields(): String {
        loginEmailEt.text.toString().let {
            if (!IsEmail.isValid(it))
                return getString(R.string.login_error_email)
        }

        loginPassEt.text.toString().let {
            if (!IsLengthValid.isValid(it, 6, false))
                return getString(R.string.login_error_password, 6)
        }
        return ""
    }

    override fun displayLoading(loading: Boolean) {
        loginSigninBtn.setLoading(loading)
    }

    override fun onLoginSucceeded(it: LoginResponse) {
        showToast("Login realizado com sucesso")
    }

    override fun displayError(message: String?) {
        showErrorToast(message ?: getString(R.string.unknown_error))
    }

    override fun openRegister() {
        notImplementedFeature()
    }

    override fun openForgotPassword() {
        startActivitySlideTransition(createForgotPasswordIntent())
    }
}


