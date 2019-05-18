package br.com.victorcatao.amaki.ui.customerpartner

import br.com.victorcatao.amaki.data.remote.models.CreateContactRequest
import br.com.victorcatao.amaki.data.repositories.ContactRepository
import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import io.reactivex.disposables.CompositeDisposable

class CustomerPartnerPresenter : CustomerPartnerContract.Presenter {

    private var mView: CustomerPartnerContract.View? = null
    private var mDisposable = CompositeDisposable()

    override fun attachView(mvpView: CustomerPartnerContract.View?) {
        mView = mvpView
    }

    override fun detachView() {
        mView = null
        mDisposable.dispose()
    }

    override fun onClickSend(name: String?, phone: String?, establishment: String?, address: String?, socialNetwork: String?, email: String?, about: String?) {

        val req = CreateContactRequest(name, phone, establishment, socialNetwork, address, email, about)

        mView?.displayLoading(true)
        ContactRepository.createContact(req).singleSubscribe(
                onSuccess = {
                    mView?.displayLoading(false)
                    mView?.displaySuccess()
                },
                onError = {
                    mView?.displayLoading(false)
                    mView?.displayError(it.message)
                }
        )
    }
}