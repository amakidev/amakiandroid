package br.com.victorcatao.amaki.data.remote.datasources

import br.com.victorcatao.amaki.NetworkConstants.CONTACT_URL
import br.com.victorcatao.amaki.data.remote.ServiceGenerator
import br.com.victorcatao.amaki.data.remote.interceptors.AddCookieInterceptor
import br.com.victorcatao.amaki.data.remote.interceptors.ReceivedCookieInterceptor
import br.com.victorcatao.amaki.data.remote.models.CreateContactRequest
import br.com.victorcatao.amaki.data.remote.services.ContactService

object ContactRemoteDataSource {

    private val service = ServiceGenerator.createCommonService(ContactService::class.java,
            listOf(ReceivedCookieInterceptor(), AddCookieInterceptor()), CONTACT_URL)

    fun createContact(request: CreateContactRequest) = service.createContact(request = request)

    fun getContacts() = service.getContacts()

}