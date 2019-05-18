package br.com.victorcatao.amaki.data.remote.services

import br.com.victorcatao.amaki.data.remote.models.PostsResponse
import io.reactivex.Single
import retrofit2.http.GET

interface JsonPlaceholderService {
    @GET("posts")
    fun getPosts(): Single<List<PostsResponse>>
}
