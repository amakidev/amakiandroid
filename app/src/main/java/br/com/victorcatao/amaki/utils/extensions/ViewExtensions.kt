package br.com.victorcatao.amaki.utils.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.utils.CurrencyMask
import br.com.victorcatao.amaki.utils.CustomLinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit


fun pxToDp(px: Int): Float {
    val densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
    return px / (densityDpi / 160f)
}

fun dpToPx(dp: Float): Int {
    val density = Resources.getSystem().displayMetrics.density
    return Math.round(dp * density)
}

fun Context.dpToFloat(dps: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps,
            this.resources.displayMetrics)
}

fun Context.spToFloat(dps: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dps,
            this.resources.displayMetrics)
}

fun View.setVisible(visible: Boolean, useInvisible: Boolean = false) {
    visibility = when {
        visible -> View.VISIBLE
        useInvisible -> View.INVISIBLE
        else -> View.GONE
    }
}

fun ImageView.loadImage(url: String?, options: RequestOptions? = null, simpleTarget: SimpleTarget<Drawable>? = null): Boolean {
    try {
        val optionsToApply = options ?: RequestOptions()
                .placeholder(ContextCompat.getDrawable(context, R.drawable.placeholder_photo))
                .centerCrop()
        if (simpleTarget == null) {
            Glide.with(context).load(url).apply(optionsToApply).into(this)
        } else {
            Glide.with(context).load(url).apply(optionsToApply).into(simpleTarget)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
    return true
}


fun ImageView.loadRoundedImage(url: String?, addPlaceholder: Boolean = false): Boolean {
    try {
        val optionsToApply = RequestOptions()
        if (addPlaceholder) optionsToApply.placeholder(ContextCompat.getDrawable(context, R.drawable.placeholder_photo))
        optionsToApply.optionalTransform(MultiTransformation(CenterCrop(), RoundedCornersTransformation(context.resources.getDimensionPixelSize(R.dimen.roundness_partial), 0)))
        Glide.with(context).load(url).apply(optionsToApply).into(this)
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
    return true
}


fun ImageView.loadCircleImage(url: String?, addPlaceholder: Boolean = false): Boolean {
    try {
        val optionsToApply = RequestOptions()
        if (addPlaceholder) optionsToApply.placeholder(ContextCompat.getDrawable(context, R.drawable.placeholder_user))
        optionsToApply.circleCrop()
        Glide.with(context).load(url).apply(optionsToApply).into(this)
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
    return true
}

//fun ImageView.loadStaggeredRoundedImage(url: String?, minHeight: Int, maxHeight: Int): Boolean {
//    try {
//        val view = this
//        Glide.with(context).load(url).into(object : SimpleTarget<Drawable>() {
//            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//                view.layoutParams.height = resource.intrinsicHeight
//                view.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
//                view.loadRoundedImage(url)
//            }
//
//            override fun onLoadFailed(errorDrawable: Drawable?) {
//                view.layoutParams.height = minHeight
//                view.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
//                super.onLoadFailed(errorDrawable)
//            }
//        })
//    } catch (e: Exception) {
//        e.printStackTrace()
//        return false
//    }
//    return true
//}

fun ImageView.loadImage(file: File, options: RequestOptions? = null): Boolean {
    try {
        val optionsToApply = options ?: RequestOptions()
                .placeholder(ContextCompat.getDrawable(context, R.drawable.placeholder_photo))
                .centerCrop()
        Glide.with(context).load(file).apply(optionsToApply).into(this)
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
    return true
}

fun RecyclerView.setup(adapter: RecyclerView.Adapter<in RecyclerView.ViewHolder>,
                       layoutManager: RecyclerView.LayoutManager? = CustomLinearLayoutManager(this.context),
                       decoration: RecyclerView.ItemDecoration? = null,
                       hasFixedSize: Boolean = true) {

    this.adapter = adapter
    this.layoutManager = layoutManager
    this.setHasFixedSize(hasFixedSize)
    decoration?.let { this.addItemDecoration(it) }
}

//SNACK

fun showSnack(coordinator: CoordinatorLayout, message: String, retryText: String, action: (v: View) -> Unit?, indefinite: Boolean = true) {
    Snackbar.make(coordinator, message, if (indefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG)
            .setAction(retryText) { v -> action(v) }
            .show()
}

fun snack(coordinator: CoordinatorLayout, message: String, retryText: String,
          action: (v: View) -> Unit?, indefinite: Boolean = true): Snackbar {
    return Snackbar.make(coordinator, message, if (indefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG)
            .setAction(retryText) { v -> action(v) }
}

fun showSnack(coordinator: CoordinatorLayout, message: String, indefinite: Boolean) =
        Snackbar.make(coordinator, message, if (indefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG)


//Animations
fun View.translate(animId: Int, duration: Long? = null) {
    val anim = AnimationUtils.loadAnimation(this.context, animId)
    if (duration != null) anim.duration = duration
    startAnimation(anim)
}

fun EditText.addTextWatcherDebounce(timeoutInMillis: Long, action: ((String) -> Unit)) {
    Observable.create(ObservableOnSubscribe<String> { subscriber ->
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                subscriber.onNext(query.toString())
            }
        })
    }).debounce(timeoutInMillis, TimeUnit.MILLISECONDS).distinct()
            .observableSubscribe(onNext = {
                action(it)
            })
}

fun EditText.afterTextChanged(onTextChanged: ((String) -> Unit)) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            onTextChanged(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}

fun EditText.addCurrencyMask(displayCurrency: Boolean = false) {
    addTextChangedListener(CurrencyMask.insert(Locale("pt", "BR"), this))
}

fun EditText.currencyValue() = CurrencyMask.parseValue(this.text.toString())