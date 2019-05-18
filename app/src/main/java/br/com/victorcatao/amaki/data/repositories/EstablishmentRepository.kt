package br.com.victorcatao.amaki.data.repositories

import br.com.victorcatao.amaki.data.remote.datasources.EstablishmentRemoteDataSource
import br.com.victorcatao.amaki.data.remote.models.*
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object EstablishmentRepository {
    fun getEstablishments(request: GetEstablishmentsRequest): Single<List<EstablishmentResponse>> {
        return EstablishmentRemoteDataSource.getEstablishments(request = request)
    }

    fun getStatesAndCities(): Single<List<CityResponse>> {
        return EstablishmentRemoteDataSource.getStatesAndCities()
    }

    fun createEstablishment(request: CreateEstablishmentsRequest): Single<CreateEstablishmentResponse> {
        return EstablishmentRemoteDataSource.createEstablishment(request)
    }

    fun updateEstablishmentPicture(file: File, establishment_id: String) = EstablishmentRemoteDataSource
            .updateEstablishmentPicture(
                    MultipartBody.Part.createFormData("image", file.name, RequestBody.create(MediaType.parse("image/*"), file)),
                    establishment_id
            )
            .doAfterSuccess {
                // FAZER O QUE QUISER QUANDO TERMINAR
            }


    fun getAbout(): Single<AboutAmaki> {
        return EstablishmentRemoteDataSource.getAbout()
    }

    fun deleteEstablishment(request: DeleteEstablishmentRequest): Single<Any> {
        return EstablishmentRemoteDataSource.deleteEstablishment(request)
    }

    fun updateAboutAmaki(request: AboutAmaki): Single<Any> {
        return EstablishmentRemoteDataSource.updateAboutAmaki(request)
    }

}
