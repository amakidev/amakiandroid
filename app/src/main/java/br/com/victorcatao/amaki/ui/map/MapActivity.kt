package br.com.victorcatao.amaki.ui.map

import android.content.Context
import android.os.Bundle
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.EstablishmentResponse
import br.com.victorcatao.amaki.data.remote.models.EstablishmentsExtra
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.utils.extensions.str
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.intentFor

class MapActivity : BaseActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var establishments = listOf<EstablishmentResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        setToolbar(str(R.string.map_title), true)

        establishments = (intent.getSerializableExtra(Constants.EXTRA_URL) as EstablishmentsExtra).establishments
        setupMap()
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        establishments.forEach {
            val position = LatLng(it.latitude?.toDouble() ?: 0.0, it.longitude?.toDouble() ?: 0.0)
            mMap!!
                    .addMarker(MarkerOptions().position(position).title(it.name ?: "")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin_amaki)))
        }

        val firstPosition = LatLng(establishments.first().latitude?.toDouble() ?: 0.0, establishments.first().longitude?.toDouble() ?: 0.0)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPosition, 13.0f))
    }

}


fun Context.createMapIntent(extra: EstablishmentsExtra) =
        intentFor<MapActivity>(Constants.EXTRA_URL to extra)