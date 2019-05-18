package br.com.victorcatao.amaki.data.remote.datasources

import br.com.victorcatao.amaki.NetworkConstants.ESTABLISHMENT_URL
import br.com.victorcatao.amaki.data.remote.ServiceGenerator
import br.com.victorcatao.amaki.data.remote.interceptors.AddCookieInterceptor
import br.com.victorcatao.amaki.data.remote.interceptors.ReceivedCookieInterceptor
import br.com.victorcatao.amaki.data.remote.models.AboutAmaki
import br.com.victorcatao.amaki.data.remote.models.CreateEstablishmentsRequest
import br.com.victorcatao.amaki.data.remote.models.DeleteEstablishmentRequest
import br.com.victorcatao.amaki.data.remote.models.GetEstablishmentsRequest
import br.com.victorcatao.amaki.data.remote.services.EstablishmentService
import okhttp3.MultipartBody

object EstablishmentRemoteDataSource {

    private val service = ServiceGenerator.createCommonService(EstablishmentService::class.java,
            listOf(ReceivedCookieInterceptor(), AddCookieInterceptor()), ESTABLISHMENT_URL)

    fun getEstablishments(request: GetEstablishmentsRequest) = service.getEstablishments(request)

    fun getStatesAndCities() = service.getStatesAndCities()

    fun createEstablishment(request: CreateEstablishmentsRequest) = service.createEstablishment(request)

    fun updateEstablishmentPicture(picture: MultipartBody.Part, establishment_id: String) = service.updateEstablishmentPicture(picture, establishment_id)

    fun getAbout() = service.getAbout()

    fun deleteEstablishment(request: DeleteEstablishmentRequest) = service.deleteEstablishment(request)

    fun updateAboutAmaki(request: AboutAmaki) = service.updateAboutAmaki(request)

}
