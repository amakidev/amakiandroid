package br.com.victorcatao.amaki.ui.forgotpassword

import android.content.Context
import android.os.Bundle
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.ui.forgotpassword.steps.ForgotPassEmailFragment
import br.com.victorcatao.amaki.utils.extensions.notImplementedFeature
import org.jetbrains.anko.intentFor

class ForgotPasswordActivity : BaseActivity(), ForgotPasswordContract.View {

    private val presenter: ForgotPasswordContract.Presenter by lazy {
        ForgotPasswordPresenter().apply {
            attachView(this@ForgotPasswordActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        openEmailScreen()
    }

    private fun openEmailScreen() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.forgotPassContainer, ForgotPassEmailFragment())
            commit()
        }
    }

    override fun displayMessage(msg: String?) {
        notImplementedFeature()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}

fun Context.createForgotPasswordIntent() = intentFor<ForgotPasswordActivity>()

