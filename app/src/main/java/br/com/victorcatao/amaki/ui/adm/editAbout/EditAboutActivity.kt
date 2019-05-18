package br.com.victorcatao.amaki.ui.adm.editAbout

import android.content.Context
import android.os.Bundle
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.data.remote.models.AboutAmaki
import br.com.victorcatao.amaki.data.repositories.EstablishmentRepository
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.utils.extensions.singleSubscribe
import br.com.victorcatao.amaki.utils.extensions.str
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_edit_about.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.longToast

class EditAboutActivity : BaseActivity() {

    val bag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_about)
        setToolbar(str(R.string.about_title), true)

        setupListeners()
        downloadData()
    }

    fun downloadData() {
        bag.add(EstablishmentRepository.getAbout().singleSubscribe(
                onSuccess = {
                    aboutAmakiET.setText(it.text)
                },
                onError = {

                }
        ))
    }

    fun setupListeners() {
        sendbutton.setOnClickListener {
            val request = AboutAmaki(aboutAmakiET.text.toString())
            bag.add(EstablishmentRepository.updateAboutAmaki(request).singleSubscribe(
                    onSuccess = {
                        longToast("Atualizado com sucesso!")
                        finish()
                    },
                    onError = {
                        longToast(it.message.toString())
                    }
            ))
        }
    }

}


fun Context.createEditAboutIntent() =
        intentFor<EditAboutActivity>()