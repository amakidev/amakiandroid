package br.com.victorcatao.amaki.data.remote.datasources

import br.com.victorcatao.amaki.NetworkConstants.CATEGORY_URL
import br.com.victorcatao.amaki.data.remote.ServiceGenerator
import br.com.victorcatao.amaki.data.remote.interceptors.AddCookieInterceptor
import br.com.victorcatao.amaki.data.remote.interceptors.ReceivedCookieInterceptor
import br.com.victorcatao.amaki.data.remote.models.CreateCategoryRequest
import br.com.victorcatao.amaki.data.remote.models.DeleteCategoryRequest
import br.com.victorcatao.amaki.data.remote.services.CategoryService

object CategoryRemoteDataSource {

    private val service = ServiceGenerator.createCommonService(CategoryService::class.java,
            listOf(ReceivedCookieInterceptor(), AddCookieInterceptor()), CATEGORY_URL)

    fun getCategories() = service.getCategories()

    fun createCategory(request: CreateCategoryRequest) = service.createCategory(request)

    fun deleteCategory(request: DeleteCategoryRequest) = service.deleteCategory(request)

}


