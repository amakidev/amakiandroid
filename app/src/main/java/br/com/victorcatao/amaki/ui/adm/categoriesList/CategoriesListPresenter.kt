package br.com.victorcatao.amaki.ui.adm.categoriesList

import br.com.victorcatao.amaki.data.remote.models.DeleteCategoryRequest
import br.com.victorcatao.amaki.data.repositories.CategoryRepository
import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import io.reactivex.disposables.CompositeDisposable

class CategoriesListPresenter : CategoriesListContract.Presenter {

    private var mView: CategoriesListContract.View? = null
    private var mDisposable = CompositeDisposable()

    override fun attachView(mvpView: CategoriesListContract.View?) {
        mView = mvpView
    }

    override fun detachView() {
        mView = null
        mDisposable.dispose()
    }

    override fun loadCategories() {
        mView?.displayLoading(true)
        mDisposable.add(CategoryRepository.getCategories().singleSubscribe(
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

    override fun onPressDeleteCategory(id: String) {
        mView?.displayLoading(true)
        val request = DeleteCategoryRequest(id)
        mDisposable.add(CategoryRepository.deleteCategory(request).singleSubscribe(
                onSuccess = {
                    mView?.displayLoading(false)
                    loadCategories()
                    mView?.displayDeleteSuccess()
                },
                onError = {
                    mView?.displayLoading(false)
                    mView?.displayError(it.message)
                }
        ))
    }
}