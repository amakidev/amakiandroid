package br.com.victorcatao.amaki.data.repositories

import br.com.victorcatao.amaki.data.local.model.UserDataSigninModel
import br.com.victorcatao.amaki.data.remote.datasources.LoginRemoteDataSource
import br.com.victorcatao.amaki.data.remote.models.LoginResponse
import br.com.victorcatao.amaki.data.remote.models.SignInRequest
import io.reactivex.Single

object LoginRepository {

    fun singIn(userData: UserDataSigninModel): Single<LoginResponse> {
        return LoginRemoteDataSource.signIn(SignInRequest(
                userData.email,
                userData.password
        ))
    }
}


