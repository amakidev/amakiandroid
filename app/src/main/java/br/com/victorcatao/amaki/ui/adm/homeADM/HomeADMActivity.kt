package br.com.victorcatao.amaki.ui.adm.homeADM

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.ui.adm.allEstablishments.createAllEstablishmentsIntent
import br.com.victorcatao.amaki.ui.adm.categoriesList.createCategoriesListIntent
import br.com.victorcatao.amaki.ui.adm.contactsList.createContactsListIntent
import br.com.victorcatao.amaki.ui.adm.editAbout.createEditAboutIntent
import br.com.victorcatao.amaki.ui.adm.newCategory.createNewCategoryIntent
import br.com.victorcatao.amaki.ui.adm.newEstablishment.createNewEstablishmentIntent
import br.com.victorcatao.amaki.ui.base.BaseActivity
import br.com.victorcatao.amaki.utils.extensions.notImplementedFeature
import kotlinx.android.synthetic.main.activity_home_adm.*
import org.jetbrains.anko.intentFor

class HomeADMActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_adm)
        setToolbar(getString(R.string.home_adm_title), false)
        setupListeners()
    }

    private fun setupListeners() {
        newCategoryBtn.setOnClickListener {
            startActivity(createNewCategoryIntent())
        }

        newEstablishmentBtn.setOnClickListener {
            startActivity(createNewEstablishmentIntent())
        }

        establishmentBtn.setOnClickListener {
            startActivity(createAllEstablishmentsIntent())
        }

        partnersBtn.setOnClickListener {
            startActivity(createContactsListIntent())
        }

        categoriesBtn.setOnClickListener {
            startActivity(createCategoriesListIntent())
        }

        aboutAmakiBtn.setOnClickListener {
            startActivity(createEditAboutIntent())
        }
    }
}

fun Context.createHomeADMIntent() = intentFor<HomeADMActivity>()