package br.com.victorcatao.amaki.ui.adm.newEstablishment

import br.com.victorcatao.amaki.data.remote.models.*
import br.com.victorcatao.amaki.data.repositories.CategoryRepository
import br.com.victorcatao.amaki.data.repositories.EstablishmentRepository
import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import com.google.android.libraries.places.api.model.Place
import io.reactivex.disposables.CompositeDisposable
import java.io.File

class NewEstablishmentPresenter : NewEstablishmentContract.Presenter {

    private var view: NewEstablishmentContract.View? = null
    private var place: Place? = null
    private var mDisposable = CompositeDisposable()
    private var selectedCategories = listOf<SearchModel>()
    private var establishment: EstablishmentResponse? = null
    private var establishmentPhoto: File? = null

    var categories = listOf<CategoryResponse>()

    override fun attachView(mvpView: NewEstablishmentContract.View?) {
        this.view = mvpView
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadCategories() {
        view?.displayLoading(true)
        mDisposable.add(CategoryRepository.getCategories().singleSubscribe(
                onSuccess = {
                    this.categories = it
                    view?.displayLoading(false)
                },
                onError = {
                    view?.displayLoading(false)
                    view?.displayError(it.message ?: "Erro ao retornar categorias")
                }
        ))
    }

    override fun createEstablishment(name: String, cellphone: String, phone: String, facebook: String, instagram: String, site: String, desc: String, isWhatsApp: Boolean, hasDiscount: Boolean, isPremium: Boolean) {
        if (name.isNullOrEmpty()) {
            view?.displayError("Preencha o NOME corretamente")
            return
        }

        if (desc.isNullOrEmpty()) {
            view?.displayError("Preencha a DESCRIÇÃO corretamente")
            return
        }

        if (place == null && establishment == null) {
            view?.displayError("Preencha o ENDERECO corretamente")
            return
        }

        if (selectedCategories.count() <= 0) {
            view?.displayError("Preencha as CATEGORIAS corretamente")
            return
        }

        val req = CreateEstablishmentsRequest(
                id_establishment = establishment?._id,
                desc = desc,
                latitude = place?.latLng?.latitude?.toFloat(),
                longitude = place?.latLng?.longitude?.toFloat(),
                facebook = facebook,
                instagram = instagram,
                site =  site,
                name = name,
                phone = phone,
                cellphone = cellphone,
                address = place?.address.toString(),
                categories = selectedCategories.map { it._id ?: "" },
                isWhatsApp = isWhatsApp,
                isPremium = isPremium,
                hasDiscount = hasDiscount
        )

        if(establishment != null && place == null) {
            req.latitude = establishment?.latitude
            req.longitude = establishment?.longitude
            req.address = establishment?.address
        }

        view?.displayLoading(true)

        mDisposable.add(EstablishmentRepository.createEstablishment(req).singleSubscribe(
                onSuccess = {
                    view?.displayLoading(false)
                    establishment = EstablishmentResponse(null, it._id, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)
                    if(establishmentPhoto != null) {
                        updatePhoto()
                    } else {
                        view?.displaySuccess("Sucesso!")
                    }
                },
                onError = {
                    view?.displayLoading(false)
                    view?.displayError(it.message ?: "Erro ao retornar categorias")
                }
        ))

    }

    override fun setEstablishment(establishment: EstablishmentResponse) {
        this.establishment = establishment
        this.selectedCategories = establishment.categories!!.map { it.toSearchModel() }
    }

    override fun onClickCategories() {
        val l = mutableListOf<SearchModel>()

        categories.forEach {
            val model = SearchModel(it.name, it._id)
            l.add(model)
        }
        view?.openSearchActivity(l)
    }

    override fun onSelectPlace(place: Place) {
        this.place = place
    }

    override fun setListOfCategories(categories: List<SearchModel>) {
        selectedCategories = categories.filter { it.isChecked }
        view?.setCategoriesText(selectedCategories.map { it.name }.filterNotNull().joinToString(", "))
    }

    override fun onReceivedImageFile(file: File?) {
        file?.let { image ->
            establishmentPhoto = file
            if(establishment != null) {
                updatePhoto()
            }
        }
    }

    private fun updatePhoto() {
        view?.displayLoading(true)
        mDisposable.add(EstablishmentRepository.updateEstablishmentPicture(establishmentPhoto!!, establishment?._id ?: "").singleSubscribe(
                onSuccess = {
                    view?.displayLoading(false)
                    view?.displaySuccess("Estabelecimento alterado com sucesso")
                },
                onError = {
                    view?.displayLoading(false)
                    view?.displayError(it.message ?: "Erro ao enviar a foto")
                }
        ))
    }

    override fun onClickUploadPicture() {
        view?.startUpdateMediaHelper()
    }
}