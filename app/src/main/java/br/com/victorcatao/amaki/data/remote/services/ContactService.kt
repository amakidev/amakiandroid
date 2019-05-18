package br.com.victorcatao.amaki.data.remote.services

import br.com.victorcatao.amaki.data.remote.models.ContactResponse
import br.com.victorcatao.amaki.data.remote.models.CreateContactRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ContactService {

    @POST("createContact")
    fun createContact(@Body request: CreateContactRequest): Single<Any>

    @GET("getContacts")
    fun getContacts(): Single<List<ContactResponse>>
}