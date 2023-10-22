package com.stack.open_work_mobile.models


data class FinanceModel(
    val id: Int,
    val imageCompany: String,
    val titleProject: String,
    val nameCompany: String,
    val descriptionProject: String,
    val progress: Int,
    val beginDate: String,
    val finishDate: String,
    val tipoProjeto: String,
    val idBigProject: Int,
    val idMiniProject: Int,
    val value: Double,
    val tax: Int,
    val developers: Array<DeveloperModel>
)
