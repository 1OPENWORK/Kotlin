package com.stack.open_work_mobile.models

data class RatingCompanies(
    val myAvaliations: List<MyAvaliation>, // O tipo real deve ser definido corretamente
    val evaluates: List<Evaluate>
)

data class Evaluate(
    val id: Long,
    val image: String,
    val name: String,
    val grade: Int,
    val description: String,
    val timeExpected: String,
    val idAcceptedDev: Int? // O tipo real deve ser definido corretamente
)

data class MyAvaliation(
    val id: Int,
    val image: String,
    val name: String,
    val grade: Int,
    val myGrade: Int,
    val description: String,
    val timeExpected: String,
    val maxDevelopers: Int? // O tipo real deve ser definido corretamente
)