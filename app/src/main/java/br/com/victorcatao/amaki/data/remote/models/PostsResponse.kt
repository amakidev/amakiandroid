package br.com.victorcatao.amaki.data.remote.models

data class PostsResponse (
    var userId: Int = 0,
    var id: Int = 0,
    var title: String? = null,
    var body: String? = null
)