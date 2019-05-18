package br.com.victorcatao.amaki.ui.adm.newCategory

import br.com.victorcatao.amaki.data.remote.models.CreateCategoryRequest
import br.com.victorcatao.amaki.data.repositories.CategoryRepository
import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import io.reactivex.disposables.CompositeDisposable

class NewCategoryPresenter : NewCategoryContract.Presenter {

    private var view: NewCategoryContract.View? = null
    private val mDisposable = CompositeDisposable()

    override fun attachView(mvpView: NewCategoryContract.View?) {
        this.view = mvpView
    }

    override fun detachView() {
        this.view = null
    }

    override fun onClickSave(category: String) {
        if(category.isNullOrEmpty()) {
            view?.displayError("Preencha a categoria")
            return
        }
        view?.displayLoading(true)

        mDisposable.add(CategoryRepository.createCategory(CreateCategoryRequest(category)).singleSubscribe(
                onSuccess = {
                    view?.displayLoading(false)
                    view?.showSuccessMessage()
                },
                onError = {
                    view?.displayLoading(false)
                    view?.displayError(it.message ?: "Erro ao criar a categoria")
                }
        ))
    }
}

