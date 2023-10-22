package com.stack.open_work_mobile.models

data class AddressModel(
    val zipcode: String,
    val state: String,
    val city: String,
    val district: String,
    val address: String,
    val number: String,
    val complement: String
)