package br.com.victorcatao.amaki.ui.adm.allEstablishments

import br.com.victorcatao.amaki.data.remote.models.*
import br.com.victorcatao.amaki.data.repositories.EstablishmentRepository
import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import io.reactivex.disposables.CompositeDisposable

class AllEstablishmentsPresenter : AllEstablishmentsContract.Presenter {

    private var mView: AllEstablishmentsContract.View? = null
    private var mDisposable = CompositeDisposable()

    private var selectedCategoryId: String? = null
    private var selectedCity: CityResponse? = null
    private var cities = listOf<CityResponse>()
    private var establishments = listOf<EstablishmentResponse>()
    private var categories = listOf<CategoryResponse>()

    override fun attachView(mvpView: AllEstablishmentsContract.View?) {
        mView = mvpView
    }

    override fun detachView() {
        mView = null
        mDisposable.dispose()
    }

    override fun loadEstablishments() {

        val request = GetEstablishmentsRequest(null, null, null, null)

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

    override fun onClickEstablishment(establishment: EstablishmentResponse) {
        mView?.openEstablishmentActivity(establishment)
    }

    override fun onSearch(text: String) {
        mView?.displayItems(establishments.filter { it.name?.contains(text, true) == true })
    }

    override fun onPressDeleteEstalbishment(id: String) {

        mView?.displayLoading(true)
        mDisposable.add(EstablishmentRepository.deleteEstablishment(DeleteEstablishmentRequest(id)).singleSubscribe(
                onSuccess = {
                    loadEstablishments()
                    mView?.displayLoading(false)
                },
                onError = {
                    mView?.displayLoading(false)
                    mView?.displayError(it.message)
                }
        ))

    }

}