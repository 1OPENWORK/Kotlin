package com.stack.open_work_mobile.activities.Notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.activities.lay_home.HomeActivity
import com.stack.open_work_mobile.adapters.NotifyAdapter
import com.stack.open_work_mobile.models.NotificationItem
import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.stack.open_work_mobile.utils.NotificationUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationActivity : AppCompatActivity() {
    private lateinit var itemList: MutableList<NotificationItem>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotifyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val btn = findViewById<View>(R.id.arrow_back)
        btn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        val data = findViewById<TextView>(R.id.titulo2_notification)

        val formatoData = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dataAtual = Date()
        val dataHoje = formatoData.format(dataAtual)
        data.text = dataHoje

        itemList = mutableListOf()

        // Obtém as notificações salvas do SharedPreferences
        val notificacoesSalvas = NotificationUtils.getNotificacoesSalvas(this)

        // Adiciona as notificações salvas à lista itemList
        itemList.addAll(notificacoesSalvas)



        itemList.forEach {
            itemList.forEachIndexed { index, item ->
                val notificationId = index + 1
            }
        }


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crie e defina o adaptador para a RecyclerView
        adapter = NotifyAdapter(itemList)
        recyclerView.adapter = adapter

        // Crie um ItemTouchHelper para lidar com os gestos de deslizar
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // Não precisamos disso para deslizar para excluir
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Chamado quando o item é deslizado para a esquerda ou direita
                val position = viewHolder.adapterPosition
                val removedItem = itemList.removeAt(position)
                adapter.notifyItemRemoved(position)

                // Você pode adicionar código aqui para fazer qualquer outra coisa com o item removido,
                // como exibir uma mensagem de desfazer ou excluí-lo permanentemente.
            }
        })


        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun sendNotification(context: Context, title: String, message: String, image: Int, id: Int) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Verifique se o Android 8.0+ requer a criação de um canal de notificação
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channel_id" // Identificador único para o canal
            val channelName = "Channel Name" // Nome do canal

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )


            channel.description = "Channel Description"
            channel.enableVibration(true)

            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(
            context,
            NotificationActivity::class.java
        ) // Substitua MainActivity pelo nome da sua atividade
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Use FLAG_IMMUTABLE aqui
        )

        val notificationBuilder = NotificationCompat.Builder(context, "channel_id")
            .setSmallIcon(image)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
            .setAutoCancel(true)

        val notificationId = 1 // Identificador único para a notificação
        notificationManager.notify(id, notificationBuilder.build())
    }



}