package com.stack.open_work_mobile.models.authModel

data class UpdateProfileModel (
    var name: String,
    var cellphone: String,
    var cpfCnpj: String,
    var email :String,
    var password: String
)