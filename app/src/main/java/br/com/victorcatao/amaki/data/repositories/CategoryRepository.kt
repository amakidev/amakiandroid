package br.com.victorcatao.amaki.data.repositories

import br.com.victorcatao.amaki.data.remote.datasources.CategoryRemoteDataSource
import br.com.victorcatao.amaki.data.remote.models.CategoryResponse
import br.com.victorcatao.amaki.data.remote.models.CreateCategoryRequest
import br.com.victorcatao.amaki.data.remote.models.CreateContactRequest
import br.com.victorcatao.amaki.data.remote.models.DeleteCategoryRequest
import io.reactivex.Single

object CategoryRepository {

    fun getCategories(): Single<List<CategoryResponse>> {
        return CategoryRemoteDataSource.getCategories()
    }

    fun createCategory(request: CreateCategoryRequest): Single<Any> {
        return CategoryRemoteDataSource.createCategory(request)
    }

    fun deleteCategory(request: DeleteCategoryRequest): Single<Any> {
        return CategoryRemoteDataSource.deleteCategory(request)
    }
}