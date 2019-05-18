package br.com.victorcatao.amaki.ui.adm.contactsList

import br.com.victorcatao.amaki.data.repositories.ContactRepository
import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import io.reactivex.disposables.CompositeDisposable

class ContactsListPresenter : ContactsListContract.Presenter {

    private var mView: ContactsListContract.View? = null
    private var mDisposable = CompositeDisposable()

    override fun attachView(mvpView: ContactsListContract.View?) {
        mView = mvpView
    }

    override fun detachView() {
        mView = null
        mDisposable.dispose()
    }

    override fun loadContacts() {
        mView?.displayLoading(true)
        mDisposable.add(ContactRepository.getContacts().singleSubscribe(
                onSuccess = {
                    mView?.displayLoading(false)
                    mView?.displayItems(it)
                },
                onError = {
                    mView?.displayLoading(false)
                    mView?.displayError(it.message)
                }
        ))
    }
}