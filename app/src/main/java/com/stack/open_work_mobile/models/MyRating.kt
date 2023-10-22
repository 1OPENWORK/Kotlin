package com.stack.open_work_mobile.models

import android.widget.Button
import android.widget.RatingBar

class MyRating(
    var id: Int,
    var nomeEmpresa: String,
    var dataAvaliacao: String,
   var qtdEstrelas: String,
    /*  var estrelas: Float,*/
    /*var imageEstrelas: Int,
    var logo: Int,*/
    var descricao: String,
    var button : Button? = null,
    var rating : RatingBar? = null
) {


}