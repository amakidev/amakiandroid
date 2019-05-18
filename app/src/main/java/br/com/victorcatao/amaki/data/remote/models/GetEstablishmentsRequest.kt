package br.com.victorcatao.amaki.data.remote.models

data class GetEstablishmentsRequest(
        val city_id: String?,
        val category_id: String?,
        val user_lat: Double?,
        val user_long: Double?
)
