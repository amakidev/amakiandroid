package br.com.victorcatao.amaki.ui.gallery

import br.com.victorcatao.amaki.data.remote.models.Picture
import br.com.victorcatao.amaki.data.repositories.PicturesRepository
import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import java.io.File

class GalleryPresenter : GalleryContract.Presenter {

    private var view: GalleryContract.View? = null

    override fun attachView(mvpView: GalleryContract.View?) {
        view = mvpView
    }

    override fun detachView() {
        view = null
    }

    override fun loadItems() {
        view?.displayLoading(true)
        PicturesRepository.getPictures()
                .singleSubscribe(onSuccess = {
                    view?.displayLoading(false)
                    view?.displayPictures(it)
                }, onError = {
                    view?.displayLoading(false)
                    view?.displayError(it.message)
                })

    }

    override fun onPictureLoaded(file: File) {
    }

    override fun onItemClicked(item: Picture) {
        view?.displayPicture(item)

    }

    override fun onAddPhotoClicked() {
        view?.displayPictureSelector()
    }

    override fun onRemovePhotoClicked(item: Picture) {
        view?.removePicture(item)
    }


}