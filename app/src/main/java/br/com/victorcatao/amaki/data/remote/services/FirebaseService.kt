package br.com.victorcatao.amaki.data.remote.services

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FirebaseService {

    //TODO change api
    @FormUrlEncoded
    @POST("registerToken")
    fun registerToken(@Field("token") token: String,
                      @Field("os") os: String,
                      @Field("device") device: String): Single<Any>

}