package br.com.victorcatao.amaki.data.remote.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import br.com.victorcatao.amaki.R
import br.com.victorcatao.amaki.utils.extensions.getColorRes
import br.com.victorcatao.amaki.ui.splash.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val CHANNEL = "IMCHANNEL"

class FirebaseMessagingService : FirebaseMessagingService() {

    private var msgCounter = 0

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val notification = remoteMessage.notification
        showNotification(notification?.body)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        remoteMessage?.let {
            Log.d("BOTI", "Notification From: " + remoteMessage.from)
            Log.d(CHANNEL, "From: " + remoteMessage.from)

            if (remoteMessage.data.isNotEmpty()) {
                Log.d(CHANNEL, "Message Notification: " + remoteMessage.data)
                showNotification(remoteMessage.data["message"], remoteMessage.data["title"])
            } else if (remoteMessage.notification != null) {
                Log.d(CHANNEL, "Message Notification Body: " + remoteMessage.notification!!.body)
                sendNotification(remoteMessage)
            }
        }
    }

    private fun showNotification(message: String?, title: String? = null) {
        //TODO Select activity to open when notification is clicked
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        //TODO set notification icons
        val notificationBuilder = NotificationCompat.Builder(this, "")
//                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_bar_notification))
//                .setSmallIcon(R.drawable.ic_bar_notification)
                .setContentTitle(if (title.isNullOrBlank()) getString(R.string.app_name_default) else title)
                .setContentText(message)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000))
                .setLights(getColorRes(R.color.colorAccent), 3000, 3000)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setChannelId(CHANNEL)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationChannel(CHANNEL, getString(R.string.app_name_default), NotificationManager.IMPORTANCE_DEFAULT))
        }

        notificationManager.notify(msgCounter, notificationBuilder.build())
        msgCounter = +1
    }

}