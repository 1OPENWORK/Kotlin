package com.stack.open_work_mobile.activities.lay_home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stack.open_work_mobile.R
import com.bumptech.glide.Glide


class ProjectCardHomeAdapter(private val projectCardHomeList: ArrayList<CardProjectHome>) :
    RecyclerView.Adapter<ProjectCardHomeAdapter.MyViewHolderCardProject>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderCardProject {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_project_home_open, parent, false)
        return MyViewHolderCardProject(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolderCardProject, position: Int) {
        val currentItem = projectCardHomeList[position]

        holder.companyName.text = currentItem.nameCompany
        holder.avaliationCompany.text = currentItem.grade.toString()
        holder.describe.text = currentItem.description
        holder.qtdDev.text = currentItem.maxDevelopers.toString()
        holder.value.text = currentItem.value.toString()

        // Carregar a imagem da empresa usando Glide
        val imageUrl = currentItem.imageCompany // Substitua isso pela lógica correta para obter a URL da imagem
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .into(holder.companyLogo)
        }
    }

    override fun getItemCount(): Int {
        return projectCardHomeList.size
    }

    class MyViewHolderCardProject(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyName: TextView = itemView.findViewById(R.id.name_company)
        val companyLogo: ImageView = itemView.findViewById(R.id.logo_company)
        val avaliationCompany: TextView = itemView.findViewById(R.id.avaliation_number_id)
        val describe: TextView = itemView.findViewById(R.id.description_project)
        val qtdDev: TextView = itemView.findViewById(R.id.dev_number_id)
        val value: TextView = itemView.findViewById(R.id.value_id)
    }
}