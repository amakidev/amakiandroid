package br.com.victorcatao.amaki.data.remote.models

data class PictureResponse(
        val id: String,
        val video: String? = null,
        val picture: String?
)

data class Picture(
        var id: String,
        var url: String?,
        var loaded: Boolean = true,
        var error: Boolean = false,
        var video: String? = null
)

fun PictureResponse.toModel() = Picture(id = id, url = video ?: picture, video = video)