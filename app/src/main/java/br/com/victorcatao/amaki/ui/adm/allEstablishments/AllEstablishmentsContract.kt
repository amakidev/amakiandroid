package br.com.victorcatao.amaki.ui.adm.allEstablishments

import br.com.victorcatao.amaki.data.remote.models.CategoryResponse
import br.com.victorcatao.amaki.data.remote.models.EstablishmentResponse
import br.com.victorcatao.amaki.data.remote.models.SearchModel
import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface AllEstablishmentsContract {

    interface View : BaseView<Presenter> {
        fun displayItems(item: List<EstablishmentResponse>)
        fun displayLoading(loading: Boolean)
        fun displayError(message: String?)
        fun openEstablishmentActivity(establishment: EstablishmentResponse)
    }

    interface Presenter : BasePresenter<View> {
        fun loadEstablishments()
        fun onClickEstablishment(establishment: EstablishmentResponse)
        fun onSearch(text: String)
        fun onPressDeleteEstalbishment(id: String)
    }

}
