package br.com.victorcatao.amaki.utils.extensions

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.content.res.AppCompatResources
import br.com.victorcatao.amaki.Constants
import java.io.File
import java.util.*


fun Context.getColorRes(idRes: Int): Int {
    return ContextCompat.getColor(this, idRes)
}

fun Context.getDrawableCompat(@DrawableRes drawableResId: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawableResId)
}

@Deprecated("Use makeCall() from anko instead")
fun Context.callPhone(phone: String): Boolean {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
    return true
}

fun Context.navigateIntent(address: String = "", latitude: String = "0", longitude: String = "0") {
    val gmmIntentUri = "geo:$latitude,$longitude?q=$address"
    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(gmmIntentUri))
    startActivity(mapIntent)
}

fun Context.notImplementedFeature(msg: String? = null) {
    val fullMsg = if (msg != null) {
        "Função não implementada - " + msg
    } else {
        "Função não implementada"
    }
    this.showToast(fullMsg)
}

fun Context.newColorStateList(color: Int): ColorStateList {
    return ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_pressed), //1
                    intArrayOf(android.R.attr.state_focused), //2
                    intArrayOf(android.R.attr.state_focused, android.R.attr.state_pressed) //3
            ),
            intArrayOf(color, color, color)
    )
}

fun Context.createEventIntent(title: String, dateInMillis: Long = 0, allDay: Boolean = false) {
    val date = Calendar.getInstance()
    date.timeInMillis = dateInMillis

    val intent = Intent(Intent.ACTION_EDIT)

    intent.type = "vnd.android.cursor.item/event"
    intent.putExtra("title", title)
    intent.putExtra("beginTime", date.timeInMillis)
    intent.putExtra("endTime", date.timeInMillis + (60 * 60 * 1000))
    intent.putExtra("allDay", allDay)
    startActivity(intent)
}

fun rand(from: Int, to: Int): Int {
    return Random().nextInt(to - from) + from
}


fun Context.shareFile(file: File) {
    val uri = FileProvider.getUriForFile(this,
            Constants.PACKAGE_NAME + ".fileprovider",
            file)
    val share = Intent()
    share.action = Intent.ACTION_SEND
    share.type = contentResolver.getType(uri)
    share.putExtra(Intent.EXTRA_STREAM, uri)
    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivity(share)
}