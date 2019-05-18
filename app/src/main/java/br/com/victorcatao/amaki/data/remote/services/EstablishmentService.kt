package br.com.victorcatao.amaki.data.remote.services

import br.com.victorcatao.amaki.data.remote.models.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface EstablishmentService {

    @POST("getEstablishments")
    fun getEstablishments(@Body request: GetEstablishmentsRequest): Single<List<EstablishmentResponse>>

    @GET("getStatesAndCities")
    fun getStatesAndCities(): Single<List<CityResponse>>

    @POST("createEstablishment")
    fun createEstablishment(@Body request: CreateEstablishmentsRequest): Single<CreateEstablishmentResponse>

    @Multipart
    @POST("updateEstablishmentPicture")
    fun updateEstablishmentPicture(@Part image: MultipartBody.Part, @Query("establishment_id") establishment_id: String): Single<Any>

    @GET("aboutAmaki")
    fun getAbout(): Single<AboutAmaki>

    @POST("deleteEstablishment")
    fun deleteEstablishment(@Body request: DeleteEstablishmentRequest): Single<Any>

    @POST("updateAboutAmaki")
    fun updateAboutAmaki(@Body request: AboutAmaki): Single<Any>

}