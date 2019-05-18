package br.com.victorcatao.amaki.data.remote.datasources

import br.com.victorcatao.amaki.data.remote.ServiceGenerator
import br.com.victorcatao.amaki.data.remote.models.PostsResponse
import br.com.victorcatao.amaki.data.remote.services.JsonPlaceholderService
import io.reactivex.Single

object JsonPlaceholderRemoteDataSource {
    private var mService: JsonPlaceholderService? = null

    init {
        mService = ServiceGenerator
                .createService(serviceClass = JsonPlaceholderService::class.java,
                        url = "https://jsonplaceholder.typicode.com/")
    }

    fun getPolls() : Single<List<PostsResponse>> {
        return mService!!.getPosts()
    }
}