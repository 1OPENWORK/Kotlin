package com.stack.open_work_mobile.models

data class UpdateResponse (
    val userId: Long,
    var nome: String,
    var email: String,
    var tipo: String,
    var token: String,
    var companyId: Long,
    var image: String,
)