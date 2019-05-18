package br.com.victorcatao.amaki.ui.loadingactivity

import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import br.com.victorcatao.amaki.data.repositories.JsonPlaceholderRepository
import io.reactivex.disposables.CompositeDisposable

class LoadingPresenter : LoadingContract.Presenter {

    private var mView: LoadingContract.View? = null
    private var mDisposable = CompositeDisposable()

    override fun attachView(mvpView: LoadingContract.View?) {
        mView = mvpView
    }

    override fun detachView() {
        mView = null
        mDisposable.dispose()
    }

    override fun loadItem() {
        mView?.displayLoading(true)
        mDisposable.add(JsonPlaceholderRepository.getPolls().singleSubscribe(
                onSuccess = {
                    mView?.displayLoading(false)
                    mView?.displayItem(it[0])
                },
                onError = {
                    mView?.displayLoading(false)
                    mView?.displayError(it.message)
                }
        ))
    }
}

