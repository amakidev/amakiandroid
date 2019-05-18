package br.com.victorcatao.amaki.ui.home

import br.com.victorcatao.amaki.data.remote.models.CategoryResponse
import br.com.victorcatao.amaki.data.remote.models.CityResponse
import br.com.victorcatao.amaki.data.remote.models.EstablishmentResponse
import br.com.victorcatao.amaki.data.remote.models.SearchModel
import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun displayItems(item: List<EstablishmentResponse>)
//        fun displayError(msg: String?)
        fun displayLoading(loading: Boolean)

        fun displayCategories(items: List<CategoryResponse>)
        fun displayError(message: String?)
        fun displayCity(city: String)
        fun openSearchActivity(options: List<SearchModel>)
        fun openEstablishmentActivity(establishment: EstablishmentResponse)
        fun openMap(establishments: List<EstablishmentResponse>)
    }

    interface Presenter : BasePresenter<View> {
        fun loadCities()
        fun loadEstablishments()
        fun loadCategories()
        fun onClickCategory(category: CategoryResponse)
        fun onClickSelectCity()
        fun onUpdateCity(selected: SearchModel)
        fun onClickEstablishment(establishment: EstablishmentResponse)
        fun onClickMap()
        fun setUserLatLong(latitude: Double, longitude: Double)
    }

}