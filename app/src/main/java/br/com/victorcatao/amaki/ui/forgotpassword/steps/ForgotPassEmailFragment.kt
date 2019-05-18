package br.com.victorcatao.amaki.ui.forgotpassword.steps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.ui.forgotpassword.ForgotPasswordContract
import br.com.victorcatao.amaki.ui.forgotpassword.steps.ForgotPassStepsContract.Presenter
import br.com.victorcatao.amaki.utils.validations.IsEmail
import kotlinx.android.synthetic.main.fragment_forgot_pass_email.*
import kotlinx.android.synthetic.main.fragment_forgot_pass_email.view.*

class ForgotPassEmailFragment : Fragment(), ForgotPassStepsContract.View {

    private lateinit var mView: View

    private val presenter: Presenter by lazy {
        ForgotPassStepsPresenter().apply {
            attachView(this@ForgotPassEmailFragment)
        }
    }

    private val mActivity: ForgotPasswordContract.View by lazy {
        activity as ForgotPasswordContract.View
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_forgot_pass_email, container, false)
        setUpListeners()
        return mView
    }

    private fun setUpListeners() {
        mView.forgotPassEmailBtn.setOnClickListener {
            validateFields().let { msg ->
                if (!msg.isEmpty()) mActivity.displayMessage(msg)
                else presenter.onSendEmailClicked()
            }
        }
    }

    private fun validateFields(): String {
        forgotPassEmailEt.text.toString().let {
            if (!IsEmail.isValid(it))
                return getString(R.string.login_error_email)
        }

        return ""
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun displayMessage(msg: String?) {
        mActivity.displayMessage(msg)
    }
}