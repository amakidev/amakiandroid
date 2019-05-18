package br.com.victorcatao.amaki.data.remote.interceptors

import br.com.victorcatao.amaki.data.local.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ReceivedCookieInterceptor : Interceptor {
    private val RESPONSE_HEADER_COOKIE = "Set-Cookie"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        val cookie = originalResponse.headers().get(RESPONSE_HEADER_COOKIE)
        try {
            if (!cookie!!.isEmpty() && PreferencesHelper.getInstance().sessionCookie != cookie) {
                PreferencesHelper.getInstance().putSessionCookie(cookie)
            }
        } catch (e: NullPointerException) {
            //Do Nothing
        }

        return originalResponse
    }

}
