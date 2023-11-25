package com.stack.open_work_mobile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stack.open_work_mobile.Interface.OnAvaliarClickListener
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.models.RatingCompanies


class ListAdapterRatingCompany(
    private val ratingCompaniesList: ArrayList<RatingCompanies>,
    private val clickListener: OnAvaliarClickListener
) :
    RecyclerView.Adapter<ListAdapterRatingCompany.MyViewHolderListRatingCompany>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolderListRatingCompany {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_my_rating, parent, false)
        return MyViewHolderListRatingCompany(itemView)
    }

    override fun getItemCount(): Int {
        return ratingCompaniesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolderListRatingCompany, position: Int) {
        val currentItem = ratingCompaniesList[position]

        holder.name.text = currentItem.myAvaliations[position].name
        holder.description.text = currentItem.myAvaliations[position].description
        holder.timeExpected.text = currentItem.myAvaliations[position].timeExpected.toString()
        holder.grade.text = currentItem.myAvaliations[position].grade.toString()
        holder.rating.rating = currentItem.myAvaliations[position].myGrade.toFloat()
        Glide.with(holder.itemView)
            .load(currentItem.myAvaliations[position].image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(100, 100)
            .into(holder.logo)
        holder.buttonAvaliar.setOnClickListener {
            val idEmpresa = currentItem.evaluates[position].id
            val nomeEmpresa = currentItem.evaluates[position].name
            val grade = currentItem.evaluates[position].grade
            clickListener.onAvaliarClick(idEmpresa, nomeEmpresa, grade)
        }
    }


    class MyViewHolderListRatingCompany(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById<TextView>(R.id.tv_nome_empresa2)
        val description: TextView = itemView.findViewById<TextView>(R.id.tv_description2)
        val timeExpected: TextView = itemView.findViewById<TextView>(R.id.tv_data2)
        val grade: TextView = itemView.findViewById<TextView>(R.id.tv_stars2)
        val buttonAvaliar: Button = itemView.findViewById<Button>(R.id.btn_avaliar)
        val rating: RatingBar = itemView.findViewById<RatingBar>(R.id.rb_starts2)
        val logo: ImageView = itemView.findViewById(R.id.ivLogoEmpresa)
    }
}