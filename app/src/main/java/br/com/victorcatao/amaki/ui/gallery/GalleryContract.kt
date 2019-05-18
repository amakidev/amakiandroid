package br.com.victorcatao.amaki.ui.gallery

import br.com.victorcatao.amaki.data.remote.models.Picture
import br.com.victorcatao.amaki.ui.base.BasePresenter
import br.com.victorcatao.amaki.ui.base.BaseView
import java.io.File

class GalleryContract {

    interface View : BaseView<Presenter> {
        fun displayPictures(items: List<Picture>)
        fun displayPictureSelector()
        fun onPictureLoading(item: Picture)
        fun onPictureAdded(item: Picture)
        fun displayPicture(item: Picture)
        fun displayError(msg: String?)
        fun onPictureError(msg: String?)
        fun onRemoveError(item: Picture, msg: String?)
        fun removePicture(item: Picture)
        fun displayLoading(loading: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun loadItems()
        fun onPictureLoaded(file: File)
        fun onItemClicked(item: Picture)
        fun onAddPhotoClicked()
        fun onRemovePhotoClicked(item: Picture)
    }

}