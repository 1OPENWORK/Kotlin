package com.stack.open_work_mobile.services

import com.stack.open_work_mobile.models.FinanceModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FinanceService {

    @GET("/api/dashboard/projetos-progress/{id}")
    fun projetosReceber(@Path("id") id: Int?): Call<List<FinanceModel>>

    @GET("/api/dashboard/projetos-completed/{id}")
    fun projetosRecebidos(@Path("id") id: Int?): Call<List<FinanceModel>>

    @GET("/api/dashboard/projetos-cancelled/{id}")
    fun projetosCancelados(@Path("id") id: Int?): Call<List<FinanceModel>>
}