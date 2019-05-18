package br.com.victorcatao.amaki.data.remote.models

import java.io.Serializable

data class UpdateEstablishmentPhotoRequest(
        val id_establishment: String?,
        val image: String
): Serializable