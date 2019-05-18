package br.com.victorcatao.amaki.data.remote.models

import java.io.Serializable

data class EstablishmentsExtra(
        var establishments: List<EstablishmentResponse>
): Serializable