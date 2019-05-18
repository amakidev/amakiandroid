package br.com.victorcatao.amaki.data.remote.models

import java.io.Serializable

data class EstablishmentResponse(
        val name: String?,
        val _id: String?,
        val latitude: Float?,
        val longitude: Float?,
        val distance: Float?,
        val desc: String?,
        val categories: List<CategoryResponse>?,
        val neighborhood: String?,
        val address: String?,
        val facebook: String?,
        val instagram: String?,
        val site: String?,
        val isWhatsApp: Boolean?,
        val phone: String?,
        val cellphone: String?,
        val hasDiscount: Boolean?,
        val isPremium: Int?,
        val picture: String?
): Serializable
