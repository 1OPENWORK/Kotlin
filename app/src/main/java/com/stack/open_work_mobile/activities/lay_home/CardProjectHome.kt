package com.stack.open_work_mobile.activities.lay_home

import java.time.LocalDate
import java.util.Date

data class CardProjectHome(
    val id: Long,
    var imageCompany: String,
    var nameCompany: String,
    var grade: Double,
    var title: String,
    var description: String,
    var maxDevelopers: Int,
    var value: String   
)
