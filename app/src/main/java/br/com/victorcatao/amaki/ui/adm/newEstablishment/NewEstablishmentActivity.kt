package br.com.victorcatao.amaki.ui.adm.newEstablishment

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.EstablishmentResponse
import br.com.victorcatao.amaki.data.remote.models.SearchModel
import br.com.victorcatao.amaki.data.remote.models.SearchResult
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.ui.search.createSearchIntent
import br.com.victorcatao.amaki.utils.extensions.showLongToast
import br.com.victorcatao.amaki.utils.extensions.str
import br.com.victorcatao.amaki.utils.helpers.UpdateMediaHelper
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_new_establishment.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.io.File
import java.util.*


class NewEstablishmentActivity : BaseActivity(), NewEstablishmentContract.View {

    private val presenter: NewEstablishmentContract.Presenter by lazy {
        NewEstablishmentPresenter().apply {
            attachView(this@NewEstablishmentActivity)
        }
    }

    private val updateMediaHelper by lazy {
        UpdateMediaHelper(this, object : UpdateMediaHelper.OnRequestReady {
            override fun onMediaReady(file: File?, fromCamera: Boolean, isImage: Boolean) {

                file?.let {
                    pictureEstablishmentIv.setImageBitmap(BitmapFactory.decodeFile(it.absolutePath))
                    presenter.onReceivedImageFile(it)
                }
            }

            override fun onMediaFailed(code: Int, msg: String) {
                toast(msg)
            }
        }, true)
    }

    private var establishment: EstablishmentResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_establishment)
        setToolbar("Estabelecimento", true)

        establishment = (intent.getSerializableExtra(Constants.EXTRA_URL) as? EstablishmentResponse)

        setupListeners()
        setupGooglePlaces()
        setupView()
        presenter.loadCategories()
    }

    var autocompleteFragment: AutocompleteSupportFragment? = null

    private fun setupGooglePlaces() {
        if (!Places.isInitialized()) {
            Places.initialize(context, getString(R.string.google_maps_api_key))
        }

        autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment?.setHint("Pesquisar Endereço")
        autocompleteFragment?.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.ADDRESS))
        autocompleteFragment?.setCountry("BR")

        autocompleteFragment?.setOnPlaceSelectedListener(object: PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                presenter.onSelectPlace(p0)
                autocompleteFragment?.setHint(p0.address)
            }

            override fun onError(p0: Status) {
                showLongToast("Erro: ${p0.statusCode}")
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupListeners() {
        sendbutton.onClick { onClickSend() }

        categoriesET.setOnClickListener {
            presenter.onClickCategories()
        }

        uploadImageCVBtn.setOnClickListener {
            presenter.onClickUploadPicture()
        }

    }

    private fun setupView() {
        swiperefresh.isEnabled = false

        establishment?.let {

            presenter.setEstablishment(establishment = it)

            nameET.setText(it.name)
            cellphoneET.setText(it.cellphone)
            phoneET.setText(it.phone)
            facebookET.setText(it.facebook)
            instagramET.setText(it.instagram)
            siteET.setText(it.site)
            descriptionET.setText(it.desc)
            whatsappRadio.isChecked = it.isWhatsApp == true
            amakiDiscountRadio.isChecked = it.hasDiscount == true
            premiumRadio.isChecked = it.isPremium == 1 // 1=true
            categoriesET.text = it.categories?.joinToString(", ") { it.name ?: "" }
            Glide.with(this).load(it.picture).into(pictureEstablishmentIv)
            this.autocompleteFragment?.setHint(it.address)

        }
    }

    private fun onClickSend() {
        presenter.createEstablishment(
                name = nameET.text.toString(),
                cellphone = cellphoneET.text.toString(),
                phone = phoneET.text.toString(),
                facebook = facebookET.text.toString(),
                instagram = instagramET.text.toString(),
                site = siteET.text.toString(),
                desc = descriptionET.text.toString(),
                isWhatsApp = whatsappRadio.isChecked,
                hasDiscount = amakiDiscountRadio.isChecked,
                isPremium = premiumRadio.isChecked
                )

    }

    override fun openSearchActivity(options: List<SearchModel>){
        startActivityForResult(createSearchIntent(extra = options, multipleChoices =  true), 100)
    }

    override fun setCategoriesText(text: String) {
        categoriesET.text = text
    }

    override fun displayError(msg: String) {
        showLongToast(msg)
    }

    override fun displayLoading(loading: Boolean) {
        swiperefresh.isRefreshing = loading
    }

    override fun displaySuccess(message: String) {
        showLongToast(message)
//        finish()
    }

    override fun startUpdateMediaHelper() {
        updateMediaHelper.initiate()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        updateMediaHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 || resultCode == 100) {
            val b = data?.getSerializableExtra(Constants.EXTRA_SEARCH) as SearchResult
            presenter.setListOfCategories(b.result)
        } else {
            data?.let {
                updateMediaHelper.onActivityResult(requestCode, resultCode, it)
            }
        }
    }

}


fun Context.createNewEstablishmentIntent(extra: EstablishmentResponse? = null) = intentFor<NewEstablishmentActivity>(Constants.EXTRA_URL to extra)