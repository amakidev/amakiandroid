package br.com.victorcatao.amaki.ui.establishment

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.View
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.EstablishmentResponse
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.utils.extensions.str
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_establishment.*
import org.jetbrains.anko.intentFor
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.content.pm.PackageManager

class EstablishmentActivity : BaseActivity(), EstablishmentContract.View, OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    var permissionArrays = arrayOf<String>(Manifest.permission.CALL_PHONE)
    var REQUEST_CODE = 101

    private val presenter: EstablishmentContract.Presenter by lazy {
        EstablishmentPresenter().apply {
            attachView(this@EstablishmentActivity)
        }
    }

    private var establishment: EstablishmentResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_establishment)

        establishment = intent.getSerializableExtra(Constants.EXTRA_URL) as EstablishmentResponse
        setToolbar(establishment?.name ?: str(R.string.establishment_title), true)

        setupView()
        setupMap()
        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupView() {
        itemNameTv.text = establishment?.name
        itemAddressTv.text = "${establishment?.address}"
        itemDescriptionTv.text = establishment?.desc
        itemNameTv.text = establishment?.name
        itemNameTv.text = establishment?.name

        itemDistanceTv.visibility = if (establishment?.distance == null) View.GONE else View.VISIBLE
        itemDistanceTv.text = String.format("%.2f", establishment?.distance ?: 0)

        facebokLL.visibility = if (establishment?.facebook.isNullOrEmpty()) View.GONE else View.VISIBLE
        itemFacebookTv.text = establishment?.facebook

        instagramLL.visibility = if (establishment?.instagram.isNullOrEmpty()) View.GONE else View.VISIBLE
        itemInstagramTv.text = establishment?.instagram

        siteLL.visibility = if (establishment?.site.isNullOrEmpty()) View.GONE else View.VISIBLE
        itemSiteTv.text = establishment?.site

        zapLL.visibility = if (establishment?.isWhatsApp == true) View.VISIBLE else View.GONE
        itemZapTv.text = establishment?.cellphone

        phoneLL.visibility = if (establishment?.phone.isNullOrEmpty()) View.GONE else View.VISIBLE
        itemPhoneTv.text = establishment?.phone

        itemDiscountLabelIV.visibility = if (establishment?.hasDiscount == true) View.VISIBLE else View.GONE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Dhaka, Bangladesh, and move the camera.
        val latlng = LatLng(establishment?.latitude?.toDouble()
                ?: 0.0, establishment?.longitude?.toDouble() ?: 0.0)
        mMap?.let {
            it.addMarker(MarkerOptions().position(latlng).title(establishment?.name
                    ?: "").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin_amaki)))
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17.0f))
        }
    }

    private fun setListeners() {
        facebokLL.setOnClickListener {
            this.onClickFacebook()
        }

        instagramLL.setOnClickListener {
            this.onClickInstagram()
        }

        siteLL.setOnClickListener {
            this.onClickSite()
        }

        zapLL.setOnClickListener {
            this.onClickWhatsApp()
        }

        phoneLL.setOnClickListener {
            this.onClickPhone()
        }
    }

    private fun onClickPhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, REQUEST_CODE);
        } else {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:${establishment!!.phone!!}")
            startActivity(callIntent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            for (i in 0 until grantResults.size) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:${establishment!!.phone!!}")
                    startActivity(callIntent)
                }
            }
        }
    }

    private fun onClickWhatsApp() {
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=${establishment!!.cellphone}.&text=Ola")
        val sendIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(sendIntent)
    }

    private fun onClickSite() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(establishment!!.site!!))
        startActivity(browserIntent)
    }

    private fun onClickInstagram() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/${establishment!!.instagram!!}"))
        startActivity(browserIntent)
    }

    private fun onClickFacebook() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/${establishment!!.facebook!!}"))
        startActivity(browserIntent)
    }


}

fun Context.createEstablishmentIntent(extra: EstablishmentResponse? = null) =
        intentFor<EstablishmentActivity>(Constants.EXTRA_URL to extra)

