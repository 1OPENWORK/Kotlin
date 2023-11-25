package com.stack.open_work_mobile.activities.lay_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.stack.open_work_mobile.MyFirebaseMessagingService
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.databinding.ActivityEntranceBinding
import com.stack.open_work_mobile.utils.NotificationUtils

class EntranceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntranceBinding
    private lateinit var firebaseMessagingService: MyFirebaseMessagingService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntranceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                Log.d("Task", "Task: $task")
                if (task.isSuccessful) {
                    // Token bem-sucedido
                    val token = task.result
                    Log.e("FCM Token", "Token: $token")
                } else {
                    // Falha ao obter o token
                    Log.e("FCM Token", "Falha ao obter o token", task.exception)
                }
            }

        firebaseMessagingService = MyFirebaseMessagingService()
        val titulo = "Seja bem vindo(a)!"
        val message = "Candidate-se em projetos, avalie empresas e mantenha seu perfil sempre atualizado para impulsionar sua jornada de trabalho!"
        MyFirebaseMessagingService.sendNotification(
            this,
            titulo,
            message
        )
        NotificationUtils.salvarNotificacao(this,titulo,  message, R.drawable.sininho)
        binding.btnDirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}




