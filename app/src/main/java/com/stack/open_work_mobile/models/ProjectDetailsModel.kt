package com.stack.open_work_mobile.models

import java.math.BigInteger

data class ProjectDetailsModel(
    val id: Long,
    var imageCompany: String,
    var title: String,
    var nameCompany: String,
    var description: String,
    var timeExpected: BigInteger,
    var maxDevelopers: Int,
    var value: Double,
    var tipoProjeto: String,
    var tools: List<ToolsModel>
) {
    constructor() : this(0, "", "", "", "", BigInteger.ZERO, 0, 0.0, "", emptyList())
}
