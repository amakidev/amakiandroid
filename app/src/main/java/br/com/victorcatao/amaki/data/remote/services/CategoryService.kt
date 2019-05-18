package br.com.victorcatao.amaki.data.remote.services

import br.com.victorcatao.amaki.data.remote.models.CategoryResponse
import br.com.victorcatao.amaki.data.remote.models.CreateCategoryRequest
import br.com.victorcatao.amaki.data.remote.models.DeleteCategoryRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoryService {

    @GET("getCategories")
    fun getCategories(): Single<List<CategoryResponse>>

    @POST("createCategory")
    fun createCategory(@Body request: CreateCategoryRequest): Single<Any>

    @POST("deleteCategory")
    fun deleteCategory(@Body request: DeleteCategoryRequest): Single<Any>

}