package br.com.victorcatao.amaki.ui.adm.contactsList

import br.com.victorcatao.amaki.data.remote.models.CategoryResponse
import br.com.victorcatao.amaki.data.remote.models.ContactResponse
import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface ContactsListContract {

    interface View : BaseView<Presenter> {
        fun displayLoading(loading: Boolean)
        fun displayError(message: String?)
        fun displayItems(items: List<ContactResponse>)
    }

    interface Presenter : BasePresenter<View> {
        fun loadContacts()
//        fun onClickEstablishment(establishment: EstablishmentResponse)
    }

}
