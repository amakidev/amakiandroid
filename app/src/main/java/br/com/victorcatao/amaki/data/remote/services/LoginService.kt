package br.com.victorcatao.amaki.data.remote.services

import br.com.victorcatao.amaki.data.remote.models.LoginResponse
import br.com.victorcatao.amaki.data.remote.models.SignInRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("signin")
    fun signIn(@Body signInRequest: SignInRequest): Single<LoginResponse>
}