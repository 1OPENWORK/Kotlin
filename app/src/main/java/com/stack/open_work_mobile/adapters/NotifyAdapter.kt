package com.stack.open_work_mobile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.models.NotificationItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotifyAdapter(private val itemList: List<NotificationItem>) :
    RecyclerView.Adapter<NotifyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_notify_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.textItem.text = item.text
        holder.imageView.setImageResource(item.imageResId)


        // Você pode adicionar código aqui para configurar outros elementos do layout
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textItem: TextView = itemView.findViewById(R.id.textItem)
        val imageView: ImageView = itemView.findViewById(R.id.iv_notificacao)
    }
}