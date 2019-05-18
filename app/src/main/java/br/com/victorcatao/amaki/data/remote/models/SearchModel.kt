package br.com.victorcatao.amaki.data.remote.models

import java.io.Serializable

data class SearchModel (
        val name: String?,
        val _id: String?,
        var isChecked: Boolean = false
): Serializable