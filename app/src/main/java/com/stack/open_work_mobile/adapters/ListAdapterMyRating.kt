package com.stack.open_work_mobile.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.stack.open_work_mobile.R
import com.stack.open_work_mobile.models.MyRating
import com.stack.open_work_mobile.models.RatingCompanies
import org.w3c.dom.Text

data class ListAdapterMyRating(
    private val context: Activity,
    private val ratingCompanies: ArrayList<MyRating>
) : ArrayAdapter<MyRating>(context, R.layout.list_my_rating, ratingCompanies) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_my_rating, null)

        val nameComp: TextView = view.findViewById(R.id.tv_nome_empresa2)
        val qtStarts: TextView = view.findViewById(R.id.tv_stars2)
        val stars: ImageView = view.findViewById(R.id.ivStar2)
        val logo: ImageView = view.findViewById(R.id.ivLogoEmpresa)
        val data: TextView = view.findViewById(R.id.tv_data2)
        val descricao: TextView = view.findViewById(R.id.tv_description2)
        val rating: RatingBar = view.findViewById(R.id.rb_starts2)
        val button: Button = view.findViewById(R.id.btn_avaliar)

        nameComp.setText(ratingCompanies[position].nomeEmpresa)
        qtStarts.setText(ratingCompanies[position].qtdEstrelas)
        /*  stars.setImageResource(ratingCompanies[position].imageEstrelas)
          logo.setImageResource(ratingCompanies[position].logo)*/
        data.setText(ratingCompanies[position].dataAvaliacao)
        descricao.setText(ratingCompanies[position].descricao)

        rating.setOnRatingBarChangeListener { _, rating, _ ->
            ratingCompanies[position].qtdEstrelas = rating.toString()
        }

        button.setOnClickListener {
            val currentRating = ratingCompanies[position]
        }


        return view

    }
}



