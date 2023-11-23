package com.stack.open_work_mobile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.stack.open_work_mobile.activities.lay_login.LoginActivity
import com.stack.open_work_mobile.fragments.MyCompanyFragment

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Trate a mensagem recebida e exiba a notificação
        // Você pode personalizar essa parte conforme necessário
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Verifique se a mensagem contém dados.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            // Aqui você pode processar os dados da mensagem, se necessário
        }

        // Verifique se a mensagem contém uma notificação.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            // Aqui você pode criar e exibir a notificação
            MyFirebaseMessagingService.sendNotification(applicationContext, it.title, it.body)
        }
    }


    companion object {
        private const val TAG = "MyFirebaseMsgService"

        @JvmStatic
        fun sendNotification(context: Context, title: String?, messageBody: String?) {
            // Implemente a lógica para exibir a notificação aqui
            // Por exemplo, você pode criar um Intent para abrir a atividade desejada ao clicar na notificação

            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channelId =
                "default_channel_id" // ID do canal de notificação (pode ser qualquer string única)
            val channelName = "Default Channel"   // Nome do canal de notificação

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }

            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logoopenwork)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

            notificationManager.notify(0, notificationBuilder.build())
        }
        @JvmStatic
        fun sendNotificationAvaliation(context: Context, title: String?, messageBody: String?) {
            // Implemente a lógica para exibir a notificação aqui
            // Por exemplo, você pode criar um Intent para abrir a atividade desejada ao clicar na notificação

            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channelId =
                "default_channel_id" // ID do canal de notificação (pode ser qualquer string única)
            val channelName = "Default Channel"   // Nome do canal de notificação

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }

            val intent = Intent(context, MyCompanyFragment::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logoopenwork)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

            notificationManager.notify(0, notificationBuilder.build())
        }



    }
}
