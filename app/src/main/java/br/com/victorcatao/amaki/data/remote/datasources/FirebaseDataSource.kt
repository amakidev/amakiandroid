package br.com.victorcatao.amaki.data.remote.datasources

import android.os.Build
import android.util.Log.d
import android.util.Log.e
import br.com.victorcatao.amaki.data.remote.ServiceGenerator
import br.com.victorcatao.amaki.data.remote.interceptors.AddCookieInterceptor
import br.com.victorcatao.amaki.data.remote.services.FirebaseService
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

object FirebaseDataSource {

    private val mService = ServiceGenerator.createService(serviceClass = FirebaseService::class.java,
            interceptors = listOf(AddCookieInterceptor()), url = "")

    fun registerToken() {
        if (FirebaseInstanceId.getInstance().token.isNullOrEmpty())
            return

        val token = FirebaseInstanceId.getInstance().token!!
        val os = "Android ${Build.VERSION.SDK_INT}"
        val device = Build.MODEL


        mService.registerToken(token, os, device)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith(object : DisposableSingleObserver<Any>() {
                    override fun onSuccess(t: Any) {
                        d("FCM", "token: $token")
                        d("FCM", "os: $os")
                        d("FCM", "device: $device")
                    }

                    override fun onError(ex: Throwable) {
                        e("FCM", "Erro ao registrar token FCM")
                    }
                })
    }
}