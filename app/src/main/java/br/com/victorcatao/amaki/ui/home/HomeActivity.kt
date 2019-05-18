package br.com.victorcatao.amaki.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.intentFor
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import br.com.victorcatao.amaki.data.remote.models.*
import br.com.victorcatao.amaki.ui.about.createAboutIntent
import br.com.victorcatao.amaki.ui.customerpartner.createCustomerPartnerIntent
import br.com.victorcatao.amaki.ui.establishment.createEstablishmentIntent
import br.com.victorcatao.amaki.ui.map.createMapIntent
import br.com.victorcatao.amaki.ui.search.createSearchIntent
import br.com.victorcatao.amaki.utils.extensions.*
import br.com.victorcatao.amaki.utils.helpers.LocationHelper
import kotlinx.android.synthetic.main.partial_toolbar_home.*

class HomeActivity : BaseActivity(), HomeContract.View {

    private val presenter: HomeContract.Presenter by lazy {
        val presenter = HomePresenter()
        presenter.attachView(this)
        presenter
    }

    private val adapter by lazy {
        EstablishmentListAdapter(this,
                object : EstablishmentListAdapter.OnItemClickListener {
                    override fun onItemClicked(item: EstablishmentResponse) {
                        presenter.onClickEstablishment(item)
                    }
                },
                object: EstablishmentListAdapter.OnItemLongClickListener {
                    override fun onItemLongClicked(item: EstablishmentResponse) {

                    }
                })
    }

    private val categoryAdapter by lazy {
        CategoryListAdapter(this,
                object : CategoryListAdapter.OnItemClickListener {
                    override fun onItemClicked(item: CategoryResponse) {
                        presenter.onClickCategory(item)
                    }
                })
    }

    private val locationHelper by lazy {
        LocationHelper(this, object : LocationHelper.OnLocationListener {
            override fun onLocationChanged(location: Location) {
                presenter.setUserLatLong(location.latitude, location.longitude)
//                presenter.onLocationChanged(location)
            }

            @SuppressLint("MissingPermission")
            override fun onAllPermissionsGranted() {
//                map?.isMyLocationEnabled = true
            }

            override fun onPermissionsDenied() {
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setToolbar(str(R.string.home_title), false)

        setSwipeRefresh()
        setRecyclerView()
        setupListeners()
        locationHelper.start()
        presenter.loadCities()
        presenter.loadCategories()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationHelper.stop()
        presenter.detachView()
    }

    private fun setupListeners() {
        main_city_text_view.setOnClickListener {
            presenter.onClickSelectCity()
        }

        mapButton.setOnClickListener {
            presenter.onClickMap()
        }
    }

    private fun setRecyclerView() {
        recyclerview.setup(adapter)
        categoryRecyclerview.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
        categoryRecyclerview.setup(categoryAdapter, layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
    }

    override fun displayItems(item: List<EstablishmentResponse>) {
        adapter.mList = item
    }

    override fun displayCategories(items: List<CategoryResponse>) {
        categoryAdapter.mList = items
    }

    override fun displayCity(city: String) {
        main_city_text_view.text = city
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_gallery, menu)
        return true
    }

    override fun openSearchActivity(options: List<SearchModel>) {
        startActivityForResult(createSearchIntent(options, false), 100)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId?.let {
            if (it == R.id.action_partner) {
                startActivity(createCustomerPartnerIntent())
            } else if (it == R.id.action_about) {
                startActivity(createAboutIntent())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun openEstablishmentActivity(establishment: EstablishmentResponse) {
        startActivity(createEstablishmentIntent(establishment))
    }


    override fun displayError(msg: String?) {
        Log.e(TAG, msg)
        showLongToast(msg ?: "Erro desconhecido")
    }

    private fun setSwipeRefresh() {
        swiperefresh.setOnRefreshListener { presenter.loadEstablishments() }
    }

    override fun displayLoading(loading: Boolean) {
        swiperefresh.isRefreshing = loading
        swiperefresh.isEnabled = loading
    }

    override fun openMap(establishments: List<EstablishmentResponse>) {
        startActivity(createMapIntent(EstablishmentsExtra(establishments)))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 || resultCode == 100) {
            val b = data?.getSerializableExtra(Constants.EXTRA_SEARCH) as? SearchResult
            if (b != null) {
                presenter.onUpdateCity(b!!.result.first { it.isChecked })
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}


fun Context.createHomeIntent(extra: String? = null) =
        intentFor<HomeActivity>(Constants.EXTRA_URL to extra)