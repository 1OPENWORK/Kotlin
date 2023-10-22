package com.stack.open_work_mobile.activities.lay_my_projects

import android.widget.ListAdapter
import com.stack.open_work_mobile.models.DeveloperModel
import java.time.LocalDate

data class ProjectProgressCard(
    val id: Long,
    var imageCompany: String,
    var titleProject: String,
    var nameCompany: String,
    var descriptionProject: String,
    var progress: Double,
    var finishDate: String,
    var tipoProjeto: String,
    val idBigProject: Int,
    val idMiniProject: Int,
    var value: Double,
    var tax: Double,
    var developers: List<DeveloperModel>
)
