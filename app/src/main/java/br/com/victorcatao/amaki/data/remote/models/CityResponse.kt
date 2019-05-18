package br.com.victorcatao.amaki.data.remote.models

data class CityResponse(
        val _id: String?,
        val name: String?,
        val state_id: StateAmaki?
)

data class StateAmaki(
        val nameShort: String?
)