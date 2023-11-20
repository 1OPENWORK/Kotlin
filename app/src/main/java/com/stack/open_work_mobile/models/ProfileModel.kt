package com.stack.open_work_mobile.models

data class ProfileModel (
    val id: Long,
    var name: String,
    var image: String,
    var cellphone: String,
    var cpfCnpj: String,
    var email :String
)