package br.com.victorcatao.amaki.ui.home

import br.com.victorcatao.amaki.data.remote.models.*
import br.com.victorcatao.amaki.data.repositories.CategoryRepository
import br.com.victorcatao.amaki.data.repositories.EstablishmentRepository
import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import io.reactivex.disposables.CompositeDisposable

class HomePresenter : HomeContract.Presenter {

    private var mView: HomeContract.View? = null
    private var mDisposable = CompositeDisposable()

    private var selectedCategoryId: String? = null
    private var selectedCity: CityResponse? = null
    private var cities = listOf<CityResponse>()
    private var establishments = listOf<EstablishmentResponse>()
    private var categories = listOf<CategoryResponse>()
    private var userLatitude: Double? = null
    private var userLongitude: Double? = null

    override fun attachView(mvpView: HomeContract.View?) {
        mView = mvpView
    }

    override fun detachView() {
        mView = null
        mDisposable.dispose()
    }

    override fun loadEstablishments() {

        if(selectedCity != null){
            val request = GetEstablishmentsRequest(selectedCity!!._id, selectedCategoryId, userLatitude, userLongitude)

            mView?.displayLoading(true)
            mDisposable.add(EstablishmentRepository.getEstablishments(request).singleSubscribe(
                    onSuccess = {
                        establishments = it
                        mView?.displayItems(it)
                        mView?.displayLoading(false)
                    },
                    onError = {
                        mView?.displayLoading(false)
                        mView?.displayError(it.message)
                    }
            ))
        }
    }

    override fun loadCities() {
        mView?.displayLoading(true)
        mDisposable.add(EstablishmentRepository.getStatesAndCities().singleSubscribe(
                onSuccess = {
                    mView?.displayLoading(false)
                    this.cities = it
                    this.selectedCity = it.last()
                    this.loadEstablishments()
                    mView?.displayCity(getCityName())
                },
                onError = {
                    mView?.displayLoading(false)
                    mView?.displayError(it.message)
                }
        ))
    }

    override fun loadCategories() {

        mView?.displayLoading(true)
        mDisposable.add(CategoryRepository.getCategories().singleSubscribe(
                onSuccess = {
                    categories = listOf(CategoryResponse("Todos", null)) + it
                    categories.first().selected = true
                    mView?.displayCategories(categories)
                    mView?.displayLoading(false)
                },
                onError = {
                    mView?.displayLoading(false)
                    mView?.displayError(it.message)
                }
        ))

    }

    override fun onClickSelectCity() {
        val l = mutableListOf<SearchModel>()

        cities.forEach {
            val model = SearchModel(it.name, it._id)
            l.add(model)
        }

        mView?.openSearchActivity(l)
    }

    override fun onClickCategory(category: CategoryResponse) {
        this.selectedCategoryId = category._id
        categories.forEach { it.selected = false }
        categories.first { it._id == category._id }.selected = true

        mView?.displayCategories(categories)
        loadEstablishments()
    }

    private fun getCityName(): String {
        return (selectedCity?.state_id?.nameShort ?: "") + " - " + (selectedCity?.name ?: "")
    }

    override fun onUpdateCity(selected: SearchModel) {
        selectedCity = cities.first { it._id == selected._id }
        this.loadEstablishments()
        mView?.displayCity(getCityName())
    }

    override fun onClickEstablishment(establishment: EstablishmentResponse) {
        mView?.openEstablishmentActivity(establishment)
    }

    override fun onClickMap() {
        mView?.openMap(establishments)
    }

    override fun setUserLatLong(latitude: Double, longitude: Double) {
        this.userLatitude = latitude
        this.userLongitude = longitude
        loadEstablishments()
    }
}