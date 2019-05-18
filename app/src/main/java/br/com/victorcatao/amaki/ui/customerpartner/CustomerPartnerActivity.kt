package br.com.victorcatao.amaki.ui.customerpartner

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.ContactResponse
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.utils.extensions.showLongToast
import br.com.victorcatao.amaki.utils.extensions.str
import kotlinx.android.synthetic.main.activity_customer_partner2.*
import org.jetbrains.anko.intentFor

class CustomerPartnerActivity : BaseActivity(), CustomerPartnerContract.View {

    private var contact: ContactResponse? = null

    private val presenter: CustomerPartnerContract.Presenter by lazy {
        val presenter = CustomerPartnerPresenter()
        presenter.attachView(this)
        presenter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_partner2)

        contact = (intent.getSerializableExtra(Constants.EXTRA_URL) as? ContactResponse)

        setToolbar(contact?.name ?: str(R.string.customer_partner_title), true)

        setupListeners()
        setupSwipeRefresh()
        setupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupView() {
        if(contact != null) {
            setTextAndDisable(nameET, contact!!.name)
            setTextAndDisable(phoneET, contact!!.phone)
            setTextAndDisable(establishmentET, contact!!.establishment_name)
            setTextAndDisable(addressET, contact!!.address)
            setTextAndDisable(socialNetworkET, contact!!.socialNetwork)
            setTextAndDisable(emailET, contact!!.email)
            setTextAndDisable(aboutET, contact!!.desc)
            sendbutton.visibility = View.GONE
        }
    }

    private fun setTextAndDisable(field: EditText, text: String?) {
        field.setText(text)
        field.isEnabled = false
    }

    private fun setupSwipeRefresh() {
        swiperefresh.isEnabled = false

    }

    private fun setupListeners() {
        sendbutton.setOnClickListener {
            onClickSend()
        }
    }

    override fun displayError(msg: String?) {
        Log.e(TAG, msg)
        showLongToast(msg ?: "Erro desconhecido")
    }

    override fun displayLoading(loading: Boolean) {
        swiperefresh.isRefreshing = loading

    }

    private fun onClickSend() {
        if (nameET.text.isNullOrEmpty()) {
            showLongToast("Preencha o nome")
            return
        }
        if (phoneET.text.isNullOrEmpty()) {
            showLongToast("Preencha o telefone")
            return
        }
        if (establishmentET.text.isNullOrEmpty()) {
            showLongToast("Preencha o estabelecimento")
            return
        }
        if (addressET.text.isNullOrEmpty()) {
            showLongToast("Preencha o endereço")
            return
        }
        if (emailET.text.isNullOrEmpty()) {
            showLongToast("Preencha o e-mail")
            return
        }
        if (aboutET.text.isNullOrEmpty()) {
            showLongToast("Diga-nos o que você oferece")
            return
        }

        presenter.onClickSend(nameET.text.toString(), phoneET.text.toString(), establishmentET.text.toString(), addressET.text.toString(), socialNetworkET.text.toString(), emailET.text.toString(), aboutET.text.toString())

    }

    override fun displaySuccess() {
        showLongToast("Obrigado! Entraremos em contato em breve.")
        finish()
    }
}

fun Context.createCustomerPartnerIntent(extra: ContactResponse? = null) =
        intentFor<CustomerPartnerActivity>(Constants.EXTRA_URL to extra)