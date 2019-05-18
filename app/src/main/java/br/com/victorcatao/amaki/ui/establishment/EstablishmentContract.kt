package br.com.victorcatao.amaki.ui.establishment

import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface EstablishmentContract {

    interface View : BaseView<Presenter> {
        
    }

    interface Presenter : BasePresenter<View> {
        
    }
}