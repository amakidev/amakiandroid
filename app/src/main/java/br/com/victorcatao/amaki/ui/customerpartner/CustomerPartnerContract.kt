package br.com.victorcatao.amaki.ui.customerpartner

import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface CustomerPartnerContract {

    interface View : BaseView<Presenter> {
        fun displayLoading(loading: Boolean)
        fun displayError(message: String?)
        fun displaySuccess()
    }

    interface Presenter : BasePresenter<View> {
        fun onClickSend(name: String?, phone: String?, establishment: String?, address: String?, socialNetwork: String?, email: String?, about: String?)
    }
}
