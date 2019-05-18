package br.com.victorcatao.amaki.ui.about

import android.content.Context
import android.os.Bundle
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.EstablishmentsExtra
import br.com.victorcatao.amaki.data.repositories.EstablishmentRepository
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import br.com.victorcatao.amaki.utils.extensions.str
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_about.*
import org.jetbrains.anko.intentFor


class About : BaseActivity() {

    val bag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setToolbar(str(R.string.about_title), true)

        downloadData()
    }

    fun downloadData() {
        bag.add(EstablishmentRepository.getAbout().singleSubscribe(
                onSuccess = {
                    aboutAmakiTV.text = it.text
                },
                onError = {

                }
        ))
    }

}


fun Context.createAboutIntent() =
        intentFor<About>()