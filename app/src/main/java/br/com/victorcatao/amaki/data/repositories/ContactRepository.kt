package br.com.victorcatao.amaki.data.repositories

import br.com.victorcatao.amaki.data.remote.datasources.ContactRemoteDataSource
import br.com.victorcatao.amaki.data.remote.models.ContactResponse
import br.com.victorcatao.amaki.data.remote.models.CreateContactRequest
import io.reactivex.Single

object ContactRepository {
    fun createContact(request: CreateContactRequest): Single<Any> {
        return ContactRemoteDataSource.createContact(request)
    }

    fun getContacts(): Single<List<ContactResponse>> {
        return ContactRemoteDataSource.getContacts()
    }
}