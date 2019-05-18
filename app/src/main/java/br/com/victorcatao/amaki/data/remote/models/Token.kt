package br.com.victorcatao.amaki.data.remote.models

data class TokenRequest(
        val user_id: String,
        val token: String
)

data class TokenResponse(
        val id: String,
        val user_id: String,
        val token: String
)