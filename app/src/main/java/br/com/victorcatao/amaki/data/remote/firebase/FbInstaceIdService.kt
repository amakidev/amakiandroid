package br.com.victorcatao.amaki.data.remote.firebase

import android.util.Log
import br.com.victorcatao.amaki.data.remote.datasources.FirebaseDataSource
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class FbInstaceIdService : FirebaseInstanceIdService(){
    class FbInstanceIdService : FirebaseInstanceIdService() {

        override fun onTokenRefresh() {
            val refreshedToken = FirebaseInstanceId.getInstance().token
            Log.d("BOTI", "Refreshed token: " + refreshedToken!!)
            FirebaseDataSource.registerToken()
        }

    }
}