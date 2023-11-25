package com.stack.open_work_mobile.services

import com.stack.open_work_mobile.models.Filters
import retrofit2.Call
import retrofit2.http.GET

interface Filters {

    @GET("/api/ferramentas")
    fun getTools(): Call<List<Filters>>
}