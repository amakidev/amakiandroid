package br.com.victorcatao.amaki.ui.adm.newEstablishment

import br.com.victorcatao.amaki.data.remote.models.EstablishmentResponse
import br.com.victorcatao.amaki.data.remote.models.SearchModel
import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView
import com.google.android.libraries.places.api.model.Place
import java.io.File

interface NewEstablishmentContract {

    interface View : BaseView<Presenter> {
        fun displayError(msg: String)
        fun displayLoading(loading: Boolean)
        fun openSearchActivity(options: List<SearchModel>)
        fun setCategoriesText(text: String)
        fun displaySuccess(message: String)
        fun startUpdateMediaHelper()
    }

    interface Presenter : BasePresenter<View> {
        fun createEstablishment(name: String, cellphone: String, phone: String, facebook: String, instagram: String, site: String, desc: String, isWhatsApp: Boolean, hasDiscount: Boolean, isPremium: Boolean)
        fun onSelectPlace(place: Place)
        fun loadCategories()
        fun onClickCategories()
        fun setListOfCategories(categories: List<SearchModel>)
        fun setEstablishment(establishment: EstablishmentResponse)
        fun onReceivedImageFile(file: File?)
        fun onClickUploadPicture()
    }

}