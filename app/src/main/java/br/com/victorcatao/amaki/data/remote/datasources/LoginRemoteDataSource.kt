package br.com.victorcatao.amaki.data.remote.datasources

import br.com.victorcatao.amaki.NetworkConstants.ESTABLISHMENT_URL
import br.com.victorcatao.amaki.data.remote.ServiceGenerator
import br.com.victorcatao.amaki.data.remote.interceptors.AddCookieInterceptor
import br.com.victorcatao.amaki.data.remote.interceptors.ReceivedCookieInterceptor
import br.com.victorcatao.amaki.data.remote.models.SignInRequest
import br.com.victorcatao.amaki.data.remote.services.LoginService

object LoginRemoteDataSource {

    private val service = ServiceGenerator.createCommonService(LoginService::class.java,
            listOf(ReceivedCookieInterceptor(), AddCookieInterceptor()), ESTABLISHMENT_URL)

    fun signIn(signInRequest: SignInRequest) = service.signIn(signInRequest)
}