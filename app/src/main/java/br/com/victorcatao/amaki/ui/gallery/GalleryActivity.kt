package br.com.victorcatao.amaki.ui.gallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.Picture
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.ui.common.createExpandedPictureIntent
import br.com.victorcatao.amaki.utils.extensions.*
import br.com.victorcatao.amaki.utils.helpers.CameraIntentHelper
import kotlinx.android.synthetic.main.activity_gallery.*
import org.jetbrains.anko.*
import java.io.File

class GalleryActivity : BaseActivity(), GalleryContract.View, CameraIntentHelper.OnCameraResultListener {

    private val presenter: GalleryContract.Presenter by lazy {
        val presenter = GalleryPresenter()
        presenter.attachView(this)
        swiperefresh.setOnRefreshListener { presenter.loadItems() }
        presenter
    }

    private val adapter: GalleryAdapter by lazy {
        val adapter = GalleryAdapter(this, object : GalleryAdapter.OnItemClickListener {
            override fun onItemClicked(item: Picture) {
                presenter.onItemClicked(item)
            }

            override fun onRemoveItemClicked(item: Picture) {
                alert(context.str(R.string.pictures_remove_confirmation)) {
                    yesButton { presenter.onRemovePhotoClicked(item) }
                    cancelButton { adapter.onRemoveError(item) }
                }.show()
            }

            override fun onRetryImageClicked(item: Picture) {
                val file = File(item.url)
                presenter.onPictureLoaded(file)
            }
        })
        adapter
    }

    private val cameraIntentHelper: CameraIntentHelper by lazy {
        CameraIntentHelper(this, this, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        setToolbar(str(R.string.gallery_title), true)

        setRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadItems()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun displayPictures(items: List<Picture>) {
        emptyGalleryView.setVisible(items.isEmpty())
        adapter.list = items as MutableList<Picture>
    }

    override fun onMediaReady(file: File?, isImage: Boolean) {
    }

    override fun displayPictureSelector() {
        cameraIntentHelper.startCamera()
    }

    override fun onPictureLoading(item: Picture) {
        adapter.addLoadingImage(item)
        recyclerview.scrollToPosition(adapter.itemCount - 1)
    }

    override fun onPictureAdded(item: Picture) {
        adapter.addPicture(item)
    }

    override fun displayPicture(item: Picture) {
        Log.d(TAG, "método chamado")
        item.url?.let { startActivity(context.createExpandedPictureIntent(it)) }
    }

    override fun onPictureError(msg: String?) {
        adapter.onPictureError()
        msg?.let { longToast(it) }
    }

    override fun onRemoveError(item: Picture, msg: String?) {
        adapter.onRemoveError(item)
        msg?.let { longToast(it) }
    }

    override fun removePicture(item: Picture) {
        adapter.removePicture(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraIntentHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraIntentHelper.onActivityResult(requestCode, resultCode, data)
    }

    override fun onMediaFailed(code: Int, msg: String?) {
        toast(msg ?: str(R.string.placeholder_error_label))
    }

    override fun displayLoading(loading: Boolean) {
        swiperefresh.isRefreshing = loading
    }

    override fun displayError(msg: String?) {
        snack(swipedRecyclerViewCoordinator,
                context.str(R.string.snack_error_msg),
                context.str(R.string.snack_error_try_again_msg), { presenter.loadItems() })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_gallery, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_add_gallery -> {
                notImplementedFeature()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setRecyclerView() {


        val layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        recyclerview.setup(adapter = adapter, layoutManager = layoutManager)
        swiperefresh.setOnRefreshListener { presenter.loadItems() }
    }
}

fun Context.createGalleryIntent() = intentFor<GalleryActivity>()

