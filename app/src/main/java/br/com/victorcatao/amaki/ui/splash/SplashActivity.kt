package br.com.victorcatao.amaki.ui.splash

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.victorcatao.amaki.BuildConfig
import br.com.victorcatao.amaki.ui.about.createAboutIntent
import br.com.victorcatao.amaki.ui.adm.homeADM.createHomeADMIntent
import br.com.victorcatao.amaki.ui.home.createHomeIntent
import br.com.victorcatao.amaki.ui.simplelist.createSimpleListIntent

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(BuildConfig.IS_ADM) {
            startActivity(createHomeADMIntent())
        } else {
            startActivity(createHomeIntent())
        }

        finish()
    }
}
