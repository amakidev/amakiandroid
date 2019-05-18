package br.com.victorcatao.amaki.data.remote.models

data class CreateEstablishmentsRequest(
        var id_establishment: String?,
        var desc: String?,
        var latitude: Float?,
        var longitude: Float?,
        val facebook: String?,
        val instagram: String?,
        val site: String?,
        val name: String?,
        val cellphone: String?,
        val phone: String?,
        var address: String?,
        val isWhatsApp: Boolean,
        val hasDiscount: Boolean,
        val isPremium: Boolean,
        val categories: List<String>?
)

