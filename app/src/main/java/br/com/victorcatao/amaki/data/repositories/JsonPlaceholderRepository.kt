package br.com.victorcatao.amaki.data.repositories

import br.com.victorcatao.amaki.data.local.PreferencesHelper
import br.com.victorcatao.amaki.data.remote.datasources.JsonPlaceholderRemoteDataSource
import br.com.victorcatao.amaki.data.remote.models.PostsResponse
import io.reactivex.Single


object JsonPlaceholderRepository  {
    private val mPreferencesHelper: PreferencesHelper = PreferencesHelper.getInstance()
    private val mRemoteDataSource: JsonPlaceholderRemoteDataSource = JsonPlaceholderRemoteDataSource

    fun getPolls() : Single<List<PostsResponse>> {
        return mRemoteDataSource.getPolls()
    }
}