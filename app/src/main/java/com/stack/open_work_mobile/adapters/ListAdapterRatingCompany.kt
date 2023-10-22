package com.stack.open_work_mobile.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.models.RatingCompanies

data class ListAdapterRatingCompany(
    private val context: Activity,
    private val ratingCompanies: ArrayList<RatingCompanies>
) : ArrayAdapter<RatingCompanies>(context, R.layout.list_my_rating_company, ratingCompanies) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_my_rating_company, null)

        val nameComp: TextView = view.findViewById(R.id.tv_nome_company)
        val qtStarts: TextView = view.findViewById(R.id.tv_stars)
        val stars: ImageView = view.findViewById(R.id.ivStar)
        val logo: ImageView = view.findViewById(R.id.logoempresa)
        val data: TextView = view.findViewById(R.id.tv_data)
        val descricao: TextView = view.findViewById(R.id.tv_description)
        val rating: RatingBar = view.findViewById(R.id.rb_starts)

        nameComp.setText(ratingCompanies[position].nomeEmpresa)
        qtStarts.setText(ratingCompanies[position].qtdEstrelas)
      /*  stars.setImageResource(ratingCompanies[position].imageEstrelas)
        logo.setImageResource(ratingCompanies[position].logo)*/
        data.setText(ratingCompanies[position].dataAvaliacao)
        descricao.setText(ratingCompanies[position].descricao)
  /*      rating.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
                val newRating = 4.5f
                ratingBar?.rating = newRating
                ratingCompanies[position].estrelas = newRating
            }
        })*/

        return view

    }
}



