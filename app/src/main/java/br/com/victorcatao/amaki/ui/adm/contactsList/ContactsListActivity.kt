package br.com.victorcatao.amaki.ui.adm.contactsList

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.ContactResponse
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.ui.customerpartner.createCustomerPartnerIntent
import br.com.victorcatao.amaki.utils.extensions.setup
import br.com.victorcatao.amaki.utils.extensions.showLongToast
import br.com.victorcatao.amaki.utils.extensions.str
import kotlinx.android.synthetic.main.activity_contacts_list.*
import org.jetbrains.anko.intentFor

class ContactsListActivity : BaseActivity(), ContactsListContract.View {

    private val presenter: ContactsListContract.Presenter by lazy {
        val presenter = ContactsListPresenter()
        presenter.attachView(this)
        presenter
    }

    private val adapter by lazy {
        ContactListAdapter(this,
                object : ContactListAdapter.OnItemClickListener {
                    override fun onItemClicked(item: ContactResponse) {
                        openContact(item)
                    }
                })
    }

    private fun openContact(item: ContactResponse) {
        startActivity(createCustomerPartnerIntent(item))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)
        setToolbar(str(R.string.contacts_list_title), true)

        setSwipeRefresh()
        setRecyclerView()
        setupListeners()
        presenter.loadContacts()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupListeners() {

    }

    private fun setRecyclerView() {
        recyclerview.setup(adapter)
    }

    override fun displayItems(items: List<ContactResponse>) {
        adapter.mList = items
    }


    override fun displayError(msg: String?) {
        showLongToast(msg ?: "Erro desconhecido")
    }

    private fun setSwipeRefresh() {
        swiperefresh.setOnRefreshListener { presenter.loadContacts() }
    }

    override fun displayLoading(loading: Boolean) {
        swiperefresh.isRefreshing = loading
        swiperefresh.isEnabled = loading
    }

}

fun Context.createContactsListIntent(extra: String? = null) =
        intentFor<ContactsListActivity>(Constants.EXTRA_URL to extra)
