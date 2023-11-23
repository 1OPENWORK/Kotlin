package com.stack.open_work_mobile.utils

import android.content.Context
import android.util.Log
import com.stack.open_work_mobile.models.NotificationItem

class NotificationUtils {
    companion object {
        private const val PREF_NAME = "Notificacoes"

        fun salvarNotificacao(context: Context,titulo: String, mensagem: String, image : Int) {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            // Obtém a lista atual de notificações salvas
            val notificacoes = sharedPreferences.getStringSet("notificacoes", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

            // Adiciona a nova notificação à lista
            val novaNotificacao = "$titulo|$mensagem|$image"
            notificacoes.add(novaNotificacao)

            // Salva a lista atualizada no SharedPreferences
            editor.putStringSet("notificacoes", notificacoes)
            editor.apply()
        }

        fun getNotificacoesSalvas(context: Context): List<NotificationItem> {
            val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val notificacoes = sharedPreferences.getStringSet("notificacoes", setOf()) ?: setOf()

            return notificacoes.mapNotNull { notificacao ->
                val partes = notificacao.split("|")
                if (partes.size == 3) {
                    NotificationItem(partes[0], partes[1], partes[2].toInt())
                } else {
                    null
                }
            }


        }
    }
}
