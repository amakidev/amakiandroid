package br.com.victorcatao.amaki.data.remote.models

import java.io.Serializable

data class CategoryResponse(
        val name: String?,
        val _id: String?,
        var selected: Boolean = false
): Serializable {
    fun toSearchModel(): SearchModel {
        return SearchModel(name = this.name, _id = this._id, isChecked = this.selected)
    }
}
