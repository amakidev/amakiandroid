package br.com.victorcatao.amaki.data.remote.models

import java.io.Serializable

data class CreateContactRequest(
        val name: String?,
        val phone: String?,
        val establishment_name: String?,
        val address: String?,
        val socialNetwork: String?,
        val email: String?,
        val desc: String?
): Serializable