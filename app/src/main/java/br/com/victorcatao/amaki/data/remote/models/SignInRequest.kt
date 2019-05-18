package br.com.victorcatao.amaki.data.remote.models

data class SignInRequest(
        val email: String?,
        val password: String? = null
)