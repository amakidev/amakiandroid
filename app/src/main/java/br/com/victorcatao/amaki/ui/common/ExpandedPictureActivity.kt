package br.com.victorcatao.amaki.ui.common

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import br.com.victorcatao.amaki.utils.extensions.loadImage
import br.com.victorcatao.amaki.Constants
import br.com.victorcatao.amaki.R
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.activity_expanded_picture.*
import org.jetbrains.anko.intentFor

class ExpandedPictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expanded_picture)
        setListener()
        loadImage(intent.getStringExtra(Constants.EXTRA_PICTURE_URL))
    }

    private fun loadImage(url: String) {
        (pictureDetailIv as ImageView).loadImage(url = url, simpleTarget = object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                pictureDetailIv.setImageDrawable(resource)
            }
        })
    }

    private fun setListener() {
        pictureDetailBtn.setOnClickListener { finish() }
    }
}

fun Context.createExpandedPictureIntent(url: String): Intent {
    val intent = intentFor<ExpandedPictureActivity>(Constants.EXTRA_PICTURE_URL to url)
    return intent
}
